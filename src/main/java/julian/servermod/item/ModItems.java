package julian.servermod.item;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.ModEntities;
import julian.servermod.item.custom.*;
import julian.servermod.item.custom.biome.MossItem;
import julian.servermod.item.custom.biome.PebbleItem;
import julian.servermod.item.custom.biome.RocksItem;
import julian.servermod.item.custom.cratekeys.CrateKeyBadger;
import julian.servermod.item.custom.cratekeys.CrateKeyLegendary;
import julian.servermod.item.custom.cratekeys.CrateKeyRare;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

// registers all Item
public class ModItems {

    // Tools and Armor
    public static final Item ANCIENT_PICKAXE = registerItem("ancient_pickaxe",
            new PickaxeItem(ModToolMaterial.ANCIENT, new Item.Settings()));

    // Entities
    public static final Item LOOT_BALLOON_SPAWN_EGG = registerItem("loot_balloon_spawn_egg",
            new SpawnEggItem(ModEntities.LOOT_BALLOON, 0xD57E57, 0x100000,
                    new Item.Settings()));



//    public static final Item CAPTURE_NET = registerItem("capture_net",
//            new CaptureNet(new Item.Settings().maxDamage(3)));


    public static final Item BADGER_CLUB_ID = registerItem("badger_club_id",
            new BadgerClubId(new Item.Settings().maxCount(1)));



    public static final Item WATERING_CAN = registerItem("wooden_watering_can",
            new WateringCan(new Item.Settings().maxDamage(6)));

    public static final Item BADGER_COIN = registerItem("badger_coin",
            new Item(new Item.Settings()));

    public static final Item RUBY = registerItem("ruby",
            new Item(new Item.Settings()));

    public static final Item CUSTOM_LAPIS = registerItem("custom_lapis",
            new Item(new Item.Settings()));

    public static final Item PLATINUM = registerItem("platinum",
            new Item(new Item.Settings()));

    public static final Item SOIL = registerItem("soil",
            new Soil(new Item.Settings()));

    public static final Item FERTILIZER_SPEED = registerItem("fertilizer_speed",
            new FertilizerSpeed(new Item.Settings()));

    public static final Item ASHES_O_ENCHANTMENT = registerItem("ashes_o_enchantment",
            new Item(new Item.Settings()));

    public static final Item PHOENIX_FEATHER = registerItem("phoenix_feather",
            new Item(new Item.Settings()));

    public static final Item CRATE_KEY_RARE = registerItem("crate_key_rare",
            new CrateKeyRare(new Item.Settings()));

    public static final Item CRATE_KEY_BADGER = registerItem("crate_key_badger",
            new CrateKeyBadger(new Item.Settings()));

    public static final Item CRATE_KEY_LEGENDARY = registerItem("crate_key_legendary",
            new CrateKeyLegendary(new Item.Settings()));

    // ANIMALS

    public static final Item SNAIL_SHELL = registerItem("snail_shell",
            new Item(new Item.Settings()));

