package julian.servermod.badgertasks;

import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

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


    public String niceFormattedString() {
        return requiredAmount + " x " + item.getName().getString();
    }

    @Override
    public String toString() {
        return "ActiveBadgerTask{" +
                "item=" + item +
                ", isCompleted=" + isCompleted +
                ", requiredAmount=" + requiredAmount +
                '}';
    }
}
