package julian.servermod.block.custom.biome;

import julian.servermod.ServerMod;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.custom.SnailEntity;
import julian.servermod.item.ModItems;
import julian.servermod.item.custom.SnailItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SnailPebble extends HorizontalFacingBlock {
    public static final IntProperty SIZE = IntProperty.of("size", 1, 3);
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 2, 16);

    public SnailPebble(Settings settings) {
        super(settings);

        // set size random
        this.setDefaultState(getDefaultState().with(SIZE, 1));
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return !world.getBlockState(pos.down()).isAir();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlantOnTop(state, world, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

//    @Override
//    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        super.onPlaced(world, pos, state, placer, itemStack);
//        world.setBlockState(pos, (BlockState)state.with(SIZE, (int) (Math.random() * 3) + 1), Block.NOTIFY_ALL);
//    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void playSpawnEffects(World world, BlockPos pos) {

            for(int i = 0; i < 5; ++i) {
                double d = world.random.nextGaussian() * 0.02;
                double e = world.random.nextGaussian() * 0.02;
                double f = world.random.nextGaussian() * 0.02;
                double g = 0.1;
                ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5 + d, pos.getY() + 0.5 + e, pos.getZ() + 0.5 + f, 1, 0.0, 0.0, 0.0, g);
            }

    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (world.random.nextInt(2) == 1) {
            playSpawnEffects(world, pos);

            SnailItem.spawnSnail((ServerWorld) world, pos);

        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SIZE);
        builder.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}