    public static final Item SNAIL = registerItem("snail",
            new SnailItem(new Item.Settings().maxCount(1)));

    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            new SpawnEggItem(ModEntities.SNAIL, 0xD57E57, 0x100000,
                    new Item.Settings()));


    // BIOME DECORATION

    public static final Item PEBBLES_ITEM = registerItem("pebbles",
            new PebbleItem(ModBlocks.PEBBLES_BLOCK, new Item.Settings()));

    public static final Item ROCKS_ITEM = registerItem("rocks",
            new RocksItem(ModBlocks.ROCKS_BLOCK, new Item.Settings()));

    public static final Item LEAF_LITTER = registerItem("leaves",
            new AliasedBlockItem(ModBlocks.LEAF_LITTER_BLOCK, new Item.Settings()));

    public static final Item COLD_LEAF_LITTER = registerItem("leaves_cold",
            new AliasedBlockItem(ModBlocks.COLD_LEAF_LITTER_BLOCK, new Item.Settings()));

    public static final Item DRY_LEAF_LITTER = registerItem("leaves_dead",
            new AliasedBlockItem(ModBlocks.DRY_LEAF_LITTER_BLOCK, new Item.Settings()));

    public static final Item FLOWER_COVER_WHITE = registerItem("aubrieta_white",
            new AliasedBlockItem(ModBlocks.FLOWER_COVER_WHITE_BLOCK, new Item.Settings()));

    public static final Item FLOWER_COVER_BLUE = registerItem("aubrieta_blue",
            new AliasedBlockItem(ModBlocks.FLOWER_COVER_BLUE_BLOCK, new Item.Settings()));

    public static final Item FLOWER_COVER_PINK = registerItem("aubrieta_pink",
            new AliasedBlockItem(ModBlocks.FLOWER_COVER_PINK_BLOCK, new Item.Settings()));

    public static final Item FLOWER_COVER_RED = registerItem("aubrieta_red",
            new AliasedBlockItem(ModBlocks.FLOWER_COVER_RED_BLOCK, new Item.Settings()));

    public static final Item MOSS = registerItem("moss",
            new MossItem(new Item.Settings()));

    public static final Item SHELF_FUNGUS = registerItem("shelf_fungus",
            new AliasedBlockItem(ModBlocks.SHELF_FUNGUS_BLOCK, new Item.Settings()));


    public static final Item ORANGE_MYCENA = registerItem("orange_mycena",
            new AliasedBlockItem(ModBlocks.ORANGE_MYCENA_BLOCK, new Item.Settings()));

    public static final Item LARGE_ORANGE_MYCENA = registerItem("large_orange_mycena",
            new AliasedBlockItem(ModBlocks.LARGE_ORANGE_MYCENA_BLOCK, new Item.Settings()));

    public static final Item CLOVER = registerItem("clover",
            new AliasedBlockItem(ModBlocks.CLOVER_BLOCK, new Item.Settings()));




    // Crops Seeds
    // banana seeds
    public static final Item BANANA_SEEDS = registerItem("banana_seeds",
            new AliasedBlockItem(ModBlocks.BANANA_CROP, new Item.Settings()));

    public static final Item BANANA = registerItem("banana",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // corn seeds
    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new Item.Settings()));

    public static final Item CORN = registerItem("corn",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // eggplant seeds
    public static final Item EGGPLANT_SEEDS = registerItem("eggplant_seeds",
            new AliasedBlockItem(ModBlocks.EGGPLANT_CROP, new Item.Settings()));

    public static final Item EGGPLANT = registerItem("eggplant",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // lettuce seeds
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds",
            new AliasedBlockItem(ModBlocks.LETTUCE_CROP, new Item.Settings()));

    public static final Item LETTUCE = registerItem("lettuce",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // Not used

    // pineapple seeds
    public static final Item PINEAPPLE_SEEDS = registerItem("pineapple_seeds",
            new AliasedBlockItem(ModBlocks.PINEAPPLE_CROP, new Item.Settings()));

    public static final Item PINEAPPLE = registerItem("pineapple",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // mango seeds
    public static final Item MANGO_SEEDS = registerItem("mango_seeds",
            new AliasedBlockItem(ModBlocks.MANGO_CROP, new Item.Settings()));

    public static final Item MANGO = registerItem("mango",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // chili seeds
    public static final Item CHILI_SEEDS = registerItem("chilli_seeds",
            new AliasedBlockItem(ModBlocks.CHILI_CROP, new Item.Settings()));

    public static final Item CHILI = registerItem("chilli",
            new Item(new Item.Settings().food(FoodComponents.APPLE)));

    // apple seeds
    public static final Item APPLE_SEEDS = registerItem("apple_seeds",
            new AliasedBlockItem(ModBlocks.APPLE_CROP, new Item.Settings()));


    // ##########  DESIGNS  ##########
    public static final Item DESIGN_AXE_SHELL = registerItem("axe_shell_design",
            new ItemDesign(new Item.Settings().maxCount(1).fireproof().rarity(Rarity.EPIC), AxeItem.class, 1, "Axe Shell"));

    // BANNER
    //BADGER
//    public static final Item FARMING_BANNER = registerItem("farming_banner_item",
//            new BannerItem(
//                    ModBlocks.FARMING_BANNER_BLOCK,
//                    ModBlocks.FARMING_BANNER_WALL_BLOCK, new Item.Settings().maxCount(16)));


    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(WATERING_CAN);
    }


    private static Item registerItem(String name, Item item) {
        ServerMod.LOGGER.info("Registering " + name);
        return Registry.register(Registries.ITEM, Identifier.of(ServerMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ServerMod.LOGGER.info("Registering Mod Items for " + ServerMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
