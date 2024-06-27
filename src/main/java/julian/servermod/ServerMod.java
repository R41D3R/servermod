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
import julian.servermod.item.ModItemGroups;
import julian.servermod.item.ModItems;
import julian.servermod.screen.CrateRewardScreen;
import julian.servermod.screen.ModScreenHandlers;
import julian.servermod.screen.StoreScreen;
import julian.servermod.screen.util.BadgerTaskNetworkUtil;
import julian.servermod.screen.util.BoulderNetworkUtil;
import julian.servermod.screen.util.InventoryUtil;
import julian.servermod.sound.ModSounds;
import julian.servermod.utils.playerdata.WalletData;
import julian.servermod.world.gen.ModWorldGeneration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMod implements ModInitializer {

	public static final OwoNetChannel STORE_BUY_CHANNEL = OwoNetChannel.create(new Identifier(ServerMod.MOD_ID, "store_buy"));


	public static final String MOD_ID = "servermod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerModBLocks();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlockEntities.registerBLockEntities();
		ModScreenHandlers.registerScreenHandlers();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			ModColorProviders.registerModColorProviders();
		}

		ModWorldGeneration.generateModWorldGen();

		ModCommandRegister.registerCommands();
		ModSounds.registerSounds();

		BoulderNetworkUtil.init();

		BadgerTaskNetworkUtil.init();


		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			handlePlayerLogin(handler.getPlayer());
		});

		FabricDefaultAttributeRegistry.register(ModEntities.LOOT_BALLOON, LootBalloonEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SNAIL, SnailEntity.setAttributes());


		// UseEntityCallback.EVENT.register(new VillagerTradingChangeHandler());

		StrippableBlockRegistry.register(ModBlocks.MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_LOG);
		StrippableBlockRegistry.register(ModBlocks.MAPLE_WOOD, ModBlocks.STRIPPED_MAPLE_WOOD);

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_MAPLE_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_MAPLE_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLE_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLES_LEAVES_RED, 30, 60);
		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAPLES_LEAVES_ORANGE, 30, 60);

		// CrateParticleAnimationSystem.initialize();

		// register Key pressing

		// ruby store screen



		STORE_BUY_CHANNEL.registerServerbound(ServerMod.StorePacket.class, (message, access) -> {
			Item currencyItem = Item.byRawId(message.currencyItem);

			if (message.buyItem != 0) {
				Item buyItem = Item.byRawId(message.buyItem);
				access.player().giveItemStack(new ItemStack(buyItem));

				WalletData.removeItemCountFromWallet(access.player(), currencyItem, message.cost);
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
			int amount = InventoryUtil.countItemsWallet(access.player(), currencyItem);
			ServerMod.LOGGER.info("Sending currency update to client: " + amount);
			ServerModClient.CurrencyPacket packet = new ServerModClient.CurrencyPacket(Item.getRawId(currencyItem), amount);
			ServerModClient.CURRENCY_CHANNEL.serverHandle(access.player()).send(packet);
		});



	}

	public static void handlePlayerLogin(PlayerEntity player) {
		boolean reassigned = BadgerTaskManager.resetBadgerTasksIfNecessary(player.getUuid());
		if (reassigned) {
			Text message = Text.of("Your badger tasks have been reassigned.").copy().formatted(Formatting.GREEN);
			player.sendMessage(message, false);
		}
	}

	public record StorePacket(int cost, int buyItem, int currencyItem) {}

}