package julian.servermod.entity.ai.goal;

import com.google.common.collect.ImmutableList;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class EatLitterGoal extends Goal {
    protected final SnailEntity snail;
    private static final int MAX_TIMER = 40;
    private int timer;
    private final World world;



    private static final ImmutableList<Predicate<BlockState>> LITTER_PREDICATE = ImmutableList.of(
            BlockStatePredicate.forBlock(ModBlocks.LEAF_LITTER_BLOCK),
            BlockStatePredicate.forBlock(ModBlocks.COLD_LEAF_LITTER_BLOCK),
            BlockStatePredicate.forBlock(ModBlocks.DRY_LEAF_LITTER_BLOCK)
    );


    public EatLitterGoal(SnailEntity snail) {
        this.snail = snail;
        this.world = snail.getWorld();
    }

    @Override
    public boolean canStart() {
        BlockPos blockPos = this.snail.getBlockPos();
        for (Predicate<BlockState> predicate : LITTER_PREDICATE) {
            if (predicate.test(this.snail.getWorld().getBlockState(blockPos))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.timer = this.getTickCount(40);
        this.snail.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.timer = 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.timer > 0;
    }

    public int getTimer() {
        return this.timer;
    }

    @Override
    public void tick() {
        this.timer = Math.max(0, this.timer - 1);
        if (this.timer != this.getTickCount(4)) {
            return;
        }
        BlockPos blockPos = this.snail.getBlockPos();
        for (Predicate<BlockState> predicate : LITTER_PREDICATE) {
            if (predicate.test(this.snail.getWorld().getBlockState(blockPos))) {
                this.world.breakBlock(blockPos, false);
                this.snail.onEatingLitter();
                return;
            }
        }

//        BlockPos blockPos2 = blockPos.down();
//        if (this.world.getBlockState(blockPos2).isOf(Blocks.GRASS_BLOCK)) {
//            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
//                this.world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, blockPos2, Block.getRawIdFromState(Blocks.GRASS_BLOCK.getDefaultState()));
//                this.world.setBlockState(blockPos2, Blocks.DIRT.getDefaultState(), Block.NOTIFY_LISTENERS);
//            }
//            this.snail.onEatingGrass();
//
//        }
    }
}
