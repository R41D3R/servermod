package julian.servermod.datagen;

import julian.servermod.block.ModBlocks;
import julian.servermod.block.custom.crop.DailyCropBlock;
import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        // BIOME
        addDrop(ModBlocks.PEBBLES_BLOCK, ModItems.PEBBLES_ITEM);
        addDrop(ModBlocks.ROCKS_BLOCK, ModItems.ROCKS_ITEM);
        addDrop(ModBlocks.SNAIL_PEBBLES_BLOCK, ModItems.PEBBLES_ITEM);
        addDrop(ModBlocks.SNAIL_ROCKS_BLOCK, ModItems.ROCKS_ITEM);

        addDrop(ModBlocks.LEAF_LITTER_BLOCK, ModItems.LEAF_LITTER);
        addDrop(ModBlocks.COLD_LEAF_LITTER_BLOCK, ModItems.COLD_LEAF_LITTER);
        addDrop(ModBlocks.DRY_LEAF_LITTER_BLOCK, ModItems.DRY_LEAF_LITTER);

        addDrop(ModBlocks.FLOWER_COVER_WHITE_BLOCK, ModItems.FLOWER_COVER_WHITE);
        addDrop(ModBlocks.FLOWER_COVER_BLUE_BLOCK, ModItems.FLOWER_COVER_BLUE);
        addDrop(ModBlocks.FLOWER_COVER_PINK_BLOCK, ModItems.FLOWER_COVER_PINK);
        addDrop(ModBlocks.FLOWER_COVER_RED_BLOCK, ModItems.FLOWER_COVER_RED);
        addDrop(ModBlocks.MOSS_COVER_BLOCK, ModItems.MOSS);
        addDrop(ModBlocks.MOSS_HANG_BLOCK, ModItems.MOSS);
        addDrop(ModBlocks.SHELF_FUNGUS_BLOCK, ModItems.SHELF_FUNGUS);

        addDrop(ModBlocks.ORANGE_MYCENA_BLOCK, ModItems.ORANGE_MYCENA);
        //addDrop(ModBlocks.LARGE_ORANGE_MYCENA_BLOCK, ModItems.LARGE_ORANGE_MYCENA);
        addDrop(ModBlocks.LARGE_ORANGE_MYCENA_BLOCK, tallGrassDrops(ModBlocks.LARGE_ORANGE_MYCENA_BLOCK, ModItems.LARGE_ORANGE_MYCENA, ModItems.ORANGE_MYCENA));
        addDrop(ModBlocks.CLOVER_BLOCK, ModItems.CLOVER);

        addDrop(ModBlocks.RUBY_BLOCK);
        // addDrop(ModBlocks.PLATINUM_BLOCK);
        addDrop(ModBlocks.PLANTER);

        addDrop(ModBlocks.RUBY_ORE, copperLikeOreDrops(ModBlocks.RUBY_ORE, ModItems.RUBY));
        addDrop(ModBlocks.DEEPSLATE_RUBY_ORE, copperLikeOreDrops(ModBlocks.DEEPSLATE_RUBY_ORE, ModItems.RUBY));
        addDrop(ModBlocks.DEEPSLATE_PLATINUM_ORE, diamondLikeOreDrops(ModBlocks.DEEPSLATE_PLATINUM_ORE, ModItems.PLATINUM));
        addDrop(ModBlocks.LOOT_VASE_BLOCK, lootVaseBlockDrops(ModBlocks.LOOT_VASE_BLOCK, Items.EMERALD));
        addDrop(ModBlocks.BALLOON_CRATE_BLOCK, lootVaseBlockDrops(ModBlocks.BALLOON_CRATE_BLOCK, ModItems.RUBY));

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

        // Crops
        addDrop(ModBlocks.CORN_CROP, customCropDrops(ModBlocks.CORN_CROP, ModItems.CORN, ModItems.CORN_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.CORN_CROP, ModItems.CORN)));
        addDrop(ModBlocks.APPLE_CROP, customCropDrops(ModBlocks.APPLE_CROP, Items.APPLE, ModItems.APPLE_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.APPLE_CROP, Items.APPLE)));
        addDrop(ModBlocks.BANANA_CROP, customCropDrops(ModBlocks.BANANA_CROP, ModItems.BANANA, ModItems.BANANA_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.BANANA_CROP, ModItems.BANANA)));
        addDrop(ModBlocks.MANGO_CROP, customCropDrops(ModBlocks.MANGO_CROP, ModItems.MANGO, ModItems.MANGO_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.MANGO_CROP, ModItems.MANGO)));
        addDrop(ModBlocks.PINEAPPLE_CROP, customCropDrops(ModBlocks.PINEAPPLE_CROP, ModItems.PINEAPPLE, ModItems.PINEAPPLE_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.PINEAPPLE_CROP, ModItems.PINEAPPLE)));
        addDrop(ModBlocks.EGGPLANT_CROP, customCropDrops(ModBlocks.EGGPLANT_CROP, ModItems.EGGPLANT, ModItems.EGGPLANT_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.EGGPLANT_CROP, ModItems.EGGPLANT)));
        addDrop(ModBlocks.LETTUCE_CROP, customCropDrops(ModBlocks.LETTUCE_CROP, ModItems.LETTUCE, ModItems.LETTUCE_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.LETTUCE_CROP, ModItems.LETTUCE)));
        addDrop(ModBlocks.CHILI_CROP, customCropDrops(ModBlocks.CHILI_CROP, ModItems.CHILI, ModItems.CHILI_SEEDS, cropBlockLikeDrop((DailyCropBlock) ModBlocks.CHILI_CROP, ModItems.CHILI)));
    }

    public LootTable.Builder tallGrassDrops(Block tallGrass, Item tallgrass_item, Item grass_item) {
        // Build loot table with two pools
        return LootTable.builder()
                .pool(LootPool.builder()
                        .with(ItemEntry.builder(grass_item).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f))).conditionally(WITH_SHEARS)
                                .alternatively(ItemEntry.builder(tallgrass_item).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)))
                        .conditionally(BlockStatePropertyLootCondition.builder(tallGrass)
                                .properties(StatePredicate.Builder.create().exactMatch(TallPlantBlock.HALF, DoubleBlockHalf.LOWER))))));
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    public LootTable.Builder diamondLikeOreDrops(Block drop, Item item) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 3.0F)))
                                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    public LootTable.Builder lootVaseBlockDrops(Block drop, Item item) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 5.0F)))
                )
        );
    }

    public BlockStatePropertyLootCondition.Builder cropBlockLikeDrop(DailyCropBlock block, Item item) {
        return BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(DailyCropBlock.AGE, block.MAX_AGE));
    }

    public LootTable.Builder customCropDrops(Block crop, Item product, Item seeds, LootCondition.Builder maxAgeCondition) {
        DailyCropBlock dailyCropBlock = (DailyCropBlock) crop;

        // Define condition for non-max age (any age other than max age)
        LootCondition.Builder notMaxAgeCondition = BlockStatePropertyLootCondition.builder(dailyCropBlock)
                .properties(StatePredicate.Builder.create().exactMatch(DailyCropBlock.AGE, dailyCropBlock.MAX_AGE));

        // Define the inverse condition (non-max age)
        LootCondition.Builder nonMaxAgeCondition = BlockStatePropertyLootCondition.builder(dailyCropBlock)
                .properties(StatePredicate.Builder.create().exactMatch(DailyCropBlock.AGE, dailyCropBlock.MAX_AGE)).invert();

        return this.applyExplosionDecay(crop, LootTable.builder()
                // Pool for dropping the product at max age
                .pool(LootPool.builder()
                        .conditionally(maxAgeCondition)
                        .with(ItemEntry.builder(product)))
                // Pool for dropping seeds at non-max age
                .pool(LootPool.builder()
                        .conditionally(nonMaxAgeCondition)
                        .with(ItemEntry.builder(seeds))));
    }
}
