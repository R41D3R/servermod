package julian.servermod;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import io.wispforest.owo.network.OwoNetChannel;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.client.LootBalloonRenderer;
import julian.servermod.entity.client.SnailRenderer;
import julian.servermod.item.ModItems;
import julian.servermod.screen.*;
import julian.servermod.screen.util.BadgerTaskClientNetworkUtil;
import julian.servermod.sound.ModSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
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
    public static KeyBinding storeGuiKey;
    public static KeyBinding rewardGuiKey;
    public static final OwoNetChannel MY_CHANNEL = OwoNetChannel.create(new Identifier(ServerMod.MOD_ID, "main"));


    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.PHOENIX_BLOCK_SCREEN_HANDLER, PhoenixBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.STYLING_TABLE_MINE_HANDLER, StylingTableMineScreen::new);
        HandledScreens.register(ModScreenHandlers.BOULDER_BLOCK_SCREEN_HANDLER, BoulderBlockScreen::new);
        HandledScreens.register(ModScreenHandlers.BADGER_TASK_BLOCK_SCREEN_HANDLER, BadgerTaskBlockScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PHOENIX_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BOULDER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RARE_CHEST, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BADGER_TASK_BLOCK, RenderLayer.getCutout());
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

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LEAF_LITTER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COLD_LEAF_LITTER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRY_LEAF_LITTER_BLOCK, RenderLayer.getCutout());

        BadgerTaskClientNetworkUtil.init();

        EntityRendererRegistry.register(ModEntities.LOOT_BALLOON, LootBalloonRenderer::new);
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);


        // trees
        // saplings
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAPLE_SAPLING, RenderLayer.getCutout());


        // Leaves
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAPLES_LEAVES_RED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAPLES_LEAVES_ORANGE, RenderLayer.getCutout());

        // TODO: Add Textures for Maple Signs
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.MAPLE_SIGN_TEXTURE));
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.MAPLE_HANGING_SIGN_TEXTURE));

        // register Key pressing

        // ruby store screen
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


        MY_CHANNEL.registerServerbound(StorePacket.class, (message, access) -> {
            Item currencyItem = Item.byRawId(message.currencyItem);
            Item buyItem = Item.byRawId(message.buyItem);


            access.player().giveItemStack(new ItemStack(buyItem));

            int remaining = message.cost;
            for (final var stack : access.player().getInventory().main) {
                if (remaining <= 0)
                    break;
                if (stack.getItem() == currencyItem) {
                    int remove = Math.min(stack.getCount(), remaining);
                    stack.decrement(remove);
                    remaining -= remove;
                }
            }
        });
    }

    public record StorePacket(int cost, int buyItem, int currencyItem) {}

}
