package julian.servermod.entity.ai.goal;


import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

public class HideGoal extends Goal {
    private final SnailEntity snail;
    protected PlayerEntity closestPlayer;


    public HideGoal(SnailEntity snail) {
        this.snail = snail;
    }


    @Override
    public boolean canStart() {
        return snail.canHide();
    }

    @Override
    public boolean shouldContinue() {
        return snail.canHide();
    }

    @Override
    public void start() {
        snail.setJumping(false);
        snail.getNavigation().stop();
        snail.getMoveControl().moveTo(snail.getX(), snail.getY(), snail.getZ(), 0.0D);
    }


    @Override
    public void tick() {
        snail.getNavigation().stop();
        snail.setVelocity(0, snail.getVelocity().y, 0);
        snail.getMoveControl().moveTo(snail.getX(), snail.getY(), snail.getZ(), 0.0D);
    }
}
