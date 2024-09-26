package julian.servermod.entity.custom;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.custom.BalloonLootCrateBlock;
import julian.servermod.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;


import java.util.EnumSet;
import java.util.Objects;


public class LootBalloonEntity extends FlyingEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public LootBalloonEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new LootBalloonMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PathAwareEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0f);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FlyRandomlyGoal(this));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.POP;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity || source.getSource() instanceof ProjectileEntity) {
//            if (this.getWorld().isClient) {
//
//            }

            if (!this.getWorld().isClient) {
                playEffects(((PlayerEntity) source.getAttacker()));
                popBalloon();
            }
            return true;
        }
        return false; // Prevent the entity from taking damage from other sources
    }

    private void runParticleCommand(PlayerEntity player, double x, double y, double z) {
        String command = String.format("/particle explosion %.3f %.3f %.3f 0.000 0.000 0.000 1 0 force", x, y, z);
        CommandManager commandManager = Objects.requireNonNull(player.getServer()).getCommandManager();
        ServerCommandSource commandSource = player.getServer().getCommandSource();
        commandManager.executeWithPrefix(commandSource, command);
    }

    private void playEffects(PlayerEntity player) {
        BlockPos pos = new BlockPos((int)this.getX(), (int)this.getY(), (int)this.getZ()).up(2);
//        getWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSounds.POP, SoundCategory.AMBIENT, 1.0f, 1.0f, false);

        double radius = 0.5; // Adjust the radius as needed
        int particleCount = 2; // Adjust the number of particles as needed
        for (int i = 0; i < particleCount; i++) {
            double x = pos.getX() + (getWorld().random.nextDouble() - 0.5) * radius * 2;
            double y = pos.getY() + (getWorld().random.nextDouble() - 0.5) * radius;
            double z = pos.getZ() + (getWorld().random.nextDouble() - 0.5) * radius * 2;
            // getWorld().addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
            runParticleCommand(player, x, y, z);
        }
        ServerMod.LOGGER.info("Show particles at " + pos);

    }

    private void popBalloon() {
        // Replace balloon with a falling block (crate)

        BlockPos pos = new BlockPos(((int)this.getX())-1, ((int)this.getY())-1, ((int)this.getZ())-1);
        BlockState crateState = ModBlocks.BALLOON_CRATE_BLOCK.getDefaultState(); // Use appropriate crate block
        getWorld().setBlockState(pos, crateState);
        ServerMod.LOGGER.info("Balloon popped at " + pos);

        this.discard();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    class WanderAroundGoal extends Goal {
        private final FlyingEntity entity;
        private final double speed;
        private double targetX;
        private double targetY;
        private double targetZ;
        private double sinOffset;

        public WanderAroundGoal(FlyingEntity entity, double speed) {
            this.entity = entity;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public void start() {
            this.sinOffset = this.entity.getWorld().getTime() + this.entity.getId();
        }

        @Override
        public void tick() {
            if (this.entity.getRandom().nextInt(120) == 0) {
                this.targetX = this.entity.getX() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
                this.targetY = this.entity.getY();
                this.targetZ = this.entity.getZ() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
            }

//            if (this.entity.getRandom().nextInt(200) == 0) {
//                this.targetY = this.entity.getY() + (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 16.0D;
//            }

            double time = this.entity.getWorld().getTime() + this.sinOffset;
            double x = this.targetX; // Sin wave for horizontal motion
            double y = this.targetY; //+ Math.sin(time / 10.0) * 0.5; // Sin wave for vertical motion
            double z = this.targetZ; // Sin wave for horizontal motion

            //this.entity.setVelocity(this.entity.getVelocity().x, 0, this.entity.getVelocity().z);
        }
    }

    class LootBalloonMoveControl extends MoveControl {
        private final LootBalloonEntity entity;
        private int collisionCheckCooldown;

        private long lastTime = 0;
        private double lastXvec = 0;
        private double lastZvec = 0;
        private long waitTime = 0;

        public LootBalloonMoveControl(LootBalloonEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @Override
        public void tick() {
            if (this.state != State.MOVE_TO) {
                return;
            }
            if (this.collisionCheckCooldown-- <= 0) {
                this.collisionCheckCooldown += this.entity.getRandom().nextInt(5) + 2;
                Vec3d vec3d = new Vec3d(this.targetX - this.entity.getX(), this.targetY - this.entity.getY(), this.targetZ - this.entity.getZ());
                double d = vec3d.length();
                if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
                    this.entity.setVelocity(this.entity.getVelocity().add(vec3d.multiply(0.1)));
                } else {
                    this.state = State.WAIT;
                }
            }



            // Add vertical movement
            double yOffset = Math.sin(this.entity.age * 0.2) * 0.05;

            // add direction that changes every 5-10 seconds
            if ((this.lastXvec == 0 && this.lastZvec == 0) ||  (this.entity.getWorld().getTime() - this.lastTime > this.waitTime)) {
                this.lastXvec = (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 0.1;
                this.lastZvec = (this.entity.getRandom().nextDouble() * 2.0D - 1.0D) * 0.1;
                this.lastTime = this.entity.getWorld().getTime();
                this.waitTime = 20L * (10 + this.entity.getRandom().nextInt(10));
            }

            this.entity.setVelocity(this.lastXvec, yOffset, this.lastZvec);
            // this.entity.setVelocity(this.entity.getVelocity().x, yOffset, this.entity.getVelocity().z);

        }

        private boolean willCollide(Vec3d direction, int steps) {
            // Implement collision detection logic here if needed
            return false;
        }
    }

    static class FlyRandomlyGoal
            extends Goal {
        private final LootBalloonEntity ghast;

        public FlyRandomlyGoal(LootBalloonEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            double f;
            double e;
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            }
            double d = moveControl.getTargetX() - this.ghast.getX();
            double g = d * d + (e = moveControl.getTargetY() - this.ghast.getY()) * e + (f = moveControl.getTargetZ() - this.ghast.getZ()) * f;
            return g < 1.0 || g > 3600.0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.ghast.getRandom();
            double d = this.ghast.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 8.0f);
            double e = this.ghast.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 8.0f);
            double f = this.ghast.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 8.0f);
            this.ghast.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }
}
