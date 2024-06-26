package julian.servermod.utils;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrateParticleAnimationSystem {
    private static final Map<BlockPos, ParticleAnimation> activeAnimations = new HashMap<>();
    private static final int ANIMATION_DURATION = 200; // Duration in ticks

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                updateAnimations(world);
            }
        });
    }

    public static void startAnimation(ServerPlayerEntity player, BlockPos pos) {
        activeAnimations.put(pos, new ParticleAnimation(player.getUuid()));
    }

    private static void updateAnimations(ServerWorld world) {
        activeAnimations.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            ParticleAnimation animation = entry.getValue();
            ServerPlayerEntity player = world.getServer().getPlayerManager().getPlayer(animation.playerUuid);

            if (animation.remainingTicks > 0) {
                CrateParticles.spawnCrossSpiralsParticles(player, pos, world);
                animation.remainingTicks--;
                return false; // Keep the animation active
            } else {
                return true; // Remove the animation if the player is not found, in a different world, or the timer has expired
            }
        });

        CrateParticles.updateTimers();
    }

    private static class ParticleAnimation {
        final UUID playerUuid;
        int remainingTicks;

        ParticleAnimation(UUID playerUuid) {
            this.playerUuid = playerUuid;
            this.remainingTicks = ANIMATION_DURATION;
        }
    }
}