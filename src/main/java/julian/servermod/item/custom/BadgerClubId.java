package julian.servermod.item.custom;

import julian.servermod.badgertasks.ActiveBadgerTask;
import julian.servermod.badgertasks.ActiveBadgerTaskList;
import julian.servermod.badgertasks.BadgerClubData;
import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.utils.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class BadgerClubId extends Item {
    public BadgerClubId(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            int totalBadgerTasks = BadgerClubData.getTotalBadgerTasks((IEntityDataSaver) user);
            MutableText baseText = Text.literal("Total Badger Tasks: ");
            MutableText numberText = Text.literal(String.valueOf(totalBadgerTasks)).setStyle(Style.EMPTY.withColor(Formatting.GREEN));
            MutableText finalText = baseText.append(numberText);
            // user.sendMessage(finalText, false);

            int streak = BadgerTaskManager.getStreak(user.getUuid());
            MutableText baseTextStreak = Text.literal("Current Streak: ");
            MutableText numberTextStreak = Text.literal(String.valueOf(streak)).setStyle(Style.EMPTY.withColor(Formatting.GREEN));
            MutableText finalTextStreak = baseTextStreak.append(numberTextStreak);
            user.sendMessage(finalTextStreak, false);

            ActiveBadgerTaskList tasks = BadgerTaskManager.getActiveBadgerTasks(user.getUuid());
            if (tasks.allCompleted()) {
                user.sendMessage(Text.of("You have completed all your Badger Tasks for today!").copy().formatted(Formatting.GREEN));
            } else {
                user.sendMessage(Text.of("Your Badger Tasks for today are:"));
                for (ActiveBadgerTask task : tasks) {
                    if (!task.getIsCompleted()) {
                        user.sendMessage(Text.of(task.niceFormattedString()).copy().formatted(Formatting.GREEN));
                    }
                }
            }

            List<ItemStack> rewards = BadgerTaskManager.calculateNextRewards(user.getUuid());
            user.sendMessage(Text.of("Your next rewards are:"));
            for (ItemStack reward : rewards) {
                user.sendMessage(Text.of(reward.getCount() + " x " + reward.getItem().getName().getString()).copy().formatted(Formatting.GREEN));
            }
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Use with right click to get").copy()
                .formatted(Formatting.GRAY)
        );
        tooltip.add(Text.of("your Badger Club Data").copy()
                .formatted(Formatting.GRAY)
        );
        super.appendTooltip(stack, context, tooltip, type);
    }
}
