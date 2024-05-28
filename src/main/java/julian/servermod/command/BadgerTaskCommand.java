package julian.servermod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import julian.servermod.badgertasks.ActiveBadgerTask;
import julian.servermod.badgertasks.ActiveBadgerTaskList;
import julian.servermod.badgertasks.BadgerTaskManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BadgerTaskCommand {

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayer();
        ActiveBadgerTaskList tasks = BadgerTaskManager.getActiveBadgerTasks(player.getUuid());
        if (tasks.allCompleted()) {
            player.sendMessage(Text.of("You have completed all your Badger Tasks for today!"));
            return 1;
        }

        player.sendMessage(Text.of("Your Badger Tasks for today are:"));
        for (ActiveBadgerTask task : tasks) {
            if (!task.getIsCompleted()) {
                player.sendMessage(Text.of(task.niceFormattedString()));
            }
        }
        return 1;
    }

    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("badger")
                .then(CommandManager.literal("tasks")
                        .executes(BadgerTaskCommand::run)));
    }
}
