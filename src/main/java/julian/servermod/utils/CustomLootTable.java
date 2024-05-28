package julian.servermod.utils;


import julian.servermod.ServerMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomLootTable {
    private final float COMMON_LOOT_WEIGHT;
    private final float RARE_LOOT_WEIGHT;
    private final float EPIC_LOOT_WEIGHT;
    private final float LEGENDARY_LOOT_WEIGHT;

    private final List<ItemStack> COMMON_LOOT;
    private final List<ItemStack> RARE_LOOT;
    private final List<ItemStack> EPIC_LOOT;
    private final List<ItemStack> LEGENDARY_LOOT;

    public CustomLootTable(List<ItemStack> commonLoot, List<ItemStack> rareLoot,
                           List<ItemStack> epicLoot, List<ItemStack> legendaryLoot,
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

    public ItemStack getRandomLoot() {
        List<ItemStack> lootList = getLootList();
        ServerMod.LOGGER.info("Loot List: " + lootList);
        return lootList.get(new Random().nextInt(lootList.size()));
    }

    public List<ItemStack> getRandomLoot(int count) {
        List<ItemStack> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(getRandomLoot().copy());
        }
        ServerMod.LOGGER.info("Result: " + result);
        return result;
    }

    public List<ItemStack> getLootList() {
        WeightedRandomBag<List<ItemStack>> bag = new WeightedRandomBag<>();
        bag.addEntry(new ArrayList<>(COMMON_LOOT), COMMON_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(RARE_LOOT), RARE_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(EPIC_LOOT), EPIC_LOOT_WEIGHT);
        bag.addEntry(new ArrayList<>(LEGENDARY_LOOT), LEGENDARY_LOOT_WEIGHT);

        List<ItemStack> randomLootList = bag.getRandom();
        return new ArrayList<>(randomLootList);
    }
}
