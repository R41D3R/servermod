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
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class LootBalloonEntity extends PathAwareEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private Vec3d initialPlayerPos;
    private Vec3d direction;
    private double startY;
    private long spawnTime;


    public LootBalloonEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return PathAwareEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0f);
    }

//    public void initializeDirection(Vec3d initialPlayerPos) {
//        this.initialPlayerPos = initialPlayerPos;
//        this.direction = initialPlayerPos.subtract(this.getPos()).normalize();
//        this.startY = this.getY();
//        this.spawnTime = getWorld().getTime();
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        // Move in the direction of the player position at spawn
//        double time = getWorld().getTime() - this.spawnTime;
//        double verticalMotion = Math.sin(time / 10.0) * 0.5; // Sine wave for up and down motion
//        this.setVelocity(this.direction.x, verticalMotion, this.direction.z);
//        this.velocityDirty = true;
//        this.move(MovementType.SELF, this.getVelocity());
//
//        // Despawn after 1 minute
//        if (time > 1200) {
//            this.discard();
//        }
//    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!getWorld().isClient && source.getAttacker() instanceof PlayerEntity) {
            popBalloon();
            return true;
        }
        playEffects();
        return super.damage(source, amount);
    }

    private void playEffects() {
        BlockPos pos = new BlockPos((int)this.getX(), (int)this.getY(), (int)this.getZ()).up(2);
        getWorld().playSound(this.getX(), this.getY(), this.getZ(), ModSounds.POP, SoundCategory.AMBIENT, 1.0f, 1.0f, false);

        double radius = 0.5; // Adjust the radius as needed
        int particleCount = 2; // Adjust the number of particles as needed
        for (int i = 0; i < particleCount; i++) {
            double x = pos.getX() + (getWorld().random.nextDouble() - 0.5) * radius * 2;
            double y = pos.getY() + (getWorld().random.nextDouble() - 0.5) * radius;
            double z = pos.getZ() + (getWorld().random.nextDouble() - 0.5) * radius * 2;
            getWorld().addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
        }
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
}
