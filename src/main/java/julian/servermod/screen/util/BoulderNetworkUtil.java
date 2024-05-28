package julian.servermod.screen.util;

import julian.servermod.ServerMod;
import julian.servermod.screen.BoulderBlockScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;

public class BoulderNetworkUtil {

    public static final Identifier REPAIR_ITEMS_PACKET = new Identifier(ServerMod.MOD_ID, "repair_items");

    public static void init() {

        // Repair items action
        ServerPlayNetworking.registerGlobalReceiver(REPAIR_ITEMS_PACKET,
                (server, player, handler, buf, responseSender) -> {
                    int syncId = buf.readVarInt();
                    ScreenHandler screenHandler = player.currentScreenHandler;

                    if (syncId == screenHandler.syncId && screenHandler instanceof BoulderBlockScreenHandler) {
                        // Repair items
                        BoulderBlockScreenHandler repairScreenHandler = (BoulderBlockScreenHandler) screenHandler;
                        repairScreenHandler.repairAll();

                    }
                });

//        // Get Streak for player
//        ServerPlayNetworking.registerGlobalReceiver(REPAIR_ITEMS_PACKET,
//                (server, player, handler, buf, responseSender) -> {
//                    int syncId = buf.readVarInt();
//                    ScreenHandler screenHandler = player.currentScreenHandler;
//
//                    if (syncId == screenHandler.syncId && screenHandler instanceof BoulderBlockScreenHandler) {
//                        // Repair items
//                        BoulderBlockScreenHandler repairScreenHandler = (BoulderBlockScreenHandler) screenHandler;
//                        repairScreenHandler.repairAll();
//
//                    }
//                });
    }
}