package julian.servermod.datagen;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerParentedItemModel(ModItems.LOOT_BALLOON_SPAWN_EGG, ModelIds.getMinecraftNamespacedItem("template_spawn_egg"));

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {


        // TOOLS
        itemModelGenerator.register(ModItems.ANCIENT_PICKAXE, Models.HANDHELD);

        // ANIMALS
        itemModelGenerator.register(ModItems.SNAIL_SHELL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SNAIL, Models.GENERATED);


        // PLANTS
        itemModelGenerator.register(ModItems.PEBBLES_ITEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROCKS_ITEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAF_LITTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.COLD_LEAF_LITTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRY_LEAF_LITTER, Models.GENERATED);

        itemModelGenerator.register(ModItems.MOSS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHELF_FUNGUS, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOWER_COVER_WHITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOWER_COVER_BLUE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOWER_COVER_PINK, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOWER_COVER_RED, Models.GENERATED);

        itemModelGenerator.register(ModItems.ORANGE_MYCENA, Models.GENERATED);
        itemModelGenerator.register(ModItems.LARGE_ORANGE_MYCENA, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOVER, Models.GENERATED);

        itemModelGenerator.register(ModItems.CRATE_KEY_BADGER, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRATE_KEY_LEGENDARY, Models.GENERATED);

        itemModelGenerator.register(ModItems.FERTILIZER_SPEED, Models.GENERATED);

        itemModelGenerator.register(ModItems.PHOENIX_FEATHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.BADGER_CLUB_ID, Models.GENERATED);

        itemModelGenerator.register(ModItems.BADGER_COIN, Models.GENERATED);

        // crop seeds
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.BANANA_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BANANA, Models.GENERATED);
        itemModelGenerator.register(ModItems.EGGPLANT_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.EGGPLANT, Models.GENERATED);
        itemModelGenerator.register(ModItems.LETTUCE_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.LETTUCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PINEAPPLE_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PINEAPPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGO_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGO, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHILI_SEEDS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHILI, Models.GENERATED);
        itemModelGenerator.register(ModItems.APPLE_SEEDS, Models.GENERATED);
        // ADD CROP items and loot drop items


    }
}
