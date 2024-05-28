package julian.servermod.screen.util;

import julian.servermod.ServerMod;
import julian.servermod.badgertasks.ActiveBadgerTask;
import julian.servermod.badgertasks.ActiveBadgerTaskList;
import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.screen.BadgerTaskBlockScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class BadgerTaskNetworkUtil {

    public static final Identifier SYNC_BADGER_TASKS = new Identifier(ServerMod.MOD_ID, "sync_badger_tasks");
    public static final Identifier COMPLETE_BADGER_TASK = new Identifier(ServerMod.MOD_ID, "complete_badger_task");


    public static void init() {

        // sync badger tasks data
        ServerPlayNetworking.registerGlobalReceiver(SYNC_BADGER_TASKS,
                (server, player, handler, buf, responseSender) -> {
                    int syncId = buf.readVarInt();
                    ScreenHandler screenHandler = player.currentScreenHandler;

                    ServerMod.LOGGER.info("Received Task Sync Package");

                    if (syncId == screenHandler.syncId && screenHandler instanceof BadgerTaskBlockScreenHandler) {
                        // Sync badger tasks data with client
                        BadgerTaskBlockScreenHandler badgerScreenHandler = (BadgerTaskBlockScreenHandler) screenHandler;
                        ActiveBadgerTaskList tasks = BadgerTaskManager.getActiveBadgerTasks(player.getUuid());
                        badgerScreenHandler.tasks = tasks;
                        badgerScreenHandler.streak = BadgerTaskManager.getStreak(player.getUuid());
                        badgerScreenHandler.rewards = BadgerTaskManager.getHypotheticalRewardForPlayer(player.getUuid());
                        BadgerTaskNetworkUtil.syncBadgerTasks(player, syncId, tasks, badgerScreenHandler.streak, badgerScreenHandler.rewards);
                    }
                });

        // complete badger task
        ServerPlayNetworking.registerGlobalReceiver(COMPLETE_BADGER_TASK,
                (server, player, handler, buf, responseSender) -> {
                    int syncId = buf.readVarInt();
                    Item item = buf.readItemStack().getItem();
                    ScreenHandler screenHandler = player.currentScreenHandler;

                    ServerMod.LOGGER.info("Received Complete Task Package");

                    if (syncId == screenHandler.syncId && screenHandler instanceof BadgerTaskBlockScreenHandler) {
                        // Sync badger tasks data with client
                        BadgerTaskBlockScreenHandler badgerScreenHandler = (BadgerTaskBlockScreenHandler) screenHandler;
                        BadgerTaskManager.completeBadgerTask(player.getUuid(), item);
                        List<ItemStack> reward = BadgerTaskManager.getRewardForPlayer(player.getUuid());
                        if (reward != null) {
                            for (ItemStack itemStack : reward) {
                                player.giveItemStack(itemStack);
                            }
                        }
                    }
                });
    }

    public static void syncBadgerTasks(ServerPlayerEntity player, int syncId, ActiveBadgerTaskList tasks, int streak, List<ItemStack> rewards) {
        // Create packet
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarInt(syncId);
        tasks.toPacket(buf);
        buf.writeInt(streak);
        for (ItemStack reward : rewards) {
            buf.writeItemStack(reward);
        }

        ServerMod.LOGGER.info("Send Task Sync Package from Server");
        // Send message
        ServerPlayNetworking.send(player, BadgerTaskNetworkUtil.SYNC_BADGER_TASKS, buf);
    }

}