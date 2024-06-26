package julian.servermod.utils;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class CrateParticles {
    private static int stepX = 1;
    private static final int particles = 2;
    private static final int particlesPerRotation = 20;
    private static final double radius = 1;

    public static void updateTimers() {
        stepX++;
    }

    public static void spawnSpiralParticles(ServerPlayerEntity player, BlockPos pos, ServerWorld world) {
        for (int stepY = 0; stepY < 60; stepY += (120 / particles)) {
            double dx = -(Math.cos(((stepX + stepY) / (double) particlesPerRotation) * Math.PI * 2)) * radius;
            double dy = stepY / (double) particlesPerRotation / 2.0;
            double dz = -(Math.sin(((stepX + stepY) / (double) particlesPerRotation) * Math.PI * 2)) * radius;
            double x = pos.getX() + 0.5 + dx;
            double y = pos.getY() + 0.5 + dy;
            double z = pos.getZ() + 0.5 + dz;
            ParticleS2CPacket particlePacket = new ParticleS2CPacket(
                    ParticleTypes.SOUL_FIRE_FLAME, false, x, y, z, 0.0f, 0.0f, 0.0f, 0.0f, 1
            );
            player.networkHandler.sendPacket(particlePacket);
        }
    }

    public static void spawnCrossSpiralsParticles(ServerPlayerEntity player, BlockPos pos, ServerWorld world) {
        for (int stepY = 0; stepY < 60; stepY += (120 / particles)) {
            double dx = -(Math.cos(((stepX + stepY) / (double) particlesPerRotation) * Math.PI * 2)) * radius;
            double dy = stepY / (double) particlesPerRotation / 2.0;
            double dz = -(Math.sin(((stepX + stepY) / (double) particlesPerRotation) * Math.PI * 2)) * radius;
            double x = pos.getX() + 0.5 + dx;
            double y = pos.getY() + 1.5 + dy;
            double z = pos.getZ() + 0.5 + dz;
            ParticleS2CPacket particlePacket = new ParticleS2CPacket(
                    ParticleTypes.FIREWORK, false, x, y, z, 0.0f, 0.0f, 0.0f, 0.0f, 1
            );
            player.networkHandler.sendPacket(particlePacket);
        }
    }

    public static void rewardParticles(ServerPlayerEntity player, BlockPos pos) {
        ServerWorld world = player.getServerWorld();
        for (int i = 0; i < 5; i++) {
            world.playSound(
                    null, pos, SoundEvents.ENTITY_ALLAY_DEATH, SoundCategory.BLOCKS, 0.5f, 0.5f
            );
        }
        double offsetX = 0.5;
        double offsetY = 0.2;
        double offsetZ = 0.5;
        ParticleS2CPacket particlePacket = new ParticleS2CPacket(
                ParticleTypes.SCULK_SOUL,
                false,
                pos.getX() + offsetX,
                pos.getY() + 0.5 + offsetY,
                pos.getZ() + offsetZ,
                0.0f,
                0.0f,
                0.0f,
                0.1f,
                50
        );
        player.networkHandler.sendPacket(particlePacket);
        player.networkHandler.sendPacket(particlePacket);
    }
}