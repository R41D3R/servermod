package julian.servermod.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkSection.class)
public abstract class ChunkSectionMixin {

    @Shadow
    public abstract BlockState setBlockState(int x, int y, int z, BlockState state, boolean lock);

    @Inject(method = "setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
    private void dontGenerate(int x, int y, int z, BlockState state, boolean lock, CallbackInfoReturnable<BlockState> cir) {
//        if (y > 48) {
//            if (state.isOf(Blocks.EMERALD_ORE)) {
//                cir.setReturnValue(setBlockState(x, y, z, Blocks.STONE.getDefaultState(), lock));
//            } else if (state.isOf(Blocks.DEEPSLATE_EMERALD_ORE)) {
//                cir.setReturnValue(setBlockState(x, y, z, Blocks.DEEPSLATE.getDefaultState(), lock));
//            }
//        }

    }
}
