package julian.servermod.mixin;

import julian.servermod.MinecraftServerSupplier;
import julian.servermod.badgertasks.BadgerTaskManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "runServer", at = @At("HEAD"))
    private void runServer(CallbackInfo ci) {
        MinecraftServerSupplier.setServer((MinecraftServer) (Object) this);
    }

    @Inject(method = "saveAll", at = @At("TAIL"))
    private void onChunkSaving(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
        BadgerTaskManager.getStorage().save();
    }
}