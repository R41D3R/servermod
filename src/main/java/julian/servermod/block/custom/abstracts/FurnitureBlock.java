package julian.servermod.block.custom.abstracts;

import julian.servermod.block.ModBlocks;
import julian.servermod.block.custom.abstracts.properties.Coordinate3D;
import julian.servermod.block.custom.abstracts.properties.Coordinate3DProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FurnitureBlock extends DirectionalBlock {
    public static final Coordinate3DProperty MAIN_BLOCK_PROPERTY = CustomProperties.COORDINATE_3D;

    public final int heightOfModelBlock;
    public final ArrayList<int[]> fillBlockCoordinates;

    public FurnitureBlock(Settings settings, int heightOfModelBlock, ArrayList<int[]> fillBlockCoordinates) {
        super(settings);
        this.heightOfModelBlock = heightOfModelBlock;
        this.fillBlockCoordinates = fillBlockCoordinates;
    }

    public int getRotation() {
        // N -> E -> S -> W -> N
        Direction currentDirection = this.getDefaultState().get(FACING);
        return switch (currentDirection) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }



    public BlockPos getBlockPosForBlock(int[] blockCoordinates, BlockPos mainBlockPos) {
        Vector3D vector3D = new Vector3D(blockCoordinates[0], blockCoordinates[1], blockCoordinates[2]);
        vector3D.rotate(getRotation(), 'Z');
        return mainBlockPos.add(((int) vector3D.getX()), ((int) vector3D.getY()), ((int) vector3D.getZ()));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos mainBlockPos = pos.up(heightOfModelBlock);
        for (int[] blockCoordinates : fillBlockCoordinates) {
            BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos);
            if (!world.getBlockState(blockPos).isReplaceable()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        //      2
        //      1
        //  -1  0  1
        //
        BlockPos mainBlockPos = pos.up(heightOfModelBlock);
        world.setBlockState(mainBlockPos, this.getDefaultState());
        for (int[] blockCoordinates : fillBlockCoordinates) {
            BlockPos blockPos = getBlockPosForBlock(blockCoordinates, mainBlockPos);
            world.setBlockState(blockPos, ModBlocks.CUSTOM_BARRIER.getDefaultState()
                    .with(CustomBarrierBlock.MAIN_BLOCK_PROPERTY,
                            new Coordinate3D(mainBlockPos.getX(), mainBlockPos.getY(), mainBlockPos.getZ()))
            );
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Coordinate3D mainBlockCoordinatesFromThisBlock = state.get(CustomBarrierBlock.MAIN_BLOCK_PROPERTY);
        Coordinate3D mainBlockCoordinatesFromNeighborBlock = neighborState.get(CustomBarrierBlock.MAIN_BLOCK_PROPERTY);
        if (mainBlockCoordinatesFromThisBlock.equals(mainBlockCoordinatesFromNeighborBlock)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MAIN_BLOCK_PROPERTY);
        super.appendProperties(builder);
    }
}
