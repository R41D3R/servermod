package julian.servermod.item;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup SEVERMOD_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ServerMod.MOD_ID, "servermod"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.servermod"))
                    .icon(() -> new ItemStack(ModItems.RUBY)).entries((displayContext, entries) -> {
                        // Entities
                        entries.add(ModItems.LOOT_BALLOON_SPAWN_EGG);

                        entries.add(ModItems.WateringCan);
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.CUSTOM_LAPIS);
                        entries.add(ModItems.PLATINUM);
                        entries.add(ModItems.SOIL);
                        entries.add(ModItems.FERTILIZER_SPEED);

                        entries.add(ModItems.CRATE_KEY_RARE);
                        entries.add(ModBlocks.RARE_CHEST);
                        entries.add(ModBlocks.BADGER_CHEST);
                        entries.add(ModItems.CRATE_KEY_BADGER);
                        entries.add(ModBlocks.LEGENDARY_CHEST);
                        entries.add(ModItems.CRATE_KEY_LEGENDARY);

                        entries.add(ModBlocks.PLANTER);
                        entries.add(ModBlocks.RUBY_ORE);
                        entries.add(ModBlocks.DEEPSLATE_RUBY_ORE);
                        entries.add(ModBlocks.RUBY_BLOCK);
                        entries.add(ModBlocks.DEEPSLATE_PLATINUM_ORE);
                        entries.add(ModBlocks.PLATINUM_BLOCK);
                        entries.add(ModBlocks.LOOT_VASE_BLOCK);

                        entries.add(ModBlocks.PHOENIX_BLOCK);
                        entries.add(ModItems.ASHES_O_ENCHANTMENT);

                        entries.add(ModBlocks.STYLING_TABLE_MINE);
                        entries.add(ModBlocks.BOULDER_BLOCK);
                        entries.add(ModBlocks.BADGER_TASK_BLOCK);

                        // Crops
                        entries.add(ModItems.CORN);
                        entries.add(ModItems.CORN_SEEDS);
                        entries.add(ModItems.BANANA);
                        entries.add(ModItems.BANANA_SEEDS);
                        entries.add(ModItems.EGGPLANT);
                        entries.add(ModItems.EGGPLANT_SEEDS);
                        entries.add(ModItems.LETTUCE);
                        entries.add(ModItems.LETTUCE_SEEDS);
                        entries.add(ModItems.PINEAPPLE);
                        entries.add(ModItems.PINEAPPLE_SEEDS);
                        entries.add(ModItems.MANGO);
                        entries.add(ModItems.MANGO_SEEDS);
                        entries.add(ModItems.CHILI);
                        entries.add(ModItems.CHILI_SEEDS);
                        entries.add(ModItems.APPLE_SEEDS);

                        // FURNITURE
                        // Badger
                        entries.add(ModBlocks.MUSHROOM_STOOL);
                        entries.add(ModBlocks.MUSHROOM_LAMPSTAND);
                        entries.add(ModBlocks.LOG_BENCH);
                        entries.add(ModBlocks.LOG_TABLE);
                        entries.add(ModBlocks.LOG_STOOL_1);
                        entries.add(ModBlocks.LOG_STOOL_2);
                        entries.add(ModBlocks.LOG_STACK_1);
                        entries.add(ModBlocks.LOG_STACK_2);
                        entries.add(ModBlocks.LOG_STACK_3);
                        entries.add(ModBlocks.FARMING_BANNER);
                        entries.add(ModBlocks.COMBAT_BANNER);
                        entries.add(ModBlocks.SASH_BANNER_WALL);
                        entries.add(ModBlocks.BADGER_PLUSHIE);
                        entries.add(ModBlocks.STALL_CART);
                        entries.add(ModBlocks.WOODEN_BEAR);
                        entries.add(ModBlocks.WOODEN_CART);
                        entries.add(ModBlocks.WOODEN_CREEPER);
                        entries.add(ModBlocks.WOODEN_LLAMA);
                        entries.add(ModBlocks.WOODEN_PENGUIN);
                        entries.add(ModBlocks.WOODEN_SCARECROW);
                        entries.add(ModBlocks.WOODEN_SWORD);

                    }).build());

    public static void registerItemGroups() {
        ServerMod.LOGGER.info("Register Item Groups for " + ServerMod.MOD_ID);
    }
}
