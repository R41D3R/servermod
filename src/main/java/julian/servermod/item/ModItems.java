package julian.servermod.item;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.ModEntities;
import julian.servermod.item.custom.*;
import julian.servermod.item.custom.cratekeys.CrateKeyBadger;
import julian.servermod.item.custom.cratekeys.CrateKeyLegendary;
import julian.servermod.item.custom.cratekeys.CrateKeyRare;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

// registers all Item
public class ModItems {

    // Entities
    public static final Item LOOT_BALLOON_SPAWN_EGG = registerItem("loot_balloon_spawn_egg",
            new SpawnEggItem(ModEntities.LOOT_BALLOON, 0xD57E57, 0x100000,
                    new FabricItemSettings()));

    public static final Item CAPTURE_NET = registerItem("capture_net",
            new CaptureNet(new FabricItemSettings().maxDamage(3)));


    public static final Item BADGER_CLUB_ID = registerItem("badger_club_id",
            new BadgerClubId(new FabricItemSettings().maxCount(1)));



    public static final Item WateringCan = registerItem("wooden_watering_can",
            new WateringCan(new FabricItemSettings().maxDamage(6)));

    public static final Item RUBY = registerItem("ruby",
            new Item(new FabricItemSettings()));

    public static final Item CUSTOM_LAPIS = registerItem("custom_lapis",
            new Item(new FabricItemSettings()));

    public static final Item PLATINUM = registerItem("platinum",
            new Item(new FabricItemSettings()));

    public static final Item SOIL = registerItem("soil",
            new Soil(new FabricItemSettings()));

    public static final Item FERTILIZER_SPEED = registerItem("fertilizer_speed",
            new FertilizerSpeed(new FabricItemSettings()));

    public static final Item ASHES_O_ENCHANTMENT = registerItem("ashes_o_enchantment",
            new Item(new FabricItemSettings()));

    public static final Item PHOENIX_FEATHER = registerItem("phoenix_feather",
            new Item(new FabricItemSettings()));

    public static final Item CRATE_KEY_RARE = registerItem("crate_key_rare",
            new CrateKeyRare(new FabricItemSettings()));

    public static final Item CRATE_KEY_BADGER = registerItem("crate_key_badger",
            new CrateKeyBadger(new FabricItemSettings()));

    public static final Item CRATE_KEY_LEGENDARY = registerItem("crate_key_legendary",
            new CrateKeyLegendary(new FabricItemSettings()));

    // Crops Seeds

    // banana seeds
    public static final Item BANANA_SEEDS = registerItem("banana_seeds",
            new AliasedBlockItem(ModBlocks.BANANA_CROP, new FabricItemSettings()));

    public static final Item BANANA = registerItem("banana",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // corn seeds
    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new FabricItemSettings()));

    public static final Item CORN = registerItem("corn",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // eggplant seeds
    public static final Item EGGPLANT_SEEDS = registerItem("eggplant_seeds",
            new AliasedBlockItem(ModBlocks.EGGPLANT_CROP, new FabricItemSettings()));

    public static final Item EGGPLANT = registerItem("eggplant",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // lettuce seeds
    public static final Item LETTUCE_SEEDS = registerItem("lettuce_seeds",
            new AliasedBlockItem(ModBlocks.LETTUCE_CROP, new FabricItemSettings()));

    public static final Item LETTUCE = registerItem("lettuce",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // Not used

    // pineapple seeds
    public static final Item PINEAPPLE_SEEDS = registerItem("pineapple_seeds",
            new AliasedBlockItem(ModBlocks.PINEAPPLE_CROP, new FabricItemSettings()));

    public static final Item PINEAPPLE = registerItem("pineapple",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // mango seeds
    public static final Item MANGO_SEEDS = registerItem("mango_seeds",
            new AliasedBlockItem(ModBlocks.MANGO_CROP, new FabricItemSettings()));

    public static final Item MANGO = registerItem("mango",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // chili seeds
    public static final Item CHILI_SEEDS = registerItem("chilli_seeds",
            new AliasedBlockItem(ModBlocks.CHILI_CROP, new FabricItemSettings()));

    public static final Item CHILI = registerItem("chilli",
            new Item(new FabricItemSettings().food(FoodComponents.APPLE)));

    // apple seeds
    public static final Item APPLE_SEEDS = registerItem("apple_seeds",
            new AliasedBlockItem(ModBlocks.APPLE_CROP, new FabricItemSettings()));


    // BANNER
    //BADGER
//    public static final Item FARMING_BANNER = registerItem("farming_banner_item",
//            new BannerItem(
//                    ModBlocks.FARMING_BANNER_BLOCK,
//                    ModBlocks.FARMING_BANNER_WALL_BLOCK, new FabricItemSettings().maxCount(16)));


    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(WateringCan);
    }


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ServerMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ServerMod.LOGGER.info("Registering Mod Items for " + ServerMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
