package julian.servermod.block.custom.crop;

import julian.servermod.block.ModBlocks;
import julian.servermod.block.PlanterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DailyCropBlock extends CropBlock {
    public int MAX_AGE;

    public static final IntProperty LAST_GROWTH_DAY = IntProperty.of("last_growth_day", 0, 6); // 0 = Monday, 6 = Sunday

    private static final int GROWTH_HOUR = 0; // Hour for growth
    private static final int GROWTH_MINUTE = 0; // Minute for growth, change this for testing
    private static final ZoneId GERMAN_TIME_ZONE = ZoneId.of("Europe/Berlin");

    private static final int tickSchedule = 300; // Schedule next check in approximately 1 minute (1200 ticks)

    public DailyCropBlock(Settings settings, int maxAge) {
        super(settings);
        this.MAX_AGE = maxAge;

        ZonedDateTime now = ZonedDateTime.now(GERMAN_TIME_ZONE);
        ZonedDateTime yesterday = now.minusDays(1);
        DayOfWeek yesterdayDayOfWeek = yesterday.getDayOfWeek();
        int yesterdayDayOfWeekValue = yesterdayDayOfWeek.getValue() - 1; // Convert DayOfWeek to 0-based (Monday = 0, Sunday = 6)

        this.setDefaultState(this.stateManager.getDefaultState().with(LAST_GROWTH_DAY, yesterdayDayOfWeekValue));
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LAST_GROWTH_DAY);

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        checkAndGrow(world, pos, state);
        // Schedule next check in approximately 1 minute (1200 ticks)
        world.scheduleBlockTick(pos, this, tickSchedule);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        checkAndGrow(world, pos, state);
        // Schedule next check in approximately 1 minute (1200 ticks)
        world.scheduleBlockTick(pos, this, tickSchedule);
    }

    public static void growParticles(World world, BlockPos pos) {
    }

    private void growCrop(World world, BlockPos pos, BlockState state, int currentDayOfWeekValue) {
        int age = this.getAge(state);

        // don't grow if not moist
        if (!isMoisture(world, pos) || !isSoiled(world, pos)) {
            world.setBlockState(pos, this.withAge(age).with(LAST_GROWTH_DAY, currentDayOfWeekValue), 2);
            return;
        }

        if (age < this.getMaxAge()) {
            // grow the crop
            world.setBlockState(pos, this.withAge(age + 1).with(LAST_GROWTH_DAY, currentDayOfWeekValue), 2);
            world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(BooleanProperty.of("wet"), false), 2);

            if (this.getAge(state) == MAX_AGE) {
                // remove soil if fully grown
                world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(BooleanProperty.of("soil"), false), 2);
            }
        }
    }


    private void checkAndGrow(World world, BlockPos pos, BlockState state) {


        ZonedDateTime now = ZonedDateTime.now(GERMAN_TIME_ZONE);
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();
        int currentDayOfWeekValue = currentDayOfWeek.getValue() - 1; // Convert DayOfWeek to 0-based (Monday = 0, Sunday = 6)
        LocalTime currentTime = now.toLocalTime();
        LocalTime growthTime = LocalTime.of(GROWTH_HOUR, GROWTH_MINUTE);

        int lastGrowthDay = state.get(LAST_GROWTH_DAY);

        // check if it's time to grow
        if (currentDayOfWeekValue != lastGrowthDay && // BUG: This will not grow if one week is skipped
                currentTime.isAfter(growthTime)) {

            growCrop(world, pos, state, currentDayOfWeekValue);
        }
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(BooleanProperty.of("soil"), false), 2);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(ModBlocks.PLANTER);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);

    }

    protected static boolean isMoisture(BlockView world, BlockPos pos) {
        BlockPos planterPos = pos.down();
        BlockState blockState = world.getBlockState(planterPos);
        return blockState.get(PlanterBlock.WET);
    }

    protected static boolean isSoiled(BlockView world, BlockPos pos) {
        BlockPos planterPos = pos.down();
        BlockState blockState = world.getBlockState(planterPos);
        return blockState.get(PlanterBlock.SOIL);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return false;
    }
}
