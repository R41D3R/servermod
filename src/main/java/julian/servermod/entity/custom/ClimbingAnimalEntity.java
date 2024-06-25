package julian.servermod.entity.custom;

import julian.servermod.entity.ai.navigation.BetterWallClimberNavigation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ClimbingAnimalEntity extends AnimalEntity {
    private static final TrackedData<Byte> CLIMB_FLAG = DataTracker.registerData(ClimbingAnimalEntity.class, TrackedDataHandlerRegistry.BYTE);

    protected ClimbingAnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BetterWallClimberNavigation(this, world);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CLIMB_FLAG, (byte)0);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public boolean isClimbing() {
        return (this.dataTracker.get(CLIMB_FLAG) & 1) != 0;
    }

    public void setClimbing(boolean climbing) {
        byte b = this.dataTracker.get(CLIMB_FLAG);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b = (byte)(b & -2);
        }
        this.dataTracker.set(CLIMB_FLAG, b);
    }

    @Override
    public Vec3d getVelocity() {
        return super.getVelocity();
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        super.setVelocity(velocity);
    }
}