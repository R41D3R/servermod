package julian.servermod.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContentsComponent.class)
public class BundleContentsComponentsMixin {

    private static final int INCREASED_CAPACITY = 256;

    @Inject(method = "getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At("RETURN"), cancellable = true)
    private static void increaseCapacity(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        Fraction originalOccupancy = cir.getReturnValue();
        Fraction newOccupancy = originalOccupancy.multiplyBy(Fraction.getFraction(64, INCREASED_CAPACITY));
        cir.setReturnValue(newOccupancy);
    }

    @Mixin(BundleContentsComponent.Builder.class)
    public static class BuilderMixin {
        @ModifyVariable(method = "add(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), argsOnly = true)
        private ItemStack increaseAddLimit(ItemStack stack) {
            if (stack.getCount() > 64 && stack.getCount() <= INCREASED_CAPACITY) {
                ItemStack newStack = stack.copy();
                newStack.setCount(INCREASED_CAPACITY);
                return newStack;
            }
            return stack;
        }

        @Inject(method = "getMaxAllowed", at = @At("RETURN"), cancellable = true)
        private void increaseMaxAllowed(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
            int originalMax = cir.getReturnValue();
            int newMax = Math.min(INCREASED_CAPACITY, Math.max(originalMax, stack.getCount()));
            cir.setReturnValue(newMax);
        }
    }
}
