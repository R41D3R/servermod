package julian.servermod.spawner;

import julian.servermod.ServerMod;
import julian.servermod.entity.ModEntities;
import julian.servermod.entity.custom.LootBalloonEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.spawner.Spawner;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class LootBalloonSpawner implements Spawner {
    private static final int SPAWN_INTERVAL = (int)20 * 60 * 5; // 5 minutes in ticks (20 ticks per second * 300 seconds)
    private static final double SPAWN_CHANCE = 0.025; // 2.5% chance
    private static final int SPAWN_RADIUS = 30;
    private static final int SPAWN_HEIGHT_OFFSET = 25;
    private final Random random = new Random();
    private int spawnTimer;

    public LootBalloonSpawner() {
        this.spawnTimer = SPAWN_INTERVAL;
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        //ServerMod.LOGGER.info("Check if I can spawn balloon " + this.spawnTimer);
        if (this.spawnTimer > 0) {
            this.spawnTimer--;
            return 0;
        }

        this.spawnTimer = SPAWN_INTERVAL;

        for (ServerPlayerEntity player : world.getPlayers()) {
            if (this.random.nextDouble() <= SPAWN_CHANCE) {
                BlockPos playerPos = player.getBlockPos();
                //ServerMod.LOGGER.info("player pos was " + playerPos);
                BlockPos spawnPos = this.getSpawnPosition(world, player);
                //ServerMod.LOGGER.info("spawn pos was " + spawnPos);
                if (spawnPos != null) {
                    LootBalloonEntity lootBalloon = ModEntities.LOOT_BALLOON.spawn(world, spawnPos, SpawnReason.EVENT);
                    if (lootBalloon != null) {
                        // Additional setup for the loot balloon if needed
                        //lootBalloon.refreshPositionAndAngles(spawnPos, 0, 0);
                        // lootBalloon.initializeDirection(new Vec3d(playerPos.getX(), playerPos.getY(), playerPos.getZ()));
                        // player.sendMessage(Text.of("Balloon spawned at: " + spawnPos));
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    @Nullable
    private BlockPos getSpawnPosition(World world, ServerPlayerEntity player) {
        BlockPos playerPos = player.getBlockPos();
        for (int i = 0; i < 10; ++i) {
            int x = playerPos.getX() + this.random.nextInt(2*SPAWN_RADIUS) - SPAWN_RADIUS;
            int z = playerPos.getZ() + this.random.nextInt(2*SPAWN_RADIUS) - SPAWN_RADIUS;
//            int x = playerPos.getX();
//            int z = playerPos.getZ();
            int y = world.getTopY(Heightmap.Type.WORLD_SURFACE, x, z) + SPAWN_HEIGHT_OFFSET;
            BlockPos potentialPos = new BlockPos(x, y, z);
            //ServerMod.LOGGER.info("potential pos " + potentialPos);
            if (isValidSpawnPosition(world, potentialPos)) {
                return potentialPos;
            }
        }
        return null;
    }

    private boolean isValidSpawnPosition(World world, BlockPos pos) {
        return world.getBlockState(pos).isAir();
    }
}
