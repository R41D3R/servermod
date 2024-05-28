package julian.servermod.item.custom;

import julian.servermod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class WateringCan extends Item {


    public WateringCan(Settings settings) {

        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {


        PlayerEntity player = context.getPlayer();
        BlockPos positionClicked = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(positionClicked);
        BlockHitResult blockHitResult = WateringCan.raycast(context.getWorld(), player, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockPos blockPos = blockHitResult.getBlockPos();

        if (context.getWorld().getFluidState(blockPos).isIn(FluidTags.WATER)) {
            context.getStack().setDamage(0);
            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1, 1);
            return ActionResult.SUCCESS;
        }

        if (context.getStack().getDamage() == 5) {
            return ActionResult.FAIL;
        }

        if (state.isOf(ModBlocks.PLANTER)) {
            context.getWorld().setBlockState(positionClicked, state.with(BooleanProperty.of("wet"), true));
        }

        context.getWorld().addParticle(ParticleTypes.RAIN, positionClicked.toCenterPos().getX(), positionClicked.getY() + 1.0, positionClicked.toCenterPos().getZ(), 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.SPLASH, positionClicked.toCenterPos().getX() - 0.25, positionClicked.getY() + 1.0, positionClicked.toCenterPos().getZ() - 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.SPLASH, positionClicked.toCenterPos().getX() - 0.25, positionClicked.getY() + 1.0, positionClicked.toCenterPos().getZ() + 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.SPLASH, positionClicked.toCenterPos().getX() + 0.25, positionClicked.getY() + 1.0, positionClicked.toCenterPos().getZ() - 0.25, 0.0, 0.0, 0.0);
        context.getWorld().addParticle(ParticleTypes.RAIN, positionClicked.toCenterPos().getX() + 0.25, positionClicked.getY() + 1.0, positionClicked.toCenterPos().getZ() + 0.25, 0.0, 0.0, 0.0);

        player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1, 1);
        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
        return ActionResult.SUCCESS;

    }

    protected static BlockHitResult raycast(World world, PlayerEntity player, RaycastContext.FluidHandling fluidHandling) {
        float f = player.getPitch();
        float g = player.getYaw();
        Vec3d vec3d = player.getEyePos();
        float h = MathHelper.cos(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float i = MathHelper.sin(-g * ((float)Math.PI / 180) - (float)Math.PI);
        float j = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float k = MathHelper.sin(-f * ((float)Math.PI / 180));
        float l = i * j;
        float m = k;
        float n = h * j;
        double d = 5.0;
        Vec3d vec3d2 = vec3d.add((double)l * 5.0, (double)m * 5.0, (double)n * 5.0);
        return world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.OUTLINE, fluidHandling, player));
    }

//    @Override
//    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
//        tooltip.add(Text.literal("Used for watering Planter Boxes. Can be refilled by right-clicking on Water."));
//    }



}
