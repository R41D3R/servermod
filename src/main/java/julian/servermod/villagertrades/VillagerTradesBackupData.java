package julian.servermod.villagertrades;

import julian.servermod.utils.IEntityDataSaver;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class VillagerTradesBackupData {
    public static NbtCompound getBackupTradeData(IEntityDataSaver villager) {
        NbtCompound nbt = villager.getPersistentData();
        if (!nbt.contains("TradeBackup"))
            return null;
        return nbt.getCompound("TradeBackup");
    }

    public static void setBackupTradeData(IEntityDataSaver villager, NbtCompound backupData) {
        NbtCompound nbt = villager.getPersistentData();
        nbt.put("TradeBackup", backupData);
    }

    public static void doTradeInBackupData(IEntityDataSaver villager, TradeOffer offer) {
        NbtCompound nbt = villager.getPersistentData();
        if (!nbt.contains("TradeBackup"))
            return;
        NbtCompound backupData = nbt.getCompound("TradeBackup");
        TradeOfferList backedUpTrades = new TradeOfferList(backupData);
        for (TradeOffer possibleOffer : backedUpTrades) {
            if (tradesAreEqual(offer, possibleOffer)) {
                possibleOffer.use();
                setBackupTradeData(villager, backedUpTrades.toNbt());
            }
        }
    }

    public static boolean tradesAreEqual(TradeOffer trade1, TradeOffer trade2) {
        return trade1.getOriginalFirstBuyItem().getItem() == trade2.getOriginalFirstBuyItem().getItem() &&
                trade1.getSecondBuyItem().getItem() == trade2.getSecondBuyItem().getItem() &&
                trade1.getSellItem().getItem() == trade2.getSellItem().getItem();
    }

    public static boolean tradeIsInTradeOfferList(TradeOfferList offers, TradeOffer trade) {
        for (TradeOffer offer : offers) {
            if (tradesAreEqual(offer, trade)) {
                return true;
            }
        }
        return false;
    }
}
