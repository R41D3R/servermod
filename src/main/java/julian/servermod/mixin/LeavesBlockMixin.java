package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {

    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z", shift = At.Shift.AFTER))
    public void addLitterAfterDecay(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        // Add litter to the block below the leaves
        ServerMod.LOGGER.info("Adding litter to block below leaves");
        Block litterBlock = getLitterBlock();
        if (litterBlock != null) {
            BlockPos litterPos = getLitterSpawnPos(pos, world);
            if (litterPos != null) {
                world.setBlockState(litterPos.up(), litterBlock.getDefaultState());
            }
        }

    }

    private BlockPos getLitterSpawnPos(BlockPos pos, ServerWorld world) {
        BlockPos current_pos = pos;
        for (int i = 0; i < 20; i++) {

            current_pos = current_pos.down();
            ServerMod.LOGGER.info("Checking block at " + current_pos);
            BlockState state = world.getBlockState(current_pos);
            ServerMod.LOGGER.info("Block state: " + state);
            if (state.isSolidBlock(world, current_pos) && !state.isReplaceable()){
                ServerMod.LOGGER.info("Found block at " + current_pos);
                return current_pos;
            }
        }
        return null;
    }

    private Block getLitterBlock() {
        Identifier id = Registries.BLOCK.getId(this);
        ServerMod.LOGGER.info("Checking block " + id);
        String leaves = id.getPath();
        ServerMod.LOGGER.info("Path: " + leaves);

        if (
                leaves.equals("azalea_leaves")
                || leaves.equals("oak_leaves")
                || leaves.equals("birch_leaves")
                || leaves.equals("jungle_leaves")
                || leaves.equals("dark_oak_leaves")
        ) {
            return ModBlocks.LEAF_LITTER_BLOCK;
        } else if (leaves.equals("spruce_leaves")) {
            return ModBlocks.COLD_LEAF_LITTER_BLOCK;
        }
        return null;
    }

}
