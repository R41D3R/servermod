package julian.servermod.entity.ai.navigation;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.Entity;

import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;


public class BetterWallClimberNavigation extends MobNavigation {
    @Nullable
    private BlockPos pathToPosition;

    public BetterWallClimberNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    public Path findPathTo(BlockPos pos, int accuracy) {
        this.pathToPosition = pos;
        return super.findPathTo(pos, accuracy);
    }

    @Override
    public Path findPathTo(Entity entity, int i) {
        this.pathToPosition = entity.getBlockPos();
        return super.findPathTo(entity, i);
    }


    @Override
    public boolean startMovingTo(Entity entity, double speed) {
        Path path = this.findPathTo(entity, 0);
        if (path != null) {
            return this.startMovingAlong(path, 0.001f);
        } else {
            this.pathToPosition = entity.getBlockPos();
            this.speed = 0.001f;
            return true;
        }
    }

    @Override
    public void tick() {
        if (this.isIdle()) {
            if (this.pathToPosition != null) {
                if (!this.pathToPosition.isWithinDistance(this.entity.getBlockPos(),
                        Math.max(this.entity.getWidth(), 1.0D))
                        && (!(this.entity.getY() > (double) this.pathToPosition.getY())
                        || !(new BlockPos(this.pathToPosition.getX(), (int) this.entity.getY(),
                        this.pathToPosition.getZ())).isWithinDistance(this.entity.getBlockPos(),
                        Math.max(this.entity.getWidth(), 1.0D)))) {
                    this.entity.getMoveControl().moveTo(this.pathToPosition.getX(), this.pathToPosition.getY(),
                            this.pathToPosition.getZ(), this.speed);
                } else {
                    this.pathToPosition = null;
                }
            }
        } else {
            super.tick();
        }
    }
}
