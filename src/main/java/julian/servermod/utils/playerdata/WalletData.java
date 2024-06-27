package julian.servermod.utils.playerdata;

import julian.servermod.ServerMod;
import julian.servermod.utils.IEntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class WalletData {
    private static final String field_name = "WalletItems";

    public static WalletInventory getPlayerWallet(PlayerEntity player) {
        NbtList list;
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        if (nbt.contains(field_name)) {
            ServerMod.LOGGER.info("Contains key: " + nbt.contains(field_name));
            list = (NbtList) nbt.get(field_name);
        } else {
            list = new NbtList();
        }
        ServerMod.LOGGER.info("Reading wallet from NBT: " + list.toString());
        ServerMod.LOGGER.info("Reading wallet from NBT: " + nbt.toString());
        WalletInventory wallet = new WalletInventory(list);
        return wallet;
    }

    public static void setPlayerWallet(PlayerEntity player, NbtList new_wallet) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        nbt.put(field_name, new_wallet);
        player.readNbt(nbt);
    }

    public static int getWalletItemCountForItem(PlayerEntity player, Item item) {
        WalletInventory wallet = getPlayerWallet(player);

        for (var entry : wallet.stacks.entrySet()) {
            Item stack_item = entry.getKey();
            ServerMod.LOGGER.info("Checking item in wallet: " + stack_item.toString());
            if (stack_item == item) {
                ServerMod.LOGGER.info("Found item in wallet: " + stack_item.toString());
                return (int) entry.getValue();
            }
        }

        return 0;
    }

    public static void removeItemCountFromWallet(PlayerEntity player, Item item, int count) {
        WalletInventory wallet = getPlayerWallet(player);
        ServerMod.LOGGER.info("Removing " + count + " of " + item.toString() + " from wallet..."+ wallet.stacks.toString());
        for (var entry : wallet.stacks.entrySet()) {
            if (entry.getKey().equals(item)) {
                entry.setValue(entry.getValue() - count);
                break;
            }
        }
        ServerMod.LOGGER.info("After removing " + count + " of " + item.toString() + " from wallet..."+ wallet.stacks.toString());
        setPlayerWallet(player, wallet.toNbtList());
    }
}
