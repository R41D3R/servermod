package julian.servermod.datagen;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE);
    private static final List<ItemConvertible> PLATINUM_SMELTABLES = List.of(ModBlocks.DEEPSLATE_PLATINUM_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 200, "ruby");
        offerSmelting(exporter, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM,
                0.7f, 200, "platinum");

        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 100, "ruby");
        offerBlasting(exporter, PLATINUM_SMELTABLES, RecipeCategory.MISC, ModItems.PLATINUM,
                0.7f, 100, "platinum");


        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY,
                RecipeCategory.DECORATIONS, ModBlocks.RUBY_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.PLATINUM,
                RecipeCategory.DECORATIONS, ModBlocks.PLATINUM_BLOCK);


//        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_RUBY, 1)
//                .pattern("SSS")
//                .pattern("SRS")
//                .pattern("SSS")
//                .input('S', Items.STONE)
//                .input('R', ModItems.RUBY)
//                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
//                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
//                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAW_RUBY)));
    }
}
