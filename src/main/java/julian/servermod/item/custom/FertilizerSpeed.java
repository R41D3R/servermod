package julian.servermod.item.custom;

import julian.servermod.block.custom.crop.DailyCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FertilizerSpeed extends Item {
    public FertilizerSpeed(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof DailyCropBlock) {
            DailyCropBlock cropBlock = (DailyCropBlock) block;
            int age = cropBlock.getAge(state);
            int maxAge = cropBlock.getMaxAge();

            if (age < maxAge) {
                world.setBlockState(pos, cropBlock.withAge(age + 1));
                context.getStack().decrement(1);
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }
}
