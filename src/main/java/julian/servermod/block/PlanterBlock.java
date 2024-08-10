package julian.servermod.block;

import julian.servermod.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlanterBlock extends Block {
    public static final BooleanProperty WET = BooleanProperty.of("wet");
    public static final BooleanProperty SOIL = BooleanProperty.of("soil");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {

        builder.add(WET, SOIL);
        //builder.add(SOIL);
    }


    public PlanterBlock(FabricBlockSettings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(WET, false).with(SOIL, false));
        //setDefaultState(getDefaultState().with(SOIL, false));
    } 
}
