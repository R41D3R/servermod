package julian.servermod.item.custom.biome;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.custom.biome.Pebble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;

public class RocksItem extends BlockItem {
    public RocksItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) {
            return ActionResult.SUCCESS;
        }
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.isOf(ModBlocks.ROCKS_BLOCK)) {
            ServerMod.LOGGER.info("RocksItem:useOnBlock: state is of ROCK_BLOCK");
            Block block = state.getBlock();
            Pebble pebble = (Pebble) block;
            int current_size = state.get(Pebble.SIZE);
            ServerMod.LOGGER.info("RocksItem:useOnBlock: current_size: " + current_size);
            if (current_size == 3) {
                return ActionResult.FAIL;
            }
            context.getWorld().setBlockState(context.getBlockPos(),
                    state.with(IntProperty.of("size", 1, 3), current_size + 1));
            return ActionResult.CONSUME;
        }
        return super.useOnBlock(context);
    }
}
