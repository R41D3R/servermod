package julian.servermod.block.furniture;

import julian.servermod.block.custom.abstracts.CustomProperties;
import julian.servermod.block.custom.abstracts.DirectionalBlock;
import julian.servermod.block.custom.abstracts.properties.TripleBlockHalf;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TaskBoardBlock extends DirectionalBlock {
    // TODO: TaskBoardBlock Shape
    public static final IntProperty COLUMN = CustomProperties.TRIPLE_COL_BLOCK;
    public static final EnumProperty<DoubleBlockHalf> HALF = CustomProperties.DOUBLE_BLOCK_HALF;

    private final static VoxelShape NORTH_SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(1.0049819999999998, 0.494598, 0.49493999999999994, 1.243836, 0.733452, 0.49493999999999994),
            VoxelShapes.cuboid(-0.25753199999999987, 0.494598, 0.49493999999999994, -0.018677999999999972, 0.733452, 0.49493999999999994),
            VoxelShapes.cuboid(-0.4281419999999998, 0.392232, 0.49493999999999994, -0.15516599999999992, 0.596964, 0.49493999999999994),
            VoxelShapes.cuboid(1.14147, 1.7229900000000002, 0.49493999999999994, 1.48269, 1.9618440000000001, 0.49493999999999994),
            VoxelShapes.cuboid(1.243836, -0.005592847000000178, 0.45228749999999984, 1.31208, 0.7358099690000001, 0.5375924999999999),
            VoxelShapes.cuboid(-0.32577599999999984, -0.005592847000000178, 0.45228749999999984, -0.25753199999999987, 0.7358099690000001, 0.5375924999999999),
            VoxelShapes.cuboid(-0.34283699999999984, 0.716391, 0.43096124999999996, 1.329141, 1.8424170000000002, 0.5589187499999999),
            VoxelShapes.cuboid(-0.32577599999999984, 0.733452, 0.45228749999999984, 1.31208, 1.8253560000000002, 0.5375924999999999)
    );
    private final static VoxelShape EAST_SHAPE =  VoxelShapes.union(
            VoxelShapes.cuboid(0.510213, 0.494598, 0.9897089999999997, 0.510213, 0.733452, 1.2285629999999998),
            VoxelShapes.cuboid(0.510213, 0.494598, -0.27280499999999996, 0.510213, 0.733452, -0.033951000000000064),
            VoxelShapes.cuboid(0.510213, 0.392232, -0.4434149999999999, 0.510213, 0.596964, -0.170439),
            VoxelShapes.cuboid(0.510213, 1.7229900000000002, 1.126197, 0.510213, 1.9618440000000001, 1.467417),
            VoxelShapes.cuboid(0.46756050000000005, -0.005592847000000178, 1.2285629999999998, 0.5528655000000001, 0.7358099690000001, 1.2968069999999998),
            VoxelShapes.cuboid(0.46756050000000005, -0.005592847000000178, -0.34104899999999994, 0.5528655000000001, 0.7358099690000001, -0.27280499999999996),
            VoxelShapes.cuboid(0.44623425000000005, 0.716391, -0.35810999999999993, 0.57419175, 1.8424170000000002, 1.3138679999999998),
            VoxelShapes.cuboid(0.46756050000000005, 0.733452, -0.34104899999999994, 0.5528655000000001, 1.8253560000000002, 1.2968069999999998)
    );
    private final static VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(-0.2234099999999999, 0.494598, 0.49493999999999994, 0.015444000000000235, 0.733452, 0.49493999999999994),
            VoxelShapes.cuboid(1.039104, 0.494598, 0.49493999999999994, 1.277958, 0.733452, 0.49493999999999994),
            VoxelShapes.cuboid(1.175592, 0.392232, 0.49493999999999994, 1.4485679999999999, 0.596964, 0.49493999999999994),
            VoxelShapes.cuboid(-0.462264, 1.7229900000000002, 0.49493999999999994, -0.12104399999999993, 1.9618440000000001, 0.49493999999999994),
            VoxelShapes.cuboid(-0.29165399999999986, -0.005592847000000178, 0.45228749999999995, -0.2234099999999999, 0.7358099690000001, 0.5375925),
            VoxelShapes.cuboid(1.277958, -0.005592847000000178, 0.45228749999999995, 1.346202, 0.7358099690000001, 0.5375925),
            VoxelShapes.cuboid(-0.30871499999999985, 0.716391, 0.43096124999999996, 1.363263, 1.8424170000000002, 0.5589187499999999),
            VoxelShapes.cuboid(-0.29165399999999986, 0.733452, 0.45228749999999995, 1.346202, 1.8253560000000002, 0.5375925)
    );
    private final static VoxelShape WEST_SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.510213, 0.494598, -0.23868299999999998, 0.510213, 0.733452, 0.00017100000000014326),
            VoxelShapes.cuboid(0.510213, 0.494598, 1.023831, 0.510213, 0.733452, 1.2626849999999998),
            VoxelShapes.cuboid(0.510213, 0.392232, 1.1603189999999999, 0.510213, 0.596964, 1.4332949999999998),
            VoxelShapes.cuboid(0.510213, 1.7229900000000002, -0.4775370000000001, 0.510213, 1.9618440000000001, -0.13631700000000002),
            VoxelShapes.cuboid(0.46756049999999993, -0.005592847000000178, -0.30692699999999995, 0.5528655, 0.7358099690000001, -0.23868299999999998),
            VoxelShapes.cuboid(0.46756049999999993, -0.005592847000000178, 1.2626849999999998, 0.5528655, 0.7358099690000001, 1.3309289999999998),
            VoxelShapes.cuboid(0.44623425000000005, 0.716391, -0.32398799999999994, 0.57419175, 1.8424170000000002, 1.3479899999999998),
            VoxelShapes.cuboid(0.46756049999999993, 0.733452, -0.30692699999999995, 0.5528655, 1.8253560000000002, 1.3309289999999998)
    );

    public TaskBoardBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        VoxelShape baseShape;

        switch (direction) {
            case EAST:
                baseShape = EAST_SHAPE;
                break;
            case SOUTH:
                baseShape = SOUTH_SHAPE;
                break;
            case WEST:
                baseShape = WEST_SHAPE;
                break;
            default:
                baseShape = NORTH_SHAPE;
                break;
        }

        // Offset for upper half
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            baseShape = baseShape.offset(0, -1, 0);
        }

        // Offset for columns
        int column = state.get(COLUMN);
        double offsetX = 0;
        double offsetZ = 0;

        switch (direction) {
            case NORTH:
                offsetX = column == 1 ? 1 : (column == 3 ? -1 : 0);
                break;
            case EAST:
                offsetZ = column == 1 ? 1 : (column == 3 ? -1 : 0);
                break;
            case SOUTH:
                offsetX = column == 1 ? -1 : (column == 3 ? 1 : 0);
                break;
            case WEST:
                offsetZ = column == 1 ? -1 : (column == 3 ? 1 : 0);
                break;
        }

        return baseShape.offset(-offsetX, 0, -offsetZ);
        // return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos1) {
        BlockPos pos2 = pos1.up();
        BlockPos pos3 = getBlockPos(pos1, state, 2, "right");
        BlockPos pos4 = getBlockPos(pos2, state, 2, "right");
        BlockPos pos5 = getBlockPos(pos1, state, 2, "left");
        BlockPos pos6 = getBlockPos(pos2, state, 2, "left");

        for (BlockPos pos : new BlockPos[]{pos1, pos2, pos3, pos4, pos5, pos6}) {
            if (!world.getBlockState(pos).isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        Direction facing = state.get(FACING);
        world.setBlockState(pos, this.getDefaultState().with(FACING, facing).with(HALF, DoubleBlockHalf.LOWER).with(COLUMN, 2), Block.NOTIFY_ALL);
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(COLUMN, 2), Block.NOTIFY_ALL);
        world.setBlockState(getBlockPos(pos, state, 2, "right"), state.with(HALF, DoubleBlockHalf.LOWER).with(COLUMN, 3), Block.NOTIFY_ALL);
        world.setBlockState(getBlockPos(pos.up(), state, 2, "right"), state.with(HALF, DoubleBlockHalf.UPPER).with(COLUMN, 3), Block.NOTIFY_ALL);
        world.setBlockState(getBlockPos(pos, state, 2, "left"), state.with(HALF, DoubleBlockHalf.LOWER).with(COLUMN, 1), Block.NOTIFY_ALL);
        world.setBlockState(getBlockPos(pos.up(), state, 2, "left"), state.with(HALF, DoubleBlockHalf.UPPER).with(COLUMN, 1), Block.NOTIFY_ALL);
    }

