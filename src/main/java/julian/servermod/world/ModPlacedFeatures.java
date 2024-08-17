package julian.servermod.world;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RUBY_ORE_PLACED_KEY = registerKey("ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> PLATINUM_ORE_PLACED_KEY = registerKey("platinum_ore_placed");
    public static final RegistryKey<PlacedFeature> LOOT_VASE_PLACED_KEY = registerKey("loot_vase_placed");
    public static final RegistryKey<PlacedFeature> EMERALD_ORE_PLACED_KEY = registerKey("emerald_ore_placed");
    public static final RegistryKey<PlacedFeature> EXTRA_IRON_ORE_UPPER_KEY = registerKey("extra_iron_ore_upper");
    public static final RegistryKey<PlacedFeature> EXTRA_IRON_ORE_MIDDLE_KEY = registerKey("extra_iron_ore_middle");
    public static final RegistryKey<PlacedFeature> EXTRA_IRON_ORE_SMALL_KEY = registerKey("extra_iron_ore_small");
    public static final RegistryKey<PlacedFeature> EXTRA_COAL_ORE_KEY = registerKey("extra_coal_ore");
    public static final RegistryKey<PlacedFeature> EXTRA_COPPER_ORE_KEY = registerKey("extra_copper_ore");
    public static final RegistryKey<PlacedFeature> EXTRA_GOLD_ORE_UPPER_KEY = registerKey("extra_gold_ore_upper");
    public static final RegistryKey<PlacedFeature> EXTRA_DIAMOND_ORE_KEY = registerKey("extra_diamond_ore");
    public static final RegistryKey<PlacedFeature> EXTRA_REDSTONE_ORE_KEY = registerKey("extra_redstone_ore");
    public static final RegistryKey<PlacedFeature> EXTRA_LAPIS_ORE_KEY = registerKey("extra_lapis_ore");
//    public static final RegistryKey<PlacedFeature> EXTRA_GOLD_NETHER = registerKey("extra_gold_nether");
//    public static final RegistryKey<PlacedFeature> EXTRA_QUARTZ_ORE_NETHER = registerKey("extra_quartz_ore");



//    public static final RegistryKey<PlacedFeature> MAPLE_RED_PLACED_KEY = registerKey("maple_red_placed");
//    public static final RegistryKey<PlacedFeature> MAPLE_ORANGE_PLACED_KEY = registerKey("maple_orange_placed");
//    public static final RegistryKey<PlacedFeature> FANCY_MAPLE_RED_PLACED_KEY = registerKey("fancy_maple_red_placed");
//    public static final RegistryKey<PlacedFeature> FANCY_MAPLE_ORANGE_PLACED_KEY = registerKey("fancy_maple_orange_placed");
//    public static final RegistryKey<PlacedFeature> MAPLE_PLACED_KEY = registerKey("maple_placed");

    //public static final RegistryKey<PlacedFeature> GRANITE_ROCKS = registerKey("granite_rocks");



    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        // -------- ORES --------
        // TODO: How many drops per chunk for ruby and loot vase?
        register(context, RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(7,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(48))));

        register(context, EMERALD_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.EXTRA_EMERALD_ORE_KEY),
                ModOrePlacement.modifiersWithCount(7,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(48))));

        register(context, PLATINUM_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PLATINUM_ORE_KEY),
                ModOrePlacement.modifiersWithCount(1,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(-16))));

        register(context, EXTRA_IRON_ORE_UPPER_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_IRON),
                ModOrePlacement.modifiersWithCount(90,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(80), YOffset.fixed(384))));

        register(context, EXTRA_IRON_ORE_MIDDLE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_IRON),
                ModOrePlacement.modifiersWithCount(10,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-24), YOffset.fixed(56))));

        register(context, EXTRA_IRON_ORE_SMALL_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_IRON_SMALL),
                ModOrePlacement.modifiersWithCount(10,
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(72))));

        register(context, EXTRA_COAL_ORE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_COAL_BURIED),
                ModOrePlacement.modifiersWithCount(20,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(0), YOffset.fixed(192))));

        register(context, EXTRA_COPPER_ORE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_COPPER_SMALL),
                ModOrePlacement.modifiersWithCount(16,
                        HeightRangePlacementModifier.trapezoid(YOffset.fixed(-16), YOffset.fixed(112))));

        register(context, EXTRA_GOLD_ORE_UPPER_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_GOLD_BURIED),
                ModOrePlacement.modifiersWithCount(50,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(32), YOffset.fixed(256))));

        register(context, EXTRA_DIAMOND_ORE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIAMOND_BURIED),
                ModOrePlacement.modifiersWithCount(10,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(18))));

        register(context, EXTRA_REDSTONE_ORE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_REDSTONE),
                ModOrePlacement.modifiersWithCount(10,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-50), YOffset.fixed(18))));

        register(context, EXTRA_LAPIS_ORE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_LAPIS),
                ModOrePlacement.modifiersWithCount(4,
                        HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))));
//
//        register(context, EXTRA_GOLD_NETHER, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_NETHER_GOLD),
//                ModOrePlacement.modifiersWithCount(10, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
//
//        register(context, EXTRA_QUARTZ_ORE_NETHER, configuredFeatureRegistryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_QUARTZ),
//                ModOrePlacement.modifiersWithCount(16, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
//
        // -------- VASE --------
        register(context, LOOT_VASE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LOOT_VASE_KEY),
                List.of(CountPlacementModifier.of(1), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(-16)),
                        RarityFilterPlacementModifier.of(3),
                        // EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.IS_AIR, 60),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 30),
                        RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)),
                        BiomePlacementModifier.of()));

        // TREES
//        register(context, MAPLE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MAPLE_KEY),
//                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1),
//                        ModBlocks.MAPLE_SAPLING));
//
//        register(context, MAPLE_RED_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MAPLE_RED_KEY),
//                List.of(new PlacementModifier[]{PlacedFeatures.wouldSurvive(ModBlocks.MAPLE_SAPLING)}));
//        register(context, MAPLE_ORANGE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MAPLE_ORANGE_KEY),
//                List.of(new PlacementModifier[]{PlacedFeatures.wouldSurvive(ModBlocks.MAPLE_SAPLING)}));
//        register(context, FANCY_MAPLE_RED_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FANCY_MAPLE_RED_KEY),
//                List.of(new PlacementModifier[]{PlacedFeatures.wouldSurvive(ModBlocks.MAPLE_SAPLING)}));
//        register(context, FANCY_MAPLE_ORANGE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FANCY_MAPLE_ORANGE_KEY),
//                List.of(new PlacementModifier[]{PlacedFeatures.wouldSurvive(ModBlocks.MAPLE_SAPLING)}));


        // BIOME FEATURES
//        RegistryEntry<ConfiguredFeature<?, ?>> registryEntry3 = configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.GRANITE_ROCKS);
//
//        register(context, GRANITE_ROCKS, registryEntry3, List.of(new PlacementModifier[]{CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()}));

    }



    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(ServerMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
