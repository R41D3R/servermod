package julian.servermod.block.custom.abstracts;

import com.mojang.serialization.MapCodec;
import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.entity.CoordinateEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FurnitureBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public final int heightOfModelBlock;
    public final ArrayList<int[]> fillBlockCoordinates;

    public FurnitureBlock(Settings settings, int heightOfModelBlock, ArrayList<int[]> fillBlockCoordinates) {
        super(settings);
        this.heightOfModelBlock = heightOfModelBlock;
        this.fillBlockCoordinates = fillBlockCoordinates;
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));

    }

    public int getRotation(BlockState state) {
        // N -> E -> S -> W -> N
        Direction currentDirection = state.get(FACING);
        return switch (currentDirection) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }



    public BlockPos getBlockPosForBlock(int[] blockCoordinates, BlockPos mainBlockPos, BlockState state) {
        Vector3D vector3D = new Vector3D(blockCoordinates[0], blockCoordinates[1], blockCoordinates[2]);
        vector3D.rotate(getRotation(state), 'Z');
        return mainBlockPos.add(((int) vector3D.getX()), ((int) vector3D.getZ()), ((int) vector3D.getY()));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos mainBlockPos = pos.up(heightOfModelBlock);
        ServerMod.LOGGER.info("Try to place main Block Pos: " + mainBlockPos);
        for (int[] blockCoordinates : fillBlockCoordinates) {
            BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos, state);
            if (!world.getBlockState(blockPos).isReplaceable()) {
                ServerMod.LOGGER.info("Block at " + blockPos + " is not replaceable");
                return false;
            }
        }

        return true;
    }

    private void setMainBlockPos(World world, BlockPos pos, BlockPos mainBlockPos) {
        if (!world.isClient){
            ServerMod.LOGGER.info("Try to set mainblockpos for" + pos);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            ServerMod.LOGGER.info("BlockEntity: " + blockEntity);
            if (blockEntity instanceof CoordinateEntity){
                CoordinateEntity coordinateEntity = (CoordinateEntity) blockEntity;
                coordinateEntity.mainBlockPos = mainBlockPos;
                ServerMod.LOGGER.info("Set main Block Pos to " + mainBlockPos);
            }
        }
    }

    private BlockPos getMainBlockPos(WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CoordinateEntity){
            CoordinateEntity coordinateEntity = (CoordinateEntity) blockEntity;
            ServerMod.LOGGER.info("Get main Block Pos from " + coordinateEntity.mainBlockPos);
            return coordinateEntity.mainBlockPos;
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        //      2
        //      1
        //  -1  0  1
        //
        world.removeBlock(pos, false);
        BlockPos mainBlockPos = pos.up(heightOfModelBlock);
        world.setBlockState(mainBlockPos, state);
        setMainBlockPos(world, mainBlockPos, mainBlockPos);
        for (int[] blockCoordinates : fillBlockCoordinates) {
            BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos, state);
            world.setBlockState(blockPos, ModBlocks.CUSTOM_BARRIER.getDefaultState());
            setMainBlockPos(world, blockPos, mainBlockPos);
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient){
            BlockPos mainBlockPos = getMainBlockPos(world, pos);
            if (mainBlockPos != null) {
                for (int[] blockCoordinates : fillBlockCoordinates) {
                    BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos, state);
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                }
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockPos mainBlockCoordinatesFromThisBlock = getMainBlockPos(world, pos);
        BlockPos mainBlockCoordinatesFromNeighborBlock = getMainBlockPos(world, neighborPos);
        ServerMod.LOGGER.info("getStateForNeighborUpdate" + mainBlockCoordinatesFromThisBlock + " -> " + mainBlockCoordinatesFromNeighborBlock);
        if (mainBlockCoordinatesFromThisBlock == null) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (mainBlockCoordinatesFromThisBlock.equals(mainBlockCoordinatesFromNeighborBlock)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient){
            BlockPos mainBlockPos = getMainBlockPos(world, pos);
            for (int[] blockCoordinates : fillBlockCoordinates) {
                BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos, state);
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoordinateEntity(pos, state);
    }
}
