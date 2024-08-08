package julian.servermod.block;

import julian.servermod.ServerMod;
import julian.servermod.block.custom.*;
import julian.servermod.block.custom.abstracts.DirectionalBlock;
import julian.servermod.block.custom.abstracts.ThreeTallDirectionalBlock;
import julian.servermod.block.custom.abstracts.WallPlaceableBlock;
import julian.servermod.block.custom.biome.*;
import julian.servermod.block.custom.crop.DailyCropBlock;
import julian.servermod.block.furniture.TaskBoardBlock;
import julian.servermod.sound.ModSounds;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;


public class ModBlocks {
    public static final Block PLANTER = registerBlock("planter",
            new PlanterBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.GOLD_ORE)));

    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_GOLD_ORE)));

    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.EMERALD_BLOCK)));

    public static final Block DEEPSLATE_PLATINUM_ORE = registerBlock("deepslate_platinum_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE)));

    public static final Block PLATINUM_BLOCK = registerBlock("platinum_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)));


    public static final Block RARE_CHEST = registerBlock("rare_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block BADGER_CHEST = registerBlock("badger_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LEGENDARY_CHEST = registerBlock("legendary_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));


    public static final Block LOOT_VASE_BLOCK = registerBlock("loot_vase_block",
            new LootVaseBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque().sounds(ModSounds.LOOT_VASE_BLOCK_SOUNDS)));

    public static final Block BALLOON_CRATE_BLOCK = registerBlock("balloon_crate_block",
            new BalloonLootCrateBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));




    // PLANTS
    public static final Block SNAIL_PEBBLES_BLOCK = registerBlock("snail_pebbles_block",
            new SnailPebble(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.STONE).nonOpaque()));

    public static final Block SNAIL_ROCKS_BLOCK = registerBlock("snail_rocks_block",
            new SnailPebble(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.STONE).nonOpaque()));

    public static final Block PEBBLES_BLOCK = registerBlock("pebbles_block",
            new Pebble(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.STONE).nonOpaque()));

    public static final Block ROCKS_BLOCK = registerBlock("rocks_block",
            new Pebble(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).sounds(BlockSoundGroup.STONE).nonOpaque()));

    public static final Block LEAF_LITTER_BLOCK = registerBlock("leaf_litter_block",
            new LitterBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS).noCollision()));

    public static final Block COLD_LEAF_LITTER_BLOCK = registerBlock("cold_leaf_litter_block",
            new LitterBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS).noCollision()));

    public static final Block DRY_LEAF_LITTER_BLOCK = registerBlock("dry_leaf_litter_block",
            new LitterBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS).noCollision()));


    public static final Block FLOWER_COVER_WHITE_BLOCK = registerBlock("flower_cover_white_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)));

    public static final Block FLOWER_COVER_BLUE_BLOCK = registerBlock("flower_cover_blue_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)));

    public static final Block FLOWER_COVER_PINK_BLOCK = registerBlock("flower_cover_pink_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)));

    public static final Block FLOWER_COVER_RED_BLOCK = registerBlock("flower_cover_red_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)));

    public static final Block MOSS_COVER_BLOCK = registerBlock("moss_cover_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS).sounds(BlockSoundGroup.MOSS_CARPET)));

    public static final Block SHELF_FUNGUS_BLOCK = registerBlock("shelf_fungus_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.PINK_PETALS)));

    public static final Block MOSS_HANG_BLOCK = registerBlock("moss_hang_block",
            new MossHangBlock(FabricBlockSettings.copyOf(Blocks.SPORE_BLOSSOM).sounds(BlockSoundGroup.SPORE_BLOSSOM)));




    public static final Block ORANGE_MYCENA_BLOCK = registerBlock("orange_mycena_block",
            new CustomFernBlock(FabricBlockSettings.copyOf(Blocks.FERN)));

    public static final Block LARGE_ORANGE_MYCENA_BLOCK = registerBlock("large_orange_mycena_block",
            new TallPlantBlock(FabricBlockSettings.copyOf(Blocks.LARGE_FERN)));

    public static final Block CLOVER_BLOCK = registerBlock("clover_block",
            new CoverBlock(FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)));


    // CROPS

    public static final Block MANGO_CROP = registerBlock("mango_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 5));

    public static final Block CORN_CROP = registerBlock("corn_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 4));

    public static final Block EGGPLANT_CROP = registerBlock("eggplant_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 3));

    public static final Block LETTUCE_CROP = registerBlock("lettuce_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 2));

    // NOT ACTIVE CROPS

    public static final Block BANANA_CROP = registerBlock("banana_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 5));

    public static final Block PINEAPPLE_CROP = registerBlock("pineapple_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 4));

    public static final Block CHILI_CROP = registerBlock("chili_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 2));

    // TODO: Remove apples from loot tables
    public static final Block APPLE_CROP = registerBlock("apple_crop",
            new DailyCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT), 5));

    //
    // FURNITURE
    //

    public static final Block TASK_BOARD_BLOCK = registerBlock("task_board_block",
            new TaskBoardBlock(FabricBlockSettings.copyOf(Blocks.FLOWER_POT).nonOpaque()));

    // BADGER
    public static final Block MUSHROOM_STOOL = registerBlock("mushroom_stool",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block MUSHROOM_LAMPSTAND = registerBlock("mushroom_lampstand",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_BENCH = registerBlock("log_bench",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_TABLE = registerBlock("log_table",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_STOOL_1 = registerBlock("log_stool_1",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_STOOL_2 = registerBlock("log_stool_2",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_STACK_1 = registerBlock("log_stack_1",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_STACK_2 = registerBlock("log_stack_2",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LOG_STACK_3 = registerBlock("log_stack_3",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

//    public static final Block FARMING_BANNER_BLOCK = registerBlock("farming_banner",
//            new BannerBlock(DyeColor.GRAY, FabricBlockSettings.copyOf(Blocks.WHITE_BANNER)));

    public static final Block FARMING_BANNER = registerBlock("farming_banner",
            new WallPlaceableBlock(FabricBlockSettings.copyOf(Blocks.WHITE_BANNER)));

    public static final Block COMBAT_BANNER = registerBlock("combat_banner",
            new WallPlaceableBlock(FabricBlockSettings.copyOf(Blocks.WHITE_BANNER)));

    public static final Block SASH_BANNER_WALL = registerBlock("sash_banner_wall",
            new WallPlaceableBlock(FabricBlockSettings.copyOf(Blocks.WHITE_BANNER)));

    public static final Block BADGER_PLUSHIE = registerBlock("badger_plushie",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block STALL_CART = registerBlock("stall_cart",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_BEAR = registerBlock("wooden_bear",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_CART = registerBlock("wooden_cart",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_CREEPER = registerBlock("wooden_creeper",
            new ThreeTallDirectionalBlock(FabricBlockSettings.copyOf(Blocks.FLOWER_POT).nonOpaque()));

    public static final Block WOODEN_LLAMA = registerBlock("wooden_llama",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_PENGUIN = registerBlock("wooden_penguin",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_SCARECROW = registerBlock("wooden_scarecrow",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block WOODEN_SWORD = registerBlock("wooden_sword",
            new DirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(ServerMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(ServerMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBLocks() {
        ServerMod.LOGGER.info("Registering ModBLocks for " + ServerMod.MOD_ID);
    }
}
