package julian.servermod.badgertasks;

import julian.servermod.utils.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class BadgerClubData {
    private static final String field_name = "TotalBadgerTasks";

    public static int getTotalBadgerTasks(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(field_name);
    }

    public static void setTotalBadgerTasks(IEntityDataSaver player, int new_value) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(field_name, new_value);
    }

    public static void incrementTotalBadgerTasks(IEntityDataSaver player) {
        int totalTasks = getTotalBadgerTasks(player);
        setTotalBadgerTasks(player, totalTasks + 1);
    }
}
