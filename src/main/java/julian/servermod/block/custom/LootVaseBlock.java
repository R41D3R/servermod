package julian.servermod.block.custom;

import julian.servermod.utils.AllCustomLootTables;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LootVaseBlock extends LootBoxBlock {
    private static final VoxelShape COLLISION_SHAPE = LootVaseBlock.createCuboidShape(3, 0, 3, 13, 13, 13);

    public LootVaseBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (!EnchantmentHelper.hasAnyEnchantmentsWith(tool, EnchantmentEffectComponentTypes.BLOCK_EXPERIENCE)) {
            // random change to spawn a silverfish instead of dropping loot
            if (world.random.nextInt(10) == 0) {

                // spawn 1-3 silverfish or phantoms
                int numMobs = world.random.nextInt(3) + 1;
                for (int i = 0; i < numMobs; i++) {
                    if (world.random.nextInt(2) == 0) {
                        PhantomEntity phantom = EntityType.PHANTOM.create(world);
                        phantom.refreshPositionAndAngles(pos, 0, 0);
                        world.spawnEntity(phantom);
                    }
                    else {
                        SilverfishEntity silverfish = EntityType.SILVERFISH.create(world);
                        silverfish.refreshPositionAndAngles(pos, 0, 0);
                        world.spawnEntity(silverfish);
                    }

                }
            } else {
                // drop loot
                ServerWorld serverWorld = world.getServer().getWorld(world.getRegistryKey());
                dropExperience(serverWorld, pos);
                List<ItemStack> randomLoot = AllCustomLootTables.URN_LOOT_TABLE.getRandomLoot(world.getRegistryManager(),4);
                for (ItemStack itemStack : randomLoot) {
                    spawnItemStack(serverWorld, pos, itemStack);
                }
            }
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);

    }

    public void spawnItemStack(ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
        itemEntity.setPickupDelay(20);
        serverWorld.spawnEntity(itemEntity);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ePos) {
        return COLLISION_SHAPE;
    }
}