//    @Override
//    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
//        if (neighborState.isOf(this)) {
//            return state.with(FACING, neighborState.get(FACING));
//        }
//        return Blocks.AIR.getDefaultState();
//        // return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
//    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // ABC
        // DEF
        if (player != null) {


        if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(COLUMN) == 1) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right", 2), false);
            world.breakBlock(pos.up(), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "right", 2), false);
            return;
        }
        else if (state.get(HALF) == DoubleBlockHalf.UPPER && state.get(COLUMN) == 1) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right", 2), false);
            world.breakBlock(pos.down(), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "right", 2), false);
            return;
        }
        else if (state.get(HALF) == DoubleBlockHalf.UPPER && state.get(COLUMN) == 2) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left"), false);
            world.breakBlock(pos.down(), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "left"), false);
            return;
        } else if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(COLUMN) == 2) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left"), false);
            world.breakBlock(pos.up(), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "right"), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "left"), false);
            return;
        }
        else if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(COLUMN) == 3) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left", 2), false);
            world.breakBlock(pos.up(), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "left"), false);
            world.breakBlock(getBlockPos(pos.up(), state, state.get(COLUMN), "left", 2), false);
            return;
        }
        else if (state.get(HALF) == DoubleBlockHalf.UPPER && state.get(COLUMN) == 3) {
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left"), false);
            world.breakBlock(getBlockPos(pos, state, state.get(COLUMN), "left", 2), false);
            world.breakBlock(pos.down(), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "left"), false);
            world.breakBlock(getBlockPos(pos.down(), state, state.get(COLUMN), "left", 2), false);
            return;
        }

        super.onBreak(world, pos, state, player);
        }
    }

