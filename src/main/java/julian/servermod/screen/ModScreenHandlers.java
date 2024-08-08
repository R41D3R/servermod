package julian.servermod.screen;

import julian.servermod.ServerMod;
import julian.servermod.screen.data.BadgerTaskBlockData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

//    public static final ScreenHandlerType<BadgerTaskBlockScreenHandler> BADGER_TASK_BLOCK_SCREEN_HANDLER =
//            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(ServerMod.MOD_ID, "badger_task_block"),
//                    new ExtendedScreenHandlerType<>((syncId, inventory, data) ->
//                            new BadgerTaskBlockScreenHandler(syncId, inventory, data.pos(inventory), new ArrayPropertyDelegate(6)),
//                            BadgerTaskBlockData.PACKET_CODEC
//                    ));

    public static void registerScreenHandlers() {
        ServerMod.LOGGER.info("Registering Screen Handlers for " + ServerMod.MOD_ID);
    }
}
