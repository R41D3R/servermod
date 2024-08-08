package julian.servermod.utils;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public class AllCustomLootTables {

    public static final CustomLootTable URN_LOOT_TABLE = new CustomLootTable(
            List.of(// Common loot
                    new ItemStackAbstract(Items.IRON_INGOT, 5),
                    new ItemStackAbstract(Items.COAL, 5)
            ), List.of(// Rare loot
            new ItemStackAbstract(Items.GOLD_INGOT, 5),
            new ItemStackAbstract(Items.DIAMOND, 1)
            ), List.of(// Epic loot
            new ItemStackAbstract(ModItems.PLATINUM, 5),
            new ItemStackAbstract(Items.NETHERITE_INGOT, 1)
            ), List.of(// Legendary loot
            new ItemStackAbstract(ModItems.CRATE_KEY_RARE, 1)
            ), 0.6f, 0.3f, 0.15f, 0.05f);

    public static final CustomLootTable CRATE_KEY_RARE_LOOT_TABLE = new CustomLootTable(
            List.of(// Common loot
                    new ItemStackAbstract(Items.IRON_INGOT, 5),
                    new ItemStackAbstract(Items.COAL, 5)
            ), List.of(// Rare loot
            new ItemStackAbstract(Items.GOLD_INGOT, 5),
            new ItemStackAbstract(Items.DIAMOND, 1)
    ), List.of(// Epic loot
            new ItemStackAbstract(ModItems.PLATINUM, 5),
            new ItemStackAbstract(Items.NETHERITE_INGOT, 1)
    ), List.of(// Legendary loot
            new ItemStackAbstract(ModItems.CRATE_KEY_RARE, 1),
            new ItemStackAbstract(Items.NETHERITE_INGOT, 1)
    ), 0.6f, 0.3f, 0.15f, 0.05f);

//    public static final CustomLootTable CRATE_KEY_BADGER_LOOT_TABLE = new CustomLootTable(
//            List.of(new ItemStackAbstract(ModBlocks.LOG_BENCH, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_TABLE, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_STOOL_1, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_STOOL_2, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_STACK_1, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_STACK_2, 1),
//                    new ItemStackAbstract(ModBlocks.LOG_STACK_3, 1),
//                    new ItemStackAbstract(ModBlocks.PLANTER, 1),
//                    new ItemStackAbstract(ModItems.RUBY, 16),
//                    new ItemStackAbstract(ModItems.PLATINUM, 1)
//            ),
//            List.of(
//                    new ItemStackAbstract(ModBlocks.FARMING_BANNER, 1),
//                    new ItemStackAbstract(ModBlocks.COMBAT_BANNER, 1),
//                    new ItemStackAbstract(ModBlocks.SASH_BANNER_WALL, 1),
//                    new ItemStackAbstract(ModBlocks.BADGER_PLUSHIE, 1),
//                    new ItemStackAbstract(ModBlocks.MUSHROOM_STOOL, 1),
//                    new ItemStackAbstract(ModBlocks.MUSHROOM_LAMPSTAND, 1),
//                    new ItemStackAbstract(ModBlocks.PLANTER, 3),
//                    new ItemStackAbstract(ModItems.RUBY, 32),
//                    new ItemStackAbstract(ModItems.RUBY, 64),
//                    new ItemStackAbstract(ModItems.PLATINUM, 3)
//
//            ),
//            List.of(new ItemStackAbstract(ModItems.CRATE_KEY_BADGER, 2),
//                    new ItemStackAbstract(ModItems.CRATE_KEY_RARE, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_CART, 1),
//                    new ItemStackAbstract(ModBlocks.STALL_CART, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_CREEPER, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_PENGUIN, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_SCARECROW, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_SWORD, 1),
//                    new ItemStackAbstract(ModBlocks.PLANTER, 6)
//                    ),
//            List.of(
//                    new ItemStackAbstract(ModItems.CRATE_KEY_LEGENDARY, 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:bow_ivy"), 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:pickaxe_ivy"), 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:axe_ivy"), 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:hoe_ivy"), 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:shovel_ivy"), 1),
//                    new ItemStackAbstract(createVanityDesign("test_pack:sword_ivy"), 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_LLAMA, 1),
//                    new ItemStackAbstract(ModBlocks.WOODEN_BEAR, 1),
//                    new ItemStackAbstract(ModBlocks.BADGER_CHEST, 1)
//            ),
//            30, 20, 10, 3
//    );

    public static NbtCompound createVanityDesign(String designName) {
        String id = "vanity:design";
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", id);
        nbt.putByte("Count", (byte) 1);
        NbtCompound tag = new NbtCompound();
        NbtCompound design = new NbtCompound();
        design.putString("Style", "default");
        design.putString("Id", designName);
        tag.put("Design", design);
        nbt.put("tag", tag);
        return nbt;
    }

    public static class ItemStackAbstract {
        public final Item item;
        public final int count;
        public final NbtCompound nbt;
        public final Block block;

        public ItemStackAbstract(Item item, int count) {
            this.item = item;
            this.count = count;
            this.nbt = null;
            this.block = null;
        }

        public ItemStackAbstract(Block block, int count) {
            this.block = block;
            this.count = count;
            this.nbt = null;
            this.item = null;
        }

        public ItemStackAbstract(NbtCompound nbt, int count) {
            this.nbt = nbt;
            this.count = count;
            this.item = null;
            this.block = null;
        }
    }
}
