package julian.servermod.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    @Shadow private String newItemName;

//    @Shadow @Final
//    private Property levelCost;
//
//    @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
//    private void noxpcost$canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(true);
//    }
//    @Inject(method = "onTakeOutput", at = @At("HEAD"))
//    private void noxpcost$onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
//        levelCost.set(0);
//    }
//
//    @Inject(method = "updateResult", at = @At("TAIL"))
//    private void norxpcost$updateResult(CallbackInfo ci) {
//        levelCost.set(0);
//    }
//
//    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
//    private int noxpcost$maxValue(int input) {
//        return Integer.MAX_VALUE;
//    }

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(at = @At("HEAD"), method = "updateResult", cancellable = true)
    private void init(CallbackInfo info) {
        if (julian.servermod.utils.skinsystem.AnvilHelper.canApplySkin(this.player, this.slots, this.newItemName)) info.cancel();
        // if (julian.servermod.utils.pockets.AnvilHelper.canApplyPocket(this.player, this.slots, this.newItemName)) info.cancel();
    }
}