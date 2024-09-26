package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {

    private static final Map<String, Item> REWARD_ITEMS = Map.of(
            //"minecraft:husbandry/obtain_netherite_hoe", ModItems.SHELL_AXE_TOKEN
    );
    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V", shift = At.Shift.AFTER))
    private void injected(AdvancementEntry advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        // Your injected code here
        System.out.println("Injected code after rewards are applied.");

        ServerMod.LOGGER.info("Advancement data: for player " + this.owner + advancement.toString());
        if (REWARD_ITEMS.containsKey(advancement.toString())) {
            ItemStack itemStack = new ItemStack(REWARD_ITEMS.get(advancement.toString()));
            giveReward(this.owner.getServerWorld(), this.owner.getBlockPos(), itemStack, this.owner);
        }

    }

    @Unique
    private void giveReward(ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack, ServerPlayerEntity player) {
        player.sendMessage(Text.of("You have received a reward! Look around at the ground to collect it.").copy().formatted(Formatting.RED), false);
        ItemEntity itemEntity = new ItemEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
        itemEntity.setPickupDelay(20);
        serverWorld.spawnEntity(itemEntity);
    }
}
