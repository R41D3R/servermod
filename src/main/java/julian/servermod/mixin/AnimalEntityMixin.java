package julian.servermod.mixin;

import julian.servermod.ServerMod;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

//    @Redirect(method = "breed*", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
//    private boolean changeGameRule(GameRules rules, GameRules.Key<GameRules.BooleanRule> rule) {
//        ServerMod.LOGGER.info("Game rule changed!");
//        return false;
//    }
//
//    @Inject(method = "getXpToDrop", at = @At("HEAD"), cancellable = true)
//    private void changeXpToDrop(CallbackInfoReturnable<Integer> cir) {
//        ServerMod.LOGGER.info("XP to drop changed!");
//        cir.setReturnValue(0);
//    }
}
