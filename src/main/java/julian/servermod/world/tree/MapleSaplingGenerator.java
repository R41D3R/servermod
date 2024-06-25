package julian.servermod.world.tree;

import julian.servermod.world.ModConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

public class MapleSaplingGenerator extends SaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if (random.nextInt(10) == 0) {
            if (random.nextInt(2) == 0) {
                return ModConfiguredFeatures.FANCY_MAPLE_ORANGE_KEY;
            }
            else {
                return ModConfiguredFeatures.FANCY_MAPLE_RED_KEY;
            }
        }
        if (random.nextInt(2) == 0) {
            return ModConfiguredFeatures.MAPLE_ORANGE_KEY;
        } else {
            return ModConfiguredFeatures.MAPLE_RED_KEY;
        }
    }
}
