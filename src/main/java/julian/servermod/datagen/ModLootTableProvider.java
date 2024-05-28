package julian.servermod.datagen;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RUBY_BLOCK);
        addDrop(ModBlocks.PLATINUM_BLOCK);
        addDrop(ModBlocks.PLANTER);

        addDrop(ModBlocks.RUBY_ORE, copperLikeOreDrops(ModBlocks.RUBY_ORE, ModItems.RUBY));
        addDrop(ModBlocks.DEEPSLATE_RUBY_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_RUBY_ORE, ModItems.RUBY));
        addDrop(ModBlocks.DEEPSLATE_PLATINUM_ORE, diamondLikeOreDrops(ModBlocks.DEEPSLATE_PLATINUM_ORE, ModItems.PLATINUM));
        addDrop(ModBlocks.LOOT_VASE_BLOCK, lootVaseBlockDrops(ModBlocks.LOOT_VASE_BLOCK, ModItems.RUBY));

        // TODO: Add Crop LOOT

        // Furniture
        // Badger
        addDrop(ModBlocks.MUSHROOM_STOOL);
        addDrop(ModBlocks.MUSHROOM_LAMPSTAND);
        addDrop(ModBlocks.LOG_TABLE);
        addDrop(ModBlocks.LOG_BENCH);
        addDrop(ModBlocks.LOG_STOOL_1);
        addDrop(ModBlocks.LOG_STOOL_2);
        addDrop(ModBlocks.LOG_STACK_1);
        addDrop(ModBlocks.LOG_STACK_2);
        addDrop(ModBlocks.LOG_STACK_3);
        addDrop(ModBlocks.FARMING_BANNER);
        addDrop(ModBlocks.COMBAT_BANNER);
        addDrop(ModBlocks.SASH_BANNER_WALL);
        addDrop(ModBlocks.BADGER_PLUSHIE);
        addDrop(ModBlocks.STALL_CART);
        addDrop(ModBlocks.WOODEN_BEAR);
        addDrop(ModBlocks.WOODEN_CART);
        addDrop(ModBlocks.WOODEN_CREEPER);
        addDrop(ModBlocks.WOODEN_LLAMA);
        addDrop(ModBlocks.WOODEN_PENGUIN);
        addDrop(ModBlocks.WOODEN_SCARECROW);
        addDrop(ModBlocks.WOODEN_SWORD);
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                ((LeafEntry.Builder)
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    public LootTable.Builder diamondLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                ((LeafEntry.Builder)
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider.create(1.0f, 1.0f))))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    public LootTable.Builder lootVaseBlockDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                ((LeafEntry.Builder)
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction
                                        .builder(UniformLootNumberProvider.create(3.0f, 7.0f))))
                        ));
    }
}
