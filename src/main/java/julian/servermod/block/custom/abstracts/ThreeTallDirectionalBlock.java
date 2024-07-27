package julian.servermod.block.custom.abstracts;

import julian.servermod.block.custom.abstracts.properties.TripleBlockHalf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ThreeTallDirectionalBlock extends DirectionalBlock{
    public static final EnumProperty<TripleBlockHalf> HALF = CustomProperties.TRIPLE_BLOCK_HALF;

    public ThreeTallDirectionalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos1) {
        BlockPos pos2 = pos1.up();
        BlockPos pos3 = pos1.up(2);

        for (BlockPos pos : new BlockPos[]{pos1, pos2, pos3}) {
            if (!world.getBlockState(pos).isReplaceable()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, TripleBlockHalf.MIDDLE), Block.NOTIFY_ALL);
        world.setBlockState(pos.up(2), (BlockState)state.with(HALF, TripleBlockHalf.UPPER), Block.NOTIFY_ALL);
        world.setBlockState(pos, (BlockState)state.with(HALF, TripleBlockHalf.LOWER), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        TripleBlockHalf tripleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y
                && tripleBlockHalf == TripleBlockHalf.LOWER == (direction == Direction.UP)) {
            if (neighborState.isOf(this)) return state.with(FACING, neighborState.get(FACING));
            return Blocks.AIR.getDefaultState();
        }
        if (direction.getAxis() == Direction.Axis.Y
                && tripleBlockHalf == TripleBlockHalf.MIDDLE == (direction == Direction.UP || direction == Direction.DOWN)) {
            if (neighborState.isOf(this)) return state.with(FACING, neighborState.get(FACING));
            return Blocks.AIR.getDefaultState();
        }
        if (direction.getAxis() == Direction.Axis.Y
                && tripleBlockHalf == TripleBlockHalf.UPPER == (direction == Direction.DOWN)) {
            if (neighborState.isOf(this)) return state.with(FACING, neighborState.get(FACING));
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HALF) == TripleBlockHalf.LOWER) {
            BlockPos pos2 = pos.up();
            world.breakBlock(pos2, false);
        }
        else if (state.get(HALF) == TripleBlockHalf.UPPER) {
            BlockPos pos2 = pos.down();
            world.breakBlock(pos2, false);
        }

        super.onBreak(world, pos, state, player);
    }


    //    @Override
//    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        this.onBreak(world, pos, state, client.player);
//        // super.onStateReplaced(state, world, pos, state.with(HALF, TripleBlockHalf.MIDDLE).with(FACING, state.get(FACING)), moved);
//    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        if (state.get(HALF) != TripleBlockHalf.MIDDLE)
            return List.of();
        else return super.getDroppedStacks(state, builder);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF);
        super.appendProperties(builder);
    }
}
