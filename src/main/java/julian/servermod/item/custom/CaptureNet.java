package julian.servermod.item.custom;

import julian.servermod.ServerMod;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.LevelInfo;

import java.util.List;
import java.util.Optional;

public class CaptureNet extends Item {
    public static final String CAPTURED_ENTITY_TAG = "CapturedEntity";
    public static final String ENTITY_TYPE_TAG = "EntityType";

    public CaptureNet(Settings settings) {
        super(settings);
    }
    // TODO: Implement item functionality

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        // World world = user.getWorld();
        if (hasEntity(stack) || isBlacklisted(entity))
            return ActionResult.FAIL;
        else {
            setCaptureTags(stack, entity);
            entity.discard();
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ServerMod.LOGGER.info("useOnBlock");
        World world = context.getWorld();
        ItemStack stack = context.getPlayer().getMainHandStack();
        BlockPos blockPos = context.getBlockPos().up();
        if (hasEntity(stack) && world.getBlockState(blockPos).isAir()) {
            ServerMod.LOGGER.info("has entity and block is air");
            NbtCompound entityNbt = getCaptureTags(stack).getCompound(CAPTURED_ENTITY_TAG);
            String entityTypeString = getCaptureTags(stack).getString(ENTITY_TYPE_TAG);
            ServerMod.LOGGER.info("nbt of entity: " + entityNbt.toString());
            Optional<? extends EntityType<?>> optionalEntityType = EntityType.get(lastStringIndex(entityTypeString));
            ServerMod.LOGGER.info("optionalEntityType: " + optionalEntityType.toString());
            if (optionalEntityType.isPresent()) {
                ServerMod.LOGGER.info("entity type is present");
                EntityType<?> entityType = optionalEntityType.get();
                Entity entity = entityType.create(world);
                if (entity != null) {
                    ServerMod.LOGGER.info("entity not null");
                    entity.readNbt(entityNbt);
                    entity.setPos(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                    world.spawnEntity(entity);
                    removeCaptureTags(stack);
                    context.getStack().damage(1, context.getPlayer(),
                            playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }

    public static String lastStringIndex(String str) {
        int lastIndex = str.lastIndexOf(".");
        return str.substring(lastIndex + 1);
    }

    public void setCaptureTags(ItemStack stack, LivingEntity entity) {
        stack.getNbt().putString(ENTITY_TYPE_TAG, entity.getType().toString());
        stack.getNbt().put(CAPTURED_ENTITY_TAG, getEntityNbt(entity));
    }

    public NbtCompound getCaptureTags(ItemStack stack) {
        NbtCompound nbt = new NbtCompound();
        nbt.putString(ENTITY_TYPE_TAG, stack.getNbt().getString(ENTITY_TYPE_TAG));
        nbt.put(CAPTURED_ENTITY_TAG, stack.getNbt().get(CAPTURED_ENTITY_TAG));
        return nbt;
    }

    public void removeCaptureTags(ItemStack stack) {
        stack.getNbt().remove(ENTITY_TYPE_TAG);
        stack.getNbt().remove(CAPTURED_ENTITY_TAG);
    }

    public NbtCompound getEntityNbt(LivingEntity entity) {
        NbtCompound nbt = new NbtCompound();
        entity.writeNbt(nbt);
        return nbt;
    }

    public static boolean hasEntity(ItemStack stack) {
        return stack.getNbt().contains(CAPTURED_ENTITY_TAG);
    }

    public static boolean isBlacklisted(LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            return true;
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (hasEntity(stack)) {
            String str = lastStringIndex(stack.getNbt().getString(ENTITY_TYPE_TAG));
            String firstLetterUpperCaseStr = str.substring(0, 1).toUpperCase() + str.substring(1);
            tooltip.add(Text.of("Captured Mob: " + firstLetterUpperCaseStr).copy()
                    .formatted(Formatting.GREEN)
            );
        }



        super.appendTooltip(stack, world, tooltip, context);
    }
}
