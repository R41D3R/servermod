package julian.servermod.world.gen;

import julian.servermod.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OrePlacedFeatures;

public class ModOreGeneration {

    public static void generateOres() {
//        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
//                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.RUBY_ORE_PLACED_KEY);
//
//        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
//                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.PLATINUM_ORE_PLACED_KEY);

        // Additional ores
        // Overworld
        // small emerald ore in all overworld biomes
//        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
//                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EMERALD_ORE_PLACED_KEY);
        // 2x diamond ore in jungle
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_DIAMOND_ORE_KEY);
        // 2x copper ore in water areas, EXTRA Gold ore (3x)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER, BiomeKeys.COLD_OCEAN, BiomeKeys.FROZEN_OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_COPPER_ORE_KEY);

        // 2x iron ore in mountains
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.SNOWY_SLOPES, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.STONY_SHORE, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_IRON_ORE_MIDDLE_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.SNOWY_SLOPES, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.STONY_SHORE, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_IRON_ORE_SMALL_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_PEAKS, BiomeKeys.STONY_PEAKS, BiomeKeys.SNOWY_SLOPES, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.STONY_SHORE, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_IRON_ORE_UPPER_KEY);

        // birch 2x iron ore
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_IRON_ORE_SMALL_KEY);

        // 2x coal desert
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_COAL_ORE_KEY);

        // 2x coal savanna
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_COAL_ORE_KEY);

        // 2x lapis taiga
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_LAPIS_ORE_KEY);

        // 2x lapis dark forest
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_LAPIS_ORE_KEY);

        // 2x redstone swamp
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.EXTRA_REDSTONE_ORE_KEY);

//
//
//        // Nether
//        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_GOLD_NETHER);
//        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_QUARTZ_NETHER);
//        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_GOLD_NETHER);
//        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_QUARTZ_NETHER);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_GOLD_DELTAS);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_QUARTZ_DELTAS);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_GOLD_DELTAS);
//        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
//                GenerationStep.Feature.UNDERGROUND_ORES, OrePlacedFeatures.ORE_QUARTZ_DELTAS);
    }
}
