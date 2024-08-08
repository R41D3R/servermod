package julian.servermod.utils;


import julian.servermod.ServerMod;
import julian.servermod.utils.AllCustomLootTables.ItemStackAbstract;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomLootTable {
    private final float COMMON_LOOT_WEIGHT;
    private final float RARE_LOOT_WEIGHT;
    private final float EPIC_LOOT_WEIGHT;
    private final float LEGENDARY_LOOT_WEIGHT;

    private final List<ItemStackAbstract> COMMON_LOOT;
    private final List<ItemStackAbstract> RARE_LOOT;
    private final List<ItemStackAbstract> EPIC_LOOT;
    private final List<ItemStackAbstract> LEGENDARY_LOOT;

    public CustomLootTable(List<ItemStackAbstract> commonLoot, List<ItemStackAbstract> rareLoot,
                           List<ItemStackAbstract> epicLoot, List<ItemStackAbstract> legendaryLoot,
                           float commonWeight, float rareWeight,
                           float epicWeight, float legendaryWeight) {
        COMMON_LOOT = commonLoot;
        RARE_LOOT = rareLoot;
        EPIC_LOOT = epicLoot;
        LEGENDARY_LOOT = legendaryLoot;
        COMMON_LOOT_WEIGHT = commonWeight;
        RARE_LOOT_WEIGHT = rareWeight;
        EPIC_LOOT_WEIGHT = epicWeight;
        LEGENDARY_LOOT_WEIGHT = legendaryWeight;
    }

    public ItemStack getRandomLoot(RegistryWrapper.WrapperLookup registries) {
        List<ItemStackAbstract> lootList = getLootList();
        ServerMod.LOGGER.info("Loot List: " + lootList);
        ItemStackAbstract loot = lootList.get(new Random().nextInt(lootList.size()));
        if (loot.nbt instanceof NbtCompound) return ItemStack.fromNbtOrEmpty(registries, loot.nbt);
        else if (loot.item != null) return new ItemStack(loot.item, loot.count);
        else return new ItemStack(loot.block, loot.count);
    }

    public List<ItemStack> getRandomLoot(RegistryWrapper.WrapperLookup registries, int count) {
        List<ItemStack> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(getRandomLoot(registries).copy());
        }
        ServerMod.LOGGER.info("Result: " + result);
        return result;
    }

    public List<ItemStackAbstract> getLootList() {
        WeightedRandomBag<List<ItemStackAbstract>> bag = new WeightedRandomBag<>();
        bag.addEntry(new ArrayList<>(COMMON_LOOT), COMMON_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(RARE_LOOT), RARE_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(EPIC_LOOT), EPIC_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(LEGENDARY_LOOT), LEGENDARY_LOOT_WEIGHT);

        List<ItemStackAbstract> randomLootList = bag.getRandom();
        return new ArrayList<>(randomLootList);
    }
}
