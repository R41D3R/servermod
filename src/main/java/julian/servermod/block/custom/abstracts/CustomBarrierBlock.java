package julian.servermod.block.custom.abstracts;

import julian.servermod.block.custom.abstracts.properties.Coordinate3D;
import julian.servermod.block.custom.abstracts.properties.Coordinate3DProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class CustomBarrierBlock extends Block {
    public static final DirectionProperty CUBE_DIRECTION = Properties.FACING;
    public static final Coordinate3DProperty MAIN_BLOCK_PROPERTY = CustomProperties.COORDINATE_3D;

    public CustomBarrierBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(CUBE_DIRECTION, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CUBE_DIRECTION, MAIN_BLOCK_PROPERTY);
        super.appendProperties(builder);
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
}
