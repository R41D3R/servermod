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
