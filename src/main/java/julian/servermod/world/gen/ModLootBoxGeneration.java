package julian.servermod.world.gen;

import julian.servermod.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class ModLootBoxGeneration {

    public static void generateLootVases() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.LOOT_VASE_PLACED_KEY);
    }
}
