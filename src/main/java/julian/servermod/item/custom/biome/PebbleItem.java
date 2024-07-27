package julian.servermod.item.custom.biome;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.block.custom.biome.Pebble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class PebbleItem extends BlockItem {
    public PebbleItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        if (state.isOf(ModBlocks.PEBBLES_BLOCK)) {
            ServerMod.LOGGER.info("PebbleItem:useOnBlock: state is of PebblesBlock");
            Block block = state.getBlock();
            Pebble pebble = (Pebble) block;
            int current_size = state.get(Pebble.SIZE);
            ServerMod.LOGGER.info("PebbleItem:useOnBlock: current_size: " + current_size);
            if (current_size == 3) {
                return ActionResult.FAIL;
            }
            playPlaceSound(state, context.getWorld(), context.getBlockPos(), context.getPlayer());
            context.getWorld().setBlockState(context.getBlockPos(),
                    state.with(IntProperty.of("size", 1, 3), current_size + 1));
            return ActionResult.CONSUME;
        }
        return super.useOnBlock(context);
    }

    public void playPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity) {
        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        world.playSound(playerEntity, pos, this.getPlaceSound(state), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(playerEntity, state));
    }

    protected SoundEvent getPlaceSound(BlockState state) {
        return SoundEvents.BLOCK_STONE_PLACE;
    }
}
