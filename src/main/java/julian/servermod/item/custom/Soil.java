package julian.servermod.item.custom;

import julian.servermod.block.ModBlocks;
import julian.servermod.block.PlanterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

public class Soil extends Item {
    public Soil(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {


        PlayerEntity player = context.getPlayer();
        BlockPos positionClicked = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(positionClicked);

        if (state.isOf(ModBlocks.PLANTER)) {
            context.getStack().decrement(1);
            context.getWorld().setBlockState(positionClicked, state.with(PlanterBlock.SOIL, true));
            addParticles(context, positionClicked);
            player.playSound(SoundEvents.ITEM_BONE_MEAL_USE, 1, 1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;

    }

    public void addParticles(ItemUsageContext context, BlockPos positionClicked) {
        context.getWorld().addParticle(ParticleTypes.COMPOSTER, positionClicked.toCenterPos().getX(), positionClicked.getY() + 1.3, positionClicked.toCenterPos().getZ(), 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.COMPOSTER, positionClicked.toCenterPos().getX() - 0.15, positionClicked.getY() + 1.1, positionClicked.toCenterPos().getZ() - 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.COMPOSTER, positionClicked.toCenterPos().getX() - 0.25, positionClicked.getY() + 1.25, positionClicked.toCenterPos().getZ() + 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.COMPOSTER, positionClicked.toCenterPos().getX() + 0.35, positionClicked.getY() + 1.4, positionClicked.toCenterPos().getZ() - 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.COMPOSTER, positionClicked.toCenterPos().getX() + 0.25, positionClicked.getY() + 1.27, positionClicked.toCenterPos().getZ() + 0.25, 0.0, 0.0, 0.0);

    }
}
