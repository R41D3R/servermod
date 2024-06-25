package julian.servermod.mixin;

import net.minecraft.entity.passive.LlamaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LlamaEntity.class)
public class LlamaEntityMixin {

    @Inject(method = "getInventorySize", at = @At("HEAD"), cancellable = true)
    public void changeInventorySize(CallbackInfoReturnable<Integer> cir) {
        LlamaEntity llama = (LlamaEntity) (Object) this;
        int newSize = llama.hasChest() ? 2 + 3 * llama.getInventoryColumns() : 2;
        cir.setReturnValue(newSize);
    }

    @Inject(method = "getInventoryColumns", at = @At("HEAD"), cancellable = true)
    public void changeColumns(CallbackInfoReturnable<Integer> cir) {
//        LlamaEntity llama = (LlamaEntity) (Object) this;
//        int newSize = llama.hasChest() ? 11 + 3 * llama.getInventoryColumns() : 2;
        cir.setReturnValue(5);
        // TODO: Extend the llama's inventory beyond 5 columns (current bug drops items)
    }


}
