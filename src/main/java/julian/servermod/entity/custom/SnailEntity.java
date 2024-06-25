package julian.servermod.entity.custom;

import julian.servermod.entity.ai.goal.EatLeafLitterGoal;
import julian.servermod.entity.ai.goal.EatLitterGoal;
import julian.servermod.entity.ai.goal.FindLitterGoal;
import julian.servermod.entity.ai.goal.HideGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class SnailEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    protected int litterCount = 0;

    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new HideGoal(this));
        this.goalSelector.add(1, new EatLeafLitterGoal(this, 1, 12, 1));
        //this.goalSelector.add(2, new FindLitterGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0D));
        // this.goalSelector.add(3, new LookAroundGoal(this));
    }


    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 0.15f, 1.0f);
    }

    @Override
    public void travel(Vec3d vec3) {
        if (this.canHide()) {
            this.setVelocity(this.getVelocity().multiply(0, 1, 0));
            vec3 = vec3.multiply(0, 1, 0);
        }
        super.travel(vec3);
    }

    public void onEatingLitter() {
        this.litterCount++;
        if (this.litterCount >= 8) {
            this.litterCount = 0;

            // spawn slimeball
            ItemEntity itemEntity = new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(Items.SLIME_BALL));
            itemEntity.setPickupDelay(20);
            getWorld().spawnEntity(itemEntity);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.BEETROOT);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.canHide()) {
            return false;
        }
        return super.damage(source, amount);
    }

    public boolean canHide() {
        PlayerEntity closestPlayer = this.getWorld().getClosestPlayer(this, 2.0D);
        return closestPlayer != null;
    }


    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }


    @Override
    public boolean canBreatheInWater() {
        return true;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if (this.canHide()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snail.hide", Animation.LoopType.LOOP));
        } else if (this.isClimbing()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snail.climb", Animation.LoopType.LOOP));
        } else {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.snail.default", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
