package julian.servermod.item.custom.biome;

import julian.servermod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class MossItem extends Item {

    public MossItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        WorldView world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if (this.canPlantOnTop(context.getWorld(), pos)) {
            if (!state.isReplaceable()) pos = pos.up();
            context.getWorld().setBlockState(pos, ModBlocks.MOSS_COVER_BLOCK.getDefaultState());
            playPlaceSound(state, context.getWorld(), pos, context.getPlayer());
            return ActionResult.CONSUME;
        }

        if (ModBlocks.MOSS_HANG_BLOCK.getDefaultState().canPlaceAt(world, pos)) {
            context.getWorld().setBlockState(pos.down(), ModBlocks.MOSS_HANG_BLOCK.getDefaultState());
            playPlaceSound(state, context.getWorld(), pos, context.getPlayer());
            return ActionResult.CONSUME;
        }
        return ActionResult.FAIL;
    }

    protected boolean canPlantOnTop(World world, BlockPos pos) {
        return !world.isAir(pos.down());
    }

    public void playPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity playerEntity) {
        BlockSoundGroup blockSoundGroup = state.getSoundGroup();
        world.playSound(playerEntity, pos, this.getPlaceSound(state), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
        world.emitGameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Emitter.of(playerEntity, state));
    }

    protected SoundEvent getPlaceSound(BlockState state) {
        return SoundEvents.BLOCK_SPORE_BLOSSOM_PLACE;
    }

}


