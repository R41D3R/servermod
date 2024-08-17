package julian.servermod.color;

import julian.servermod.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import julian.servermod.ServerMod;


@Environment(EnvType.CLIENT)
public class ModColorProviders {

    public static void registerBlockColorProvider(Block block, int tintIndex, int color) {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tint) -> {
            if (tint == tintIndex) {
                return color;
            }
            return -1; // Return -1 if tintIndex doesn't match, meaning no color applied
        }, block);

        // Register item color provider for the corresponding BlockItem
        Item blockItem = Item.fromBlock(block);
        if (blockItem != null) {
            registerItemColorProvider(blockItem, tintIndex, color);
        }
    }

    public static void registerItemColorProvider(Item item, int tintIndex, int color) {
        ColorProviderRegistry.ITEM.register((stack, tint) -> {
            if (tint == tintIndex) {
                return color;
            }
            return -1; // Return -1 if tintIndex doesn't match, meaning no color applied
        }, item);
    }

    public static void registerModColorProviders() {
        ServerMod.LOGGER.info("Registering Mod Color Providers for " + ServerMod.MOD_ID);
        // Register your blocks with their respective color providers here
        // Example:
        // registerBlockColorProvider(ModBlocks.SOME_BLOCK, 0, 0x3495eb);
//        registerBlockColorProvider(ModBlocks.MUSHROOM_LAMPSTAND, 1, 0x722F37);
//        registerBlockColorProvider(ModBlocks.MUSHROOM_STOOL, 1, 0x722F37);
//        registerBlockColorProvider(ModBlocks.WOODEN_SWORD, 1, 0x424e3c);
//        registerBlockColorProvider(ModBlocks.WOODEN_LLAMA, 1, 0x6e7c46);
//        registerBlockColorProvider(ModBlocks.WOODEN_BEAR, 1, 0x869755);
        registerBlockColorProvider(ModBlocks.MUSHROOM_LAMPSTAND, 1, 0xbc3e39);
        registerBlockColorProvider(ModBlocks.MUSHROOM_STOOL, 1, 0xbc3e39);
        registerBlockColorProvider(ModBlocks.SASH_BANNER_WALL, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.FARMING_BANNER, 1, 0xfdbf3b);
        registerBlockColorProvider(ModBlocks.COMBAT_BANNER, 1, 0xc72b1d);

        registerBlockColorProvider(ModBlocks.WOODEN_SWORD, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.WOODEN_LLAMA, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.WOODEN_CREEPER, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.WOODEN_BEAR, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.WOODEN_PENGUIN, 1, 0x5a6539);
        registerBlockColorProvider(ModBlocks.WOODEN_SCARECROW, 1, 0x5a6539);

        registerBlockColorProvider(ModBlocks.TIGER_PLUSHIE, 1, 0xa83e2f);
        registerBlockColorProvider(ModBlocks.SAND_CASTLE, 1, 0x2287ae);
        registerBlockColorProvider(ModBlocks.GARDEN_GNOME, 1, 0xB20E0E);
        registerBlockColorProvider(ModBlocks.FLOWER_BASKET, 1, 0xD9730C);


    }
}
