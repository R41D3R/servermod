package julian.servermod.screen.util;

import io.netty.buffer.Unpooled;
import julian.servermod.ServerMod;
import julian.servermod.badgertasks.ActiveBadgerTaskList;
import julian.servermod.badgertasks.PossibleTasks;
import julian.servermod.screen.BadgerTaskBlockScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.village.TradeOfferList;

import java.util.ArrayList;
import java.util.List;

public class BadgerTaskClientNetworkUtil {

    public static void init() {

        // Sync client's trade offers
        ClientPlayNetworking.registerGlobalReceiver(BadgerTaskNetworkUtil.SYNC_BADGER_TASKS,
                (client, handler, buf, responseSender) -> {
                    int syncId = buf.readVarInt();
                    ActiveBadgerTaskList tasks = ActiveBadgerTaskList.fromPacket(buf);
                    int streak = buf.readInt();
                    List<ItemStack> rewards = new ArrayList<>();
                    while (buf.readableBytes() > 0) {
                        rewards.add(buf.readItemStack());
                    }

                    ServerMod.LOGGER.info("Received Task Sync Package from Server");

                    client.execute(() -> {
                        ScreenHandler screenHandler = client.player.currentScreenHandler;

                        if (syncId == screenHandler.syncId && screenHandler instanceof BadgerTaskBlockScreenHandler) {
                            BadgerTaskBlockScreenHandler badgerScreenHandler = (BadgerTaskBlockScreenHandler) screenHandler;
                            badgerScreenHandler.tasks = tasks;
                            badgerScreenHandler.streak = streak;
                            badgerScreenHandler.rewards = rewards;
                        }
                    });
                });
    }

    public static void askForBadgerTasksSync(BadgerTaskBlockScreenHandler handler) {
        ServerMod.LOGGER.info("Ask For Task Sync from Client");
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeVarInt(handler.syncId);
        ClientPlayNetworking.send(BadgerTaskNetworkUtil.SYNC_BADGER_TASKS, buf);
    }

}