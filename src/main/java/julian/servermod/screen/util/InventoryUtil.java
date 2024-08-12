package julian.servermod.screen.util;


import julian.servermod.ServerMod;
import julian.servermod.utils.playerdata.WalletData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class InventoryUtil {

    public static boolean canAfford(PlayerInventory playerInventory, int cost, Item item) {
        return countItems(playerInventory, item) >= cost;
    }

    public static int countItems(PlayerInventory playerInventory, Item item) {
        int count = 0;
        for (final var stack : playerInventory.offHand) {
            if (stack.getItem() == item)
                count += stack.getCount();
        }
        for (final var stack : playerInventory.main) {
            if (stack.getItem() == item)
                count += stack.getCount();
        }
        return count;
    }

    public static void removeItemsFromInventory(PlayerInventory playerInventory, Item item, int count) {
        for (final var stack : playerInventory.offHand) {
            if (count <= 0)
                break;
            if (stack.getItem() == item) {
                int remove = Math.min(stack.getCount(), count);
                stack.decrement(remove);
                count -= remove;
            }
        }

        for (final var stack : playerInventory.main) {
            if (count <= 0)
                break;
            if (stack.getItem() == item) {
                int remove = Math.min(stack.getCount(), count);
                stack.decrement(remove);
                count -= remove;
            }
        }
    }

    public static int countItemsWallet(PlayerEntity player, Item item) {
        return WalletData.getWalletItemCountForItem(player, item);
    }

    public static int countItemsWithWalletAndInventory(PlayerEntity player, Item item) {
        ServerMod.LOGGER.info("Counting items in inventory and wallet for " + item.toString() + "...");
        int count_inventory = countItems(player.getInventory(), item);
        int count_wallet = countItemsWallet(player, item);
        ServerMod.LOGGER.info("Counted " + count_inventory + " in inventory and " + count_wallet + " in wallet for " + item.toString() + ".");
        return count_inventory + count_wallet;
    }

    public static boolean canAffordWithWalletAndInventory(PlayerEntity player, int cost, Item item) {
        int count = 0;
        count += countItems(player.getInventory(), item);
        count += countItemsWallet(player, item);
        return count >= cost;
    }
}