package julian.servermod.badgertasks;

import net.minecraft.item.Item;

public class ActiveBadgerTask {
    private final Item item;
    private boolean isCompleted;
    private final int requiredAmount;


    ActiveBadgerTask(Item item, boolean isCompleted, int requiredAmount) {
        this.item = item;
        this.isCompleted = isCompleted;
        this.requiredAmount = requiredAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setCompleted() {
        isCompleted = true;
    }

    public Item getItem() {
        return item;
    }

    public String toString() {
        return item.toString() + " " + isCompleted + " " + requiredAmount;
    }

    public String niceFormattedString() {
        return requiredAmount + " x " + item.getName().getString();
    }
}
