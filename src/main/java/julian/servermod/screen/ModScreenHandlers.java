package julian.servermod.screen;

import julian.servermod.ServerMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<PhoenixBlockScreenHandler> PHOENIX_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ServerMod.MOD_ID, "phoenix_block"),
                    new ExtendedScreenHandlerType<>(PhoenixBlockScreenHandler::new));

    public static final ScreenHandlerType<StylingTableMineScreenHandler> STYLING_TABLE_MINE_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ServerMod.MOD_ID, "styling_table_mine"),
                    new ExtendedScreenHandlerType<>(StylingTableMineScreenHandler::new));

    public static final ScreenHandlerType<BoulderBlockScreenHandler> BOULDER_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ServerMod.MOD_ID, "boulder_block"),
                    new ExtendedScreenHandlerType<>(BoulderBlockScreenHandler::new));

    public static final ScreenHandlerType<BadgerTaskBlockScreenHandler> BADGER_TASK_BLOCK_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(ServerMod.MOD_ID, "badger_task_block"),
                    new ExtendedScreenHandlerType<>(BadgerTaskBlockScreenHandler::new));

    public static void registerScreenHandlers() {
        ServerMod.LOGGER.info("Registering Screen Handlers for " + ServerMod.MOD_ID);
    }
}
