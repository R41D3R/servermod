package julian.servermod;

import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.entity.ModBlockEntities;
import julian.servermod.color.ModColorProviders;
import julian.servermod.command.ModCommandRegister;
import julian.servermod.item.ModItemGroups;
import julian.servermod.item.ModItems;
import julian.servermod.screen.ModScreenHandlers;
import julian.servermod.screen.util.BadgerTaskNetworkUtil;
import julian.servermod.screen.util.BoulderNetworkUtil;
import julian.servermod.sound.ModSounds;
import julian.servermod.world.gen.ModWorldGeneration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMod implements ModInitializer {

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


	}

	public static void handlePlayerLogin(PlayerEntity player) {
		boolean reassigned = BadgerTaskManager.resetBadgerTasksIfNecessary(player.getUuid());
		if (reassigned) {
			Text message = Text.of("Your badger tasks have been reassigned.").copy().formatted(Formatting.GREEN);
			player.sendMessage(message, false);
		}
	}
}