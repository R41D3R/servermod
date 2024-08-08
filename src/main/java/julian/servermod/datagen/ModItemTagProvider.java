package julian.servermod.datagen;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    //public static final TagKey<Item> TAVERN_TOKENS_VALID_CURRENCY = TagKey.of(RegistryKeys.ITEM, Identifier.of("taverntokens", "valid_currency"));

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        // Add your custom currency to the Tavern Tokens valid currency tag


//        getOrCreateTagBuilder(TAVERN_TOKENS_VALID_CURRENCY)
//                .add(ModItems.BADGER_COIN)
//                .add(ModItems.RUBY);

    }
}
