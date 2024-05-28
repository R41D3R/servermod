package julian.servermod.utils;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;

public class AllCustomLootTables {

    public static final CustomLootTable URN_LOOT_TABLE = new CustomLootTable(
            List.of(// Common loot
                    new ItemStack(Items.IRON_INGOT, 5),
                    new ItemStack(Items.COAL, 5)
            ), List.of(// Rare loot
                    new ItemStack(Items.GOLD_INGOT, 5),
                    new ItemStack(Items.DIAMOND, 1)
            ), List.of(// Epic loot
                    new ItemStack(ModItems.PLATINUM, 5),
                    new ItemStack(Items.NETHERITE_INGOT, 1)
            ), List.of(// Legendary loot
                    new ItemStack(ModItems.CRATE_KEY_RARE, 1)
            ), 0.6f, 0.3f, 0.15f, 0.05f);

    public static final CustomLootTable CRATE_KEY_RARE_LOOT_TABLE = new CustomLootTable(
            List.of(// Common loot
                    new ItemStack(Items.IRON_INGOT, 5),
                    new ItemStack(Items.COAL, 5)
            ), List.of(// Rare loot
            new ItemStack(Items.GOLD_INGOT, 5),
            new ItemStack(Items.DIAMOND, 1)
    ), List.of(// Epic loot
            new ItemStack(ModItems.PLATINUM, 5),
            new ItemStack(Items.NETHERITE_INGOT, 1)
    ), List.of(// Legendary loot
            new ItemStack(ModItems.CRATE_KEY_RARE, 1),
            new ItemStack(Items.NETHERITE_INGOT, 1)
    ), 0.6f, 0.3f, 0.15f, 0.05f);

    public static final CustomLootTable CRATE_KEY_BADGER_LOOT_TABLE = new CustomLootTable(
            List.of(new ItemStack(ModBlocks.LOG_BENCH, 1),
                    new ItemStack(ModBlocks.LOG_TABLE, 1),
                    new ItemStack(ModBlocks.LOG_STOOL_1, 1),
                    new ItemStack(ModBlocks.LOG_STOOL_2, 1),
                    new ItemStack(ModBlocks.LOG_STACK_1, 1),
                    new ItemStack(ModBlocks.LOG_STACK_2, 1),
                    new ItemStack(ModBlocks.LOG_STACK_3, 1),
                    new ItemStack(ModBlocks.PLANTER, 1),
                    new ItemStack(ModItems.RUBY, 16),
                    new ItemStack(ModItems.PLATINUM, 1)
            ),
            List.of(
                    new ItemStack(ModBlocks.FARMING_BANNER, 1),
                    new ItemStack(ModBlocks.COMBAT_BANNER, 1),
                    new ItemStack(ModBlocks.SASH_BANNER_WALL, 1),
                    new ItemStack(ModBlocks.BADGER_PLUSHIE, 1),
                    new ItemStack(ModBlocks.MUSHROOM_STOOL, 1),
                    new ItemStack(ModBlocks.MUSHROOM_LAMPSTAND, 1),
                    new ItemStack(ModBlocks.PLANTER, 3),
                    new ItemStack(ModItems.RUBY, 32),
                    new ItemStack(ModItems.RUBY, 64),
                    new ItemStack(ModItems.PLATINUM, 3)

            ),
            List.of(new ItemStack(ModItems.CRATE_KEY_BADGER, 2),
                    new ItemStack(ModItems.CRATE_KEY_RARE, 1),
                    new ItemStack(ModBlocks.WOODEN_CART, 1),
                    new ItemStack(ModBlocks.STALL_CART, 1),
                    new ItemStack(ModBlocks.WOODEN_CREEPER, 1),
                    new ItemStack(ModBlocks.WOODEN_PENGUIN, 1),
                    new ItemStack(ModBlocks.WOODEN_SCARECROW, 1),
                    new ItemStack(ModBlocks.WOODEN_SWORD, 1),
                    new ItemStack(ModBlocks.PLANTER, 6)
                    ),
            List.of(
                    new ItemStack(ModItems.CRATE_KEY_LEGENDARY, 1),
                    createVanityDesign("test_pack:bow_ivy"),
                    createVanityDesign("test_pack:pickaxe_ivy"),
                    createVanityDesign("test_pack:axe_ivy"),
                    createVanityDesign("test_pack:hoe_ivy"),
                    createVanityDesign("test_pack:shovel_ivy"),
                    createVanityDesign("test_pack:sword_ivy"),
                    new ItemStack(ModBlocks.WOODEN_LLAMA, 1),
                    new ItemStack(ModBlocks.WOODEN_BEAR, 1),
                    new ItemStack(ModBlocks.BADGER_CHEST, 1)
            ),
            30, 20, 10, 3
    );

    public static ItemStack createVanityDesign(String designName) {
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
        return ItemStack.fromNbt(nbt);
    }
}
