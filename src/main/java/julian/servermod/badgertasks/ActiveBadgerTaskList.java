package julian.servermod.badgertasks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;

public class ActiveBadgerTaskList extends ArrayList<ActiveBadgerTask> {
    public ActiveBadgerTaskList() {
    }

    public ActiveBadgerTaskList(int size) {
        super(size);
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeCollection(this, (buf1, task) -> {
            buf1.writeItemStack(new ItemStack(task.getItem()));
            buf1.writeBoolean(task.getIsCompleted());
            buf1.writeInt(task.getRequiredAmount());
        });
    }

    public static ActiveBadgerTaskList fromPacket(PacketByteBuf buf) {
        return buf.readCollection(ActiveBadgerTaskList::new, buf1 -> {
            Item item = buf1.readItemStack().getItem();
            boolean isCompleted = buf1.readBoolean();
            int requiredAmount = buf1.readInt();
            return new ActiveBadgerTask(item, isCompleted, requiredAmount);
        });
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ActiveBadgerTask task : this) {
            sb.append(task.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean allCompleted() {
        for (ActiveBadgerTask task : this) {
            if (!task.getIsCompleted()) {
                return false;
            }
        }
        return true;
    }
}
