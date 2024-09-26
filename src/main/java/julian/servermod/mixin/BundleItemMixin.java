package julian.servermod.mixin;

import net.minecraft.item.BundleItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BundleItem.class)
public class BundleItemMixin {

//    @Inject(method = "getAmountFilled", at = @At("HEAD"), cancellable = true)
//    private static void addPocketOccupancy(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
//        if(PocketUtil.hasPockets(stack)) {
//            cir.setReturnValue((64 / stack.getMaxCount()) + PocketUtil.getPocketOccupancy(stack));
//        }
//    }
//
//    @Inject(method = "appendTooltip", at = @At("HEAD"))
//    private void addPocketOccupancy(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
//        BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
//        ServerMod.LOGGER.info("bundleContentsComponent: " + bundleContentsComponent);
//    }
}
