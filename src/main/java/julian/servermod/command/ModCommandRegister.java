package julian.servermod.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(BadgerTaskCommand::register);
        CommandRegistrationCallback.EVENT.register(BadgerStreakCommand::register);
        CommandRegistrationCallback.EVENT.register(BadgerRewardCommand::register);
    }
}
