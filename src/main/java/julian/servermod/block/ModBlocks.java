package julian.servermod.block;

import julian.servermod.ServerMod;
import julian.servermod.block.custom.*;
import julian.servermod.block.custom.abstracts.DirectionalBlock;
import julian.servermod.block.custom.abstracts.ThreeTallDirectionalBlock;
import julian.servermod.block.custom.abstracts.WallPlaceableBlock;
import julian.servermod.block.custom.crop.DailyCropBlock;
import julian.servermod.sound.ModSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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

    public static final Block PHOENIX_BLOCK = registerBlock("phoenix_block",
            new PhoenixBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque()));

    public static final Block STYLING_TABLE_MINE = registerBlock("styling_table_mine",
            new StylingTableMine(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).nonOpaque()));

    public static final Block BOULDER_BLOCK = registerBlock("boulder_block",
            new BoulderBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque()));

    public static final Block RARE_CHEST = registerBlock("rare_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block BADGER_CHEST = registerBlock("badger_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block LEGENDARY_CHEST = registerBlock("legendary_chest",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    public static final Block BADGER_TASK_BLOCK = registerBlock("badger_task_block",
            new BadgerTaskBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).nonOpaque()));

    public static final Block LOOT_VASE_BLOCK = registerBlock("loot_vase_block",
            new LootVaseBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque().sounds(ModSounds.LOOT_VASE_BLOCK_SOUNDS)));

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
            new ThreeTallDirectionalBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

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
        return Registry.register(Registries.BLOCK, new Identifier(ServerMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ServerMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBLocks() {
        ServerMod.LOGGER.info("Registering ModBLocks for " + ServerMod.MOD_ID);
    }
}
