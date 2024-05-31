package julian.servermod.mixin;

import com.google.common.collect.ImmutableList;
import julian.servermod.MinecraftServerSupplier;
import julian.servermod.ServerMod;
import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.spawner.LootBalloonSpawner;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.RandomSequencesState;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.WanderingTraderManager;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.Spawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "runServer", at = @At("HEAD"))
    private void runServer(CallbackInfo ci) {
        MinecraftServerSupplier.setServer((MinecraftServer) (Object) this);
    }

    @Inject(method = "saveAll", at = @At("TAIL"))
    private void onChunkSaving(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
        BadgerTaskManager.getStorage().save();
    }

    @Redirect(method = "createWorlds", at = @At(value = "NEW", target = "net/minecraft/server/world/ServerWorld"))
    private ServerWorld redirectServerWorldConstructor(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List spawners, boolean shouldTickTime, RandomSequencesState randomSequencesState) {
        // code here

        if (worldKey == World.OVERWORLD) {
            // Create a new mutable list and add all elements from the original list
            List<Spawner> newSpawners = new ArrayList<>(spawners);

            // Add your new spawner to the list
            newSpawners.add(new LootBalloonSpawner());

            ServerMod.LOGGER.info("Added LootBalloonSpawner to spawners for " + worldKey.getValue());
            return new ServerWorld(server, workerExecutor, session, properties, worldKey, dimensionOptions, worldGenerationProgressListener, debugWorld, seed, newSpawners, true, null);
        }
        ServerMod.LOGGER.info("Added LootBalloonSpawner not to spawners for " + worldKey.getValue());

        return new ServerWorld(server, workerExecutor, session, properties, worldKey, dimensionOptions, worldGenerationProgressListener, debugWorld, seed, spawners, true, null);

        // Call the original ServerWorld constructor
    }
}