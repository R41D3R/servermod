package julian.servermod.utils.playerdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import julian.servermod.ServerMod;
import julian.servermod.datagen.ModItemTagProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class WalletInventory {
    private NbtList data;
    public Map<Item, Integer> stacks = new HashMap<>();

    public WalletInventory(NbtList list) {
        this.data = list;
        for (NbtElement element : list) {
            NbtCompound compound = (NbtCompound) element;
            Identifier id = new Identifier(compound.getString("id"));
            Item item = Registries.ITEM.get(id);
            int count = compound.getInt("Count");
            stacks.put(item, count);
        }
    }

    private void updateData() {
        ServerMod.LOGGER.info("before updating data" + data.toString());
        for (int i = 0; i < data.size(); i++) {
            NbtCompound nbtCompound = data.getCompound(i);
            Item item = Registries.ITEM.get(Identifier.tryParse(nbtCompound.getString("id")));
            nbtCompound.putInt("Count", stacks.get(item));
        }
        ServerMod.LOGGER.info("after updating data" + data.toString());

    }

    public NbtList toNbtList() {
        updateData();
        return data;
    }


}
