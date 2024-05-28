package julian.servermod.item;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.item.custom.Soil;
import julian.servermod.item.custom.WateringCan;
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

    public static final Item ASHES_O_ENCHANTMENT = registerItem("ashes_o_enchantment",
            new Item(new FabricItemSettings()));

    public static final Item CRATE_KEY_RARE = registerItem("crate_key_rare",
            new CrateKeyRare(new FabricItemSettings()));

    public static final Item CRATE_KEY_BADGER = registerItem("crate_key_badger",
            new CrateKeyBadger(new FabricItemSettings()));

    public static final Item CRATE_KEY_LEGENDARY = registerItem("crate_key_legendary",
            new CrateKeyLegendary(new FabricItemSettings()));

    // Crops Seeds

    public static final Item CORN_SEEDS = registerItem("corn_seeds",
            new AliasedBlockItem(ModBlocks.CORN_CROP, new FabricItemSettings()));


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
