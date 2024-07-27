package julian.servermod.item.custom;

import julian.servermod.ServerMod;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.custom.SnailEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class SnailItem extends Item {


    public SnailItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ServerMod.LOGGER.info("useOnBlock SnailItem");
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos().up();

        if (world.getBlockState(blockPos).isAir()) {
            if (world.isClient) {
                return ActionResult.SUCCESS;
            }
            spawnSnail((ServerWorld) world, blockPos);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }

    public static void spawnSnail(ServerWorld world, BlockPos blockPos) {
        EntityType<SnailEntity> snail = ModEntities.SNAIL;
        SnailEntity snailEntity = snail.create(world);
        if (snailEntity != null) {
            Vec3d spawnPos = new Vec3d(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            snailEntity.refreshPositionAndAngles(spawnPos.x, spawnPos.y, spawnPos.z, 0, 0);
            world.spawnEntityAndPassengers(snailEntity);

            // Force position update
            snailEntity.setVelocity(Vec3d.ZERO);
            snailEntity.setPosition(spawnPos);

            // Send spawn packet to all tracking clients
            world.getChunkManager().sendToOtherNearbyPlayers(snailEntity, new EntitySpawnS2CPacket(snailEntity));
        }
    }
}
