package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.utils.pockets.PocketContentsComponent;
import julian.servermod.utils.pockets.PocketUtil;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ALL")
@Mixin(PocketContentsComponent.class)
public class PocketContentsComponentsMixin {

    // private static final int INCREASED_CAPACITY = 256;


    @Inject(method = "getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At("RETURN"), cancellable = true)
    private static void increaseCapacity(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        Fraction originalOccupancy = cir.getReturnValue();
        ServerMod.LOGGER.info("Increasing capacity to " + PocketUtil.INCREASED_CAPACITY);
        ServerMod.LOGGER.info("item: " + stack.getItem());
        Fraction newOccupancy = originalOccupancy.multiplyBy(Fraction.getFraction(64, PocketUtil.INCREASED_CAPACITY));
        cir.setReturnValue(newOccupancy);
    }

    @Mixin(PocketContentsComponent.Builder.class)
    public static class BuilderMixin {
        @ModifyVariable(method = "add(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), argsOnly = true)
        private ItemStack increaseAddLimit(ItemStack stack, ItemStack bundleStack) {
            ServerMod.LOGGER.info("Increasing capacity to " + PocketUtil.INCREASED_CAPACITY);
            if (stack.getCount() > 64 && stack.getCount() <= PocketUtil.INCREASED_CAPACITY) {
                ItemStack newStack = stack.copy();
                newStack.setCount(PocketUtil.INCREASED_CAPACITY);
                return newStack;
            }
            return stack;
        }

        @Inject(method = "getMaxAllowed", at = @At("RETURN"), cancellable = true)
        private void increaseMaxAllowed(ItemStack stack, ItemStack bundleStack, CallbackInfoReturnable<Integer> cir) {
            ServerMod.LOGGER.info("Increasing capacity to " + PocketUtil.INCREASED_CAPACITY);
            int originalMax = cir.getReturnValue();
            int newMax = Math.min(PocketUtil.INCREASED_CAPACITY, Math.max(originalMax, stack.getCount()));
            cir.setReturnValue(newMax);
        }
    }
}
