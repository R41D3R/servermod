package julian.servermod.badgertasks;

import julian.servermod.ServerMod;
import julian.servermod.item.ModItems;
import julian.servermod.utils.DateUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BadgerTaskManager {
    private static final DataStorage STORAGE = new DataStorage();

    public static DataStorage getStorage() {
        return STORAGE;
    }

    public static ActiveBadgerTaskList getActiveBadgerTasks(UUID playerId) {
        return STORAGE.getActiveBadgerTasks(playerId);
    }

    public static void completeBadgerTask(UUID playerId, Item item) {

        STORAGE.completeBadgerTask(playerId, item);
    }

    public static List<ItemStack> getRewardForPlayer(UUID playerId) {
        if (canGetRewardToday(playerId)) {
            setStreak(playerId);
            return calculateReward(playerId);
        }
        return new ArrayList<>();
    }

    public static List<ItemStack> getHypotheticalRewardForPlayer(UUID playerId) {
        if (canGetRewardToday(playerId)) {
            return calculateReward(playerId);
        }
        return null;
    }

    public static boolean resetBadgerTasksIfNecessary(UUID playerId) {
        String lastAssigned = STORAGE.getLastAssignedDay(playerId);
        ServerMod.LOGGER.info(playerId + " Last assigned: " + lastAssigned);
        if (lastAssigned == null || lastAssigned.equals("")) {
            resetBadgerTasks(playerId);
            return true;
        }
        if (DateUtils.durationBetweenDayAndToday(lastAssigned) > 0) {
            resetBadgerTasks(playerId);
            return true;
        }
        return false;
    }

    public static void resetBadgerTasks(UUID playerId) {
        ServerMod.LOGGER.info("Resetting tasks for " + playerId);
        List<PossibleTasks.BadgerTask> randomTasks = PossibleTasks.getFiveDifferentRandomTasks();
        STORAGE.assignBadgerTasks(playerId, randomTasks);
        STORAGE.setLastAssignedDayToToday(playerId);
    }



    public static boolean checkIfAllTasksCompleted(UUID playerId) {
        List<ActiveBadgerTask> activeTasks = STORAGE.getActiveBadgerTasks(playerId);
        for (ActiveBadgerTask task : activeTasks) {
            if (!task.getIsCompleted()) {
                return false;
            }
        }
        return true;
    }

    private static boolean canGetRewardToday(UUID playerId) {
        String LastFinishedDayString = STORAGE.getLastFinishedDay(playerId);
        if (LastFinishedDayString.isEmpty()) {
            return true;
        } else {
            int duration = DateUtils.durationBetweenDayAndToday(LastFinishedDayString);
            return !(duration == 0);
        }


    }

    public static int getStreak(UUID playerId) {
        String LastFinishedDayString = STORAGE.getLastFinishedDay(playerId);
        if (LastFinishedDayString.isEmpty()) {
            return 0;
        }
        int duration = DateUtils.durationBetweenDayAndToday(STORAGE.getLastFinishedDay(playerId));
        if (duration > 6) STORAGE.resetStreak(playerId);
        return STORAGE.getStreak(playerId);
    }

    private static void setStreak(UUID playerId) {
        getStreak(playerId);

        STORAGE.setLastFinishedDaytoToday(playerId);
        STORAGE.incrementStreak(playerId);
    }

    private static List<ItemStack> calculateReward(UUID playerId) {
        int streak = getStreak(playerId);
        List<ItemStack> reward = new ArrayList<>();
        reward.add(new ItemStack(ModItems.RUBY, 100));
        reward.add(new ItemStack(ModItems.CRATE_KEY_RARE, 1));
        if (streak > 5) reward.add(new ItemStack(ModItems.CRATE_KEY_RARE, 1));
        return reward;
    }

    public static List<ItemStack> calculateNextRewards(UUID playerId) {
        int streak = getStreak(playerId) + 1;
        List<ItemStack> reward = new ArrayList<>();
        reward.add(new ItemStack(ModItems.RUBY, 100));
        reward.add(new ItemStack(ModItems.CRATE_KEY_RARE, 1));
        if (streak > 5) reward.add(new ItemStack(ModItems.CRATE_KEY_RARE, 1));
        return reward;
    }
}
