package julian.servermod.entity.ai.goal;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;

public class EatLeafLitterGoal extends MoveToTargetPosGoal {
    private static final int EATING_TIME = 40;
    protected int timer;
    private final SnailEntity snail;

    public EatLeafLitterGoal(SnailEntity snail, double speed, int range, int maxYDifference) {
        super(snail, speed, range, maxYDifference);
        this.snail = snail;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 2.0D;
    }

    @Override
    public boolean shouldResetPath() {
        return this.tryingTime % 100 == 0;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        //ServerMod.LOGGER.info("Checking if target pos is leaf litter");
        BlockState blockState = world.getBlockState(pos);
        return blockState.isOf(ModBlocks.LEAF_LITTER_BLOCK) ||
                blockState.isOf(ModBlocks.COLD_LEAF_LITTER_BLOCK) ||
                blockState.isOf(ModBlocks.DRY_LEAF_LITTER_BLOCK);
    }

    @Override
    public void tick() {
        if (this.hasReached()) {
            if (this.timer >= EATING_TIME) {
                this.eatLeafLitter();
            } else {
                ++this.timer;
            }
        } else if (!this.hasReached() && snail.getRandom().nextFloat() < 0.005f) {
            ServerMod.LOGGER.info("Snail is eating but that was random");
            snail.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.1f, 1.0f);
        }
        super.tick();
    }

    protected void eatLeafLitter() {
        if (!snail.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            return;
        }
        BlockState blockState = snail.getWorld().getBlockState(this.targetPos);
        if (isTargetPos(snail.getWorld(), this.targetPos)) {
            ServerMod.LOGGER.info("Snail is eating");
            this.consumeLeafLitter(blockState);
        }
    }

    private void consumeLeafLitter(BlockState state) {
        snail.getWorld().breakBlock(this.targetPos, false);
        snail.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.1f, 1.0f);
        // You can add additional effects here, like healing the snail or increasing its size
        // For example: snail.heal(1.0f);
        this.snail.onEatingLitter();
    }

    @Override
    public boolean canStart() {
        //ServerMod.LOGGER.info("Snail can start" + (!snail.isSleeping() && super.canStart()));
        return !snail.isSleeping() && super.canStart();
    }

    @Override
    public void start() {
        ServerMod.LOGGER.info("Snail started");
        this.timer = 0;
        super.start();
    }
}