package julian.servermod.world;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> RUBY_ORE_KEY = registerKey("ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PLATINUM_ORE_KEY = registerKey("platinum_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> EMERALD_ORE_KEY = registerKey("emerald_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LOOT_VASE_KEY = registerKey("loot_vase");

    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_RED_KEY = registerKey("maple_red");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MAPLE_ORANGE_KEY = registerKey("maple_orange");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_RED_KEY = registerKey("fancy_maple_red");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_ORANGE_KEY = registerKey("fancy_maple_orange");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {

        /// ORE GENERATION _______________________________________________________
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        // can be done with BLOCKS too, see ORE GENERATION 5:00 https://www.youtube.com/watch?v=J-Wm8IVTZ88&list=PLKGarocXCE1EO43Dlf5JGh7Yk-kRAXUEJ&index=38

        List<OreFeatureConfig.Target> overworldRubyOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.RUBY_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldPlatinumOres =
                List.of(OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_PLATINUM_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> overworldEmeraldOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, Blocks.EMERALD_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState()));


        register(context, RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldRubyOres, 3));
        register(context, PLATINUM_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldPlatinumOres, 1));
        register(context, EMERALD_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldEmeraldOres, 2));

        /// LOOT VASE ____________________________________________________________
        register(context, LOOT_VASE_KEY, Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LOOT_VASE_BLOCK))
                );

        /// TREE ___________________________________________________________
        // TODO: Make fancy trees taller and more narrow and mor ofter + small bushes -> normal ones almost never spawn
        register(context, MAPLE_RED_KEY, Feature.TREE, ModConfiguredFeatures.mapleRed().build());
        register(context, MAPLE_ORANGE_KEY, Feature.TREE, ModConfiguredFeatures.mapleOrange().build());
        register(context, FANCY_MAPLE_RED_KEY, Feature.TREE, ModConfiguredFeatures.fancyMapleRed().build());
        register(context, FANCY_MAPLE_ORANGE_KEY, Feature.TREE, ModConfiguredFeatures.fancyMapleOrange().build());



    }

    private static TreeFeatureConfig.Builder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight), BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantIntProvider.create(radius), ConstantIntProvider.create(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeFeatureConfig.Builder mapleRed() {
        return ModConfiguredFeatures.builder(ModBlocks.MAPLE_LOG, ModBlocks.MAPLES_LEAVES_RED, 4, 2, 0, 2).ignoreVines();
    }

    private static TreeFeatureConfig.Builder mapleOrange() {
        return ModConfiguredFeatures.builder(ModBlocks.MAPLE_LOG, ModBlocks.MAPLES_LEAVES_ORANGE, 4, 2, 0, 2).ignoreVines();
    }

    private static TreeFeatureConfig.Builder fancyMapleRed() {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.MAPLE_LOG), new LargeOakTrunkPlacer(3, 11, 0), BlockStateProvider.of(ModBlocks.MAPLES_LEAVES_RED), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines();
    }

    private static TreeFeatureConfig.Builder fancyMapleOrange() {
        return new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.MAPLE_LOG), new LargeOakTrunkPlacer(3, 11, 0), BlockStateProvider.of(ModBlocks.MAPLES_LEAVES_ORANGE), new LargeOakFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines();
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ServerMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
