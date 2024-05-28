package julian.servermod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import julian.servermod.badgertasks.BadgerTaskManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.List;

public class BadgerRewardCommand {

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayer();
        List<ItemStack> rewards = BadgerTaskManager.calculateNextRewards(player.getUuid());

        player.sendMessage(Text.of("Your next rewards are:"));
        for (ItemStack reward : rewards) {
            player.sendMessage(Text.of(reward.getCount() + " x " + reward.getItem().getName().getString()));
        }


        return 1;
    }

    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("badger")
                .then(CommandManager.literal("rewards")
                        .executes(BadgerRewardCommand::run)));
    }
}
