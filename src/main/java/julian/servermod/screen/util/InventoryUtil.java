package julian.servermod.screen.util;


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
}