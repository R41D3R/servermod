package julian.servermod.mixin;


import julian.servermod.sound.PocketSound;
import julian.servermod.utils.pockets.PocketUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();


    @Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;decrement(I)V",
                    shift = At.Shift.AFTER))
    private void onLeatherPantsBreak(int amount, ServerWorld world, ServerPlayerEntity player, Consumer<net.minecraft.item.Item> breakCallback, CallbackInfo ci)  {
        ItemStack stack = (ItemStack)(Object)this;
        if(PocketUtil.hasPockets(stack)) {
            PocketUtil.dropAllPocketedItems(stack, player);
            PocketSound.playDropContentsSound(player);
        }
    }
}
