package julian.servermod.block.custom.biome;

import com.mojang.serialization.MapCodec;
import julian.servermod.block.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CustomFernBlock
        extends PlantBlock
        implements Fertilizable {
    protected static final float field_31261 = 6.0f;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public CustomFernBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return pos.getY() < -10;

    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        TallPlantBlock tallPlantBlock;

        // here comes in the future more blocks
        tallPlantBlock = (TallPlantBlock)ModBlocks.LARGE_ORANGE_MYCENA_BLOCK;

        if (tallPlantBlock.getDefaultState().canPlaceAt(world, pos) && world.isAir(pos.up())) {
            TallPlantBlock.placeAt(world, tallPlantBlock.getDefaultState(), pos, 2);
            //world.setBlockState(pos, tallPlantBlock.getDefaultState());
        }
    }
}
