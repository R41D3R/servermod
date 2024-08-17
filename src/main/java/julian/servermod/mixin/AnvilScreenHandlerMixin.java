package julian.servermod.mixin;

import julian.servermod.skinsystem.AnvilHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        if (AnvilHelper.canApplySkin(this.player, this.slots, this.newItemName)) info.cancel();
    }
}