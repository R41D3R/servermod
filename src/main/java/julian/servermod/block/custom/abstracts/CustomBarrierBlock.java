package julian.servermod.block.custom.abstracts;

import com.mojang.serialization.MapCodec;
import julian.servermod.ServerMod;
import julian.servermod.block.entity.CoordinateEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CustomBarrierBlock extends BlockWithEntity {

    public CustomBarrierBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    private BlockPos getMainBlockPos(WorldAccess world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CoordinateEntity){
            CoordinateEntity coordinateEntity = (CoordinateEntity) blockEntity;
            return coordinateEntity.mainBlockPos;
        }
        return null;
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
            ServerMod.LOGGER.info("Break Block at " + mainBlockPos);
            //world.setBlockState(mainBlockPos, Blocks.AIR.getDefaultState());
            world.breakBlock(mainBlockPos, true);
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoordinateEntity(pos, state);
    }
}
