package julian.servermod.entity.ai.goal;

import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import julian.servermod.block.ModBlocks;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class FindLitterGoal extends Goal {
    private final SnailEntity snail;
    private double targetX;
    private double targetY;
    private double targetZ;



    public FindLitterGoal(SnailEntity snail) {
        this.snail = snail;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        // find litter in 5x5 area
        for (int x = -2; x < 3; x++) {
            for (int z = -2; z < 3; z++) {
                BlockPos pos = this.snail.getBlockPos().add(x, 0, z);
                Block block = this.snail.getWorld().getBlockState(pos).getBlock();

                if (
                block == ModBlocks.LEAF_LITTER_BLOCK
                || block == ModBlocks.COLD_LEAF_LITTER_BLOCK
                || block == ModBlocks.DRY_LEAF_LITTER_BLOCK
                ) {
                    this.targetX = pos.getX();
                    this.targetY = pos.getY();
                    this.targetZ = pos.getZ();
                    return true;
                }
            }
        }


        return false;
    }

    public boolean isTargetValid() {
        Block block = this.snail.getWorld().getBlockState(this.getTarget()).getBlock();
        return block == ModBlocks.LEAF_LITTER_BLOCK
                || block == ModBlocks.COLD_LEAF_LITTER_BLOCK
                || block == ModBlocks.DRY_LEAF_LITTER_BLOCK;
    }

    public BlockPos getTarget() {
        return new BlockPos(((int) this.targetX), ((int) this.targetY), (int) this.targetZ);
    }

    @Override
    public boolean shouldContinue() {
        return !this.snail.getNavigation().isIdle()
                && !this.snail.hasPassengers()
                && isTargetValid();
    }


    @Override
    public void start() {
        this.snail.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.snail.speed);
    }

    @Override
    public void stop() {
        this.snail.getNavigation().stop();
        super.stop();
    }
}
