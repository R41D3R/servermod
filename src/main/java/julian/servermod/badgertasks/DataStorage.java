package julian.servermod.badgertasks;

import julian.servermod.ServerMod;
import julian.servermod.utils.DateUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtIo;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataStorage implements AutoCloseable{
    private static final File FILE = new File("mods/servermod/badgertask_data.nbt");
    private final NbtCompound data;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DataStorage() {
        try {
            FILE.getParentFile().mkdirs();
            if (!FILE.exists()) {
                data = new NbtCompound();
                save();
            } else {
                data = NbtIo.readCompressed(FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() {
        try {
            ServerMod.LOGGER.info("Saving badger task data");
            NbtIo.writeCompressed(data, FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        save();
    }

    public void assignBadgerTasks(UUID player, List<PossibleTasks.BadgerTask> tasks) {
        NbtList list = getActiveBadgerTasksTag(player);
        list.clear();
        for (PossibleTasks.BadgerTask task : tasks) {
            ServerMod.LOGGER.info(task.toString());
            list.add(createActiveBadgerTaskTag(task));
        }
        ServerMod.LOGGER.info("tasks");

    }

    private static NbtCompound createActiveBadgerTaskTag(PossibleTasks.BadgerTask badgerTask) {
        NbtCompound tag = new NbtCompound();

        tag.putInt("item", Item.getRawId(badgerTask.getItem()));
        tag.putBoolean("completed", false);
        tag.putInt("required_amount", badgerTask.getRandomAmount());
        return tag;
    }

    public ActiveBadgerTaskList getActiveBadgerTasks(UUID player) {
        NbtList list = getActiveBadgerTasksTag(player);
        ServerMod.LOGGER.info("active badger task list" + list);

        ActiveBadgerTaskList output = new ActiveBadgerTaskList();
        for (NbtElement tag : list) {
            NbtCompound compound = (NbtCompound) tag;

            Item item = Item.byRawId(compound.getInt("item"));
            boolean isCompleted = compound.getBoolean("completed");
            int requiredAmount = compound.getInt("required_amount");
            output.add(new ActiveBadgerTask(item, isCompleted, requiredAmount));
        }
        return output;
    }

    public String getLastAssignedDay(UUID player) {
        return data.getCompound("last_assigned").getString(player.toString());
    }

    public void setLastAssignedDayToToday(UUID player) {
        NbtCompound lastAssigned = getOrCreateTag(data, "last_assigned");
        lastAssigned.putString(player.toString(), DateUtils.getTodayDateAsString());
        ServerMod.LOGGER.info("Last assigned day set to today for " + player + " " + lastAssigned.getString(player.toString()));
        ServerMod.LOGGER.info("lastAssigned" + lastAssigned);
    }

    public String getLastFinishedDay(UUID player) {
        return data.getCompound("last_finished").getString(player.toString());
    }

    public void setLastFinishedDaytoToday(UUID player) {
        NbtCompound lastAssigned = getOrCreateTag(data, "last_finished");
        lastAssigned.putString(player.toString(), DateUtils.getTodayDateAsString());
    }

    public void resetStreak(UUID player) {
        NbtCompound tag = getOrCreateTag(data, "streak");
        tag.putInt(player.toString(), 0);
    }

    public void incrementStreak(UUID player) {
        NbtCompound tag = getOrCreateTag(data, "streak");
        int streak = tag.getInt(player.toString());
        tag.putInt(player.toString(), streak + 1);
    }

    public int getStreak(UUID player) {
        NbtCompound tag = getOrCreateTag(data, "streak");
        return tag.getInt(player.toString());
    }



    public void completeBadgerTask(UUID player, Item givenItem) {
        ServerMod.LOGGER.info("Completing task for " + player + " " + givenItem);
        NbtList list = getActiveBadgerTasksTag(player);
        for (NbtElement tag : list) {
            NbtCompound compound = (NbtCompound) tag;

            Item taskItem = Item.byRawId(compound.getInt("item"));
            ServerMod.LOGGER.info("taskItem" + taskItem);
            if (taskItem == givenItem) {
                compound.putBoolean("completed", true);
                ServerMod.LOGGER.info("Task completed in data storage");
                break;
            }
        }
    }


    private NbtList getActiveBadgerTasksTag(UUID player) {
        NbtCompound activeMissions = getOrCreateTag(data, "active_badger_tasks");
        ServerMod.LOGGER.info("activeMissions" + activeMissions);
        return getOrCreateList(activeMissions, player.toString());
    }

    private static NbtCompound getOrCreateTag(NbtCompound tag, String key) {
        NbtCompound compound = tag.getCompound(key);
        if (!tag.contains(key)) tag.put(key, compound);
        return compound;
    }

    private static NbtList getOrCreateList(NbtCompound tag, String key) {
        if (!tag.contains(key)) tag.put(key, new NbtList());
        return (NbtList) tag.get(key);
//        ServerMod.LOGGER.info("compound" + tag.get(key));
//        NbtList list = tag.getList(key, NbtElement.LIST_TYPE);
//
//        ServerMod.LOGGER.info("getOrCreateList" + list);
//        if (!tag.contains(key)) tag.put(key, list);
//        return list;
    }


}


