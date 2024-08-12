package julian.servermod;

import io.wispforest.owo.network.OwoNetChannel;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.client.LootBalloonRenderer;
import julian.servermod.entity.client.SnailRenderer;
import julian.servermod.item.ModItems;
import julian.servermod.screen.*;
import julian.servermod.sound.ModSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

public class ServerModClient implements ClientModInitializer {
    public static final OwoNetChannel CRATE_REWARD_SCREEN_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "crate_reward_screen"));
    public static final OwoNetChannel CURRENCY_CHANNEL = OwoNetChannel.create(Identifier.of(ServerMod.MOD_ID, "currency"));
    public static KeyBinding storeGuiKey;
    public static KeyBinding rewardGuiKey;

    @Override
    public void onInitializeClient() {
        //HandledScreens.register(ModScreenHandlers.BADGER_TASK_BLOCK_SCREEN_HANDLER, BadgerTaskBlockScreen::new);


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RARE_CHEST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TASK_BOARD_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOOT_VASE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BADGER_CHEST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LEGENDARY_CHEST, RenderLayer.getCutout());

        // Crops
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BANANA_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EGGPLANT_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LETTUCE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINEAPPLE_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHILI_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_CROP, RenderLayer.getCutout());

        //FURNITURE
        //Badger
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MUSHROOM_STOOL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MUSHROOM_LAMPSTAND, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_BENCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_TABLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_STOOL_1, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_STOOL_2, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_STACK_1, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_STACK_2, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LOG_STACK_3, RenderLayer.getCutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FARMING_BANNER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FARMING_BANNER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COMBAT_BANNER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SASH_BANNER_WALL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BADGER_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STALL_CART, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_BEAR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_CART, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_CREEPER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_LLAMA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_PENGUIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_SCARECROW, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_SWORD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWER_COVER_RED_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWER_COVER_PINK_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWER_COVER_BLUE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLOWER_COVER_WHITE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHELF_FUNGUS_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSS_COVER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_MYCENA_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_ORANGE_MYCENA_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOVER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MOSS_HANG_BLOCK, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LEAF_LITTER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COLD_LEAF_LITTER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRY_LEAF_LITTER_BLOCK, RenderLayer.getCutout());


        EntityRendererRegistry.register(ModEntities.LOOT_BALLOON, LootBalloonRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);





        storeGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.servermod.open_store", // Translation key for the keybind name
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J, // Use J key
                "category.servermod.general" // Translation key for the keybind category
        ));
        rewardGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.servermod.open_chest_reward", // Translation key for the keybind name
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K, // Use K key
                "category.servermod.general" // Translation key for the keybind category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (storeGuiKey.wasPressed()) {

                if (client.currentScreen instanceof StoreScreen) {
                    client.setScreen(null);

                } else {
                    client.setScreen(new StoreScreen(client.player));
                    ServerMod.STORE_BUY_CHANNEL.clientHandle().send(new ServerMod.StorePacket(0, 0, Item.getRawId(ModItems.RUBY)));
                    ServerMod.STORE_BUY_CHANNEL.clientHandle().send(new ServerMod.StorePacket(0, 0, Item.getRawId(ModItems.BADGER_COIN)));
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (rewardGuiKey.wasPressed()) {

                if (client.currentScreen instanceof CrateRewardScreen) {
                    client.setScreen(null);
                } else {
                    ItemStack reward = new ItemStack(ModItems.CRATE_KEY_LEGENDARY, 3);
                    client.setScreen(new CrateRewardScreen(reward, ModItems.CRATE_KEY_RARE));
                }
            }
        });

        CRATE_REWARD_SCREEN_CHANNEL.registerClientbound(CrateScreenPacket.class, (message, access) -> {
            ItemStack reward = new ItemStack(Item.byRawId(message.rewardItem), message.rewardItemCount);
            Item crateKeyItem = Item.byRawId(message.crateKeyItem);
            ServerMod.LOGGER.info("receive create screen packet");
            MinecraftClient.getInstance().execute(() ->
                    MinecraftClient.getInstance().setScreen(new CrateRewardScreen(reward, crateKeyItem))
            );
        });

        CURRENCY_CHANNEL.registerClientbound(CurrencyPacket.class, (message, access) -> {
            MinecraftClient.getInstance().execute(() -> {
                MinecraftClient.getInstance().execute(() -> {
                    Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                    if (currentScreen instanceof StoreScreen screen) {
                        Item currencyItem = Item.byRawId(message.currencyItem);
                        screen.updateCurrency(currencyItem, message.amount);
                    }
                });
            });
        });
    }

    public record CrateScreenPacket(int crateKeyItem, int rewardItem, int rewardItemCount) {}
    public record CurrencyPacket(int currencyItem, int amount) {}

}
