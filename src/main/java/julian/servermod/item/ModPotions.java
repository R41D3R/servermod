package julian.servermod.item;

import julian.servermod.ServerMod;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static Potion HASTE_POTION;
    public static Potion LONG_HASTE_POTION;

    public static Potion registerPotion(String name, StatusEffectInstance effect) {
        return Registry.register(Registries.POTION, new Identifier(ServerMod.MOD_ID, name),
                new Potion(effect));
    }

    public static void registerPotions() {

        HASTE_POTION = registerPotion("haste", new StatusEffectInstance(StatusEffects.HASTE, 3600, 0));
        LONG_HASTE_POTION = registerPotion("long_haste", new StatusEffectInstance(StatusEffects.HASTE, 9600, 1));

        registerPotionRecipes();
    }

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(Potions.SWIFTNESS, ModItems.LARGE_ORANGE_MYCENA, HASTE_POTION);
        BrewingRecipeRegistry.registerPotionRecipe(HASTE_POTION, Items.REDSTONE, LONG_HASTE_POTION);
    }
}
