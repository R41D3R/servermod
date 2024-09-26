package julian.servermod;

import io.wispforest.owo.network.OwoNetChannel;
import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.entity.ModBlockEntities;
import julian.servermod.color.ModColorProviders;
import julian.servermod.command.ModCommandRegister;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.custom.LootBalloonEntity;
import julian.servermod.entity.custom.SnailEntity;
import julian.servermod.item.ModComponents;
import julian.servermod.item.ModItemGroups;
import julian.servermod.item.ModItems;
import julian.servermod.item.ModPotions;
import julian.servermod.screen.CrateRewardScreen;
import julian.servermod.screen.ModScreenHandlers;
import julian.servermod.screen.StoreScreen;
import julian.servermod.screen.util.InventoryUtil;
import julian.servermod.sound.ModSounds;
import julian.servermod.utils.FarmWorldUtil.ChangeTimeChecker;
import julian.servermod.utils.FarmWorldUtil.FolderDeleter;
import julian.servermod.utils.FarmWorldUtil.YamlSeedUpdater;
import julian.servermod.utils.playerdata.WalletData;
import julian.servermod.utils.pockets.PocketUtil;
import julian.servermod.world.gen.ModWorldGeneration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class ServerMod implements ModInitializer {

	public static final OwoNetChannel STORE_BUY_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "store_buy"));
	public static final OwoNetChannel CRATE_REWARD_SCREEN_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "crate_reward_screen"));
	public static final OwoNetChannel BADGER_TASK_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "badger_task"));

	public static final OwoNetChannel BUNDLE_SCROLL_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "bundle_scroll"));


	public static final CustomGameRuleCategory GREEN_CATEGORY = new CustomGameRuleCategory(Identifier.of(ServerMod.MOD_ID, "netherroof_do_death"),
			Text.of("Void Nether Roof Mod"));
	public static final GameRules.Key<GameRules.BooleanRule> DO_DEATH = register("killOnRoof", GREEN_CATEGORY, GameRuleFactory.createBooleanRule(false));

	public static final String MOD_ID = "servermod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static <T extends GameRules.Rule<T>> GameRules.Key<T> register(String name, CustomGameRuleCategory category, GameRules.Type<T> type) {
		return GameRuleRegistry.register(name, category, type);
	}


	@Override
	public void onInitialize() {

		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModDataComponents.register();
		ModBlocks.registerModBLocks();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModPotions.registerPotions();
		ModBlockEntities.registerBLockEntities();
		ModScreenHandlers.registerScreenHandlers();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			ModColorProviders.registerModColorProviders();
		}

		ModWorldGeneration.generateModWorldGen();

		// ModCommandRegister.registerCommands();
		ModSounds.registerSounds();

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			handlePlayerLogin(handler.getPlayer());
		});

		FabricDefaultAttributeRegistry.register(ModEntities.LOOT_BALLOON, LootBalloonEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SNAIL, SnailEntity.setAttributes());


		// BundleScroll
		BUNDLE_SCROLL_CHANNEL.registerServerbound(ServerMod.BundleScrollPacket.class, (message, access) -> {
			int syncId = message.syncId;
			int revision = message.revision;
			int i = message.slot;
			int amt = message.amount;

			ServerPlayerEntity player = access.player();

			player.updateLastActionTime();
			ScreenHandler screenHandler = player.currentScreenHandler;
			if (screenHandler.syncId != syncId) {
				return;
			}
			if (player.isSpectator()) {
				screenHandler.syncState();
				return;
			}
			if (!screenHandler.canUse(player)) {
				LOGGER.debug("Player {} interacted with invalid menu {}", player, screenHandler);
				return;
			}
			if (!screenHandler.isValid(i)) {
				LOGGER.debug("Player {} clicked invalid slot index: {}, available slots: {}", player.getName(), i, screenHandler.slots.size());
				return;
			}

			boolean flag = revision == player.currentScreenHandler.getRevision();
			screenHandler.disableSyncing();
			Slot slot = screenHandler.getSlot(i);
			ItemStack stack = slot.getStack();
			if (PocketUtil.hasPockets(stack)) {
				PocketUtil.shiftBundle(stack, amt);
			}
			screenHandler.enableSyncing();
			if (flag) {
				screenHandler.updateToClient();
			} else {
				screenHandler.sendContentUpdates();
			}


		});

		// UseEntityCallback.EVENT.register(new VillagerTradingChangeHandler());


		// CrateParticleAnimationSystem.initialize();


		// Check if DH FarmWorld needs to be deleted
		String jsonFilePath = "config/servermod/dh_farmworld.json";
		if (ChangeTimeChecker.checkAndUpdateJSONChangeTime(jsonFilePath)) {
			if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {

				// Delete the DH FarmWorld
				List<String> farmWorlds = List.of(
						"Distant_Horizons_server_data/Minecraft+Server/servermod@@nether",
						"Distant_Horizons_server_data/Minecraft+Server/servermod@@overworld"
				);
				for (String farmWorld : farmWorlds) {
					FolderDeleter.deleteFolder(farmWorld);
				}
			}

			if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
				List<String> farmWorldConfigs = List.of(
						"config/multiworld/worlds/servermod/nether.yml",
						"config/multiworld/worlds/servermod/overworld.yml"
				);
				for (String farmWorldConfig : farmWorldConfigs) {
					YamlSeedUpdater.updateSeed(farmWorldConfig);
				}
				List<String> farmWorldData = List.of(
						"world/dimensions/servermod/nether",
						"world/dimensions/servermod/overworld"
				);
				for (String farmWorldDataDir : farmWorldData) {
					FolderDeleter.deleteFolder(farmWorldDataDir);
				}
			}
		}



		// ------ Portals ------
		CustomPortalBuilder.beginPortal()
				.frameBlock(ModBlocks.PLATINUM_BLOCK)
				.lightWithItem(ModItems.PLATINUM)
				.destDimID(Identifier.of("servermod", "overworld"))
				.tintColor(219,219,219)
				.registerPortal();

		// register Key pressing

		CRATE_REWARD_SCREEN_CHANNEL.registerClientboundDeferred(ServerModClient.CrateScreenPacket.class);
		//BADGER_TASK_CHANNEL.registerClientboundDeferred(ServerModClient.OpenBadgerTaskPacket.class);
		BADGER_TASK_CHANNEL.registerClientboundDeferred(ServerModClient.BadgerTaskPacket.class);

		BADGER_TASK_CHANNEL.registerServerbound(ServerModClient.OpenBadgerTaskPacket.class, (message, access) -> {
			// client opens badger task screen
			// rquest badger tasks from server
			int exampleItemId = Item.getRawId(Items.DIAMOND);
			int[] itemIds = new int[]{exampleItemId, exampleItemId, exampleItemId, exampleItemId, exampleItemId};
			int[] itemCounts = new int[]{41, 13, 12, 11, 11};
			int[] completed = new int[]{0, 0, 1, 0, 1};
			BADGER_TASK_CHANNEL.serverHandle(access.player()).send(new ServerModClient.BadgerTaskPacket(itemIds, itemCounts, completed));
		});

		// ruby store screen
		ServerWorldEvents.LOAD.register((server, world) -> {
			GameRules gameRules = world.getGameRules();
			gameRules.get(GameRules.REDUCED_DEBUG_INFO).set(true, server);
			gameRules.get(GameRules.SEND_COMMAND_FEEDBACK).set(false, server);
		});

		ServerTickEvents.END_SERVER_TICK.register(minecraftServer -> {
			minecraftServer.getPlayerManager().getPlayerList().forEach(playerEntity -> {
				if (Objects.requireNonNull(playerEntity.getServer()).getGameRules().getBoolean(DO_DEATH)
						&& playerEntity.getServerWorld().getDimension().hasCeiling()
						&& !playerEntity.isCreativeLevelTwoOp()) {
					Vec3d v = playerEntity.getPos();
					if (v.y >= 128) {
						DamageSources damageSources = new DamageSources(minecraftServer.getRegistryManager());
						DamageSource outOfWorldDamage = damageSources.outOfWorld();
						playerEntity.damage(outOfWorldDamage, 2f);
					}
				}
			});
		});

		STORE_BUY_CHANNEL.registerServerbound(ServerMod.StorePacket.class, (message, access) -> {
			Item currencyItem = Item.byRawId(message.currencyItem);

			if (message.buyItem != 0) {
				Item buyItem = Item.byRawId(message.buyItem);
				access.player().giveItemStack(new ItemStack(buyItem));

				InventoryUtil.removeItemsFromInventory(access.player().getInventory(), currencyItem, message.cost);
//				int remaining = message.cost;
//				for (final var stack : access.player().getInventory().main) {
//					if (remaining <= 0)
//						break;
//					if (stack.getItem() == currencyItem) {
//						int remove = Math.min(stack.getCount(), remaining);
//						stack.decrement(remove);
//						remaining -= remove;
//					}
//				}
			}
			// update the currency of the client
			int amount = InventoryUtil.countItems(access.player().getInventory(), currencyItem);
			ServerMod.LOGGER.info("Sending currency update to client: " + amount);
			ServerModClient.CurrencyPacket packet = new ServerModClient.CurrencyPacket(Item.getRawId(currencyItem), amount);
			ServerModClient.CURRENCY_CHANNEL.serverHandle(access.player()).send(packet);
		});



	}

	public static void handlePlayerLogin(PlayerEntity player) {
		boolean reassigned = BadgerTaskManager.resetBadgerTasksIfNecessary(player.getUuid());
		if (reassigned) {
//			Text message = Text.of("Your badger tasks have been reassigned.").copy().formatted(Formatting.GREEN);
//			player.sendMessage(message, false);
		}
	}

	public record StorePacket(int cost, int buyItem, int currencyItem) {}
	public record BundleScrollPacket(int syncId, int revision, int slot, int amount) {}


}