//    @Override
//    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
//        if (state.get(HALF) != DoubleBlockHalf.LOWER && state.get(COLUMN) != 2)
//            return List.of();
//        else return super.getDroppedStacks(state, builder);
//    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, COLUMN);
        super.appendProperties(builder);

    }

    private BlockPos getBlockPos(BlockPos pos, BlockState state, int col, String direction) {
        return getBlockPos(pos, state, col, direction, 1);
    }

    private BlockPos getBlockPos(BlockPos pos, BlockState state, int col, String direction, int i) {
        //     N
        //  W     E
        //     S

        // ABC
        // DEF
        if (col == 1) {
                if (state.get(FACING) == Direction.NORTH) {
                    if (direction == "right") return pos.offset(Direction.WEST, i);
                }
                if (state.get(FACING) == Direction.WEST) {
                    if (direction == "right") return pos.offset(Direction.SOUTH, i);
                }
                if (state.get(FACING) == Direction.SOUTH) {
                    if (direction == "right") return pos.offset(Direction.EAST, i);
                }
                if (state.get(FACING) == Direction.EAST) {
                    if (direction == "right") return pos.offset(Direction.NORTH, i);
                }
        } else if (col == 2) {
            if (state.get(FACING) == Direction.NORTH) {
                if (direction == "right") return pos.offset(Direction.WEST, i);
                if (direction == "left") return pos.offset(Direction.EAST, i);
            }
            if (state.get(FACING) == Direction.WEST) {
                if (direction == "right") return pos.offset(Direction.SOUTH, i);
                if (direction == "left") return pos.offset(Direction.NORTH, i);
            }
            if (state.get(FACING) == Direction.SOUTH) {
                if (direction == "right") return pos.offset(Direction.EAST, i);
                if (direction == "left") return pos.offset(Direction.WEST, i);
            }
            if (state.get(FACING) == Direction.EAST) {
                if (direction == "right") return pos.offset(Direction.NORTH, i);
                if (direction == "left") return pos.offset(Direction.SOUTH, i);
            }
        } else if (col == 3) {
            if (state.get(FACING) == Direction.NORTH) {
                if (direction == "left") return pos.offset(Direction.EAST, i);
            }
            if (state.get(FACING) == Direction.WEST) {
                if (direction == "left") return pos.offset(Direction.NORTH, i);
            }
            if (state.get(FACING) == Direction.SOUTH) {
                if (direction == "left") return pos.offset(Direction.WEST, i);
            }
            if (state.get(FACING) == Direction.EAST) {
                if (direction == "left") return pos.offset(Direction.SOUTH, i);
            }
        }
        return null;
    }
}