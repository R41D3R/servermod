package julian.servermod.badgertasks;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PossibleTasks {
    private final static List<BadgerTask> TASKS = List.of(
            new BadgerTask(Items.DIAMOND, 1, 3),
            new BadgerTask(Items.EMERALD, 2, 6),
            new BadgerTask(Items.GOLD_INGOT, 5, 13),
            new BadgerTask(Items.IRON_INGOT, 5, 13),
            new BadgerTask(Items.REDSTONE, 5, 13),
            new BadgerTask(Items.LAPIS_LAZULI, 5, 13),
            new BadgerTask(Items.COAL, 8, 17),
            new BadgerTask(Items.QUARTZ, 5, 13),
            new BadgerTask(Items.GLOWSTONE_DUST, 4, 9)
    );

    public static List<BadgerTask> getFiveDifferentRandomTasks() {
        List<BadgerTask> tasks = new ArrayList<>();
        List<BadgerTask> copy = new ArrayList<>(TASKS);
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * copy.size());
            tasks.add(copy.remove(index));
        }
        return tasks;
    }

    public static class BadgerTask {
        private final Item item;
        private final int lowerBound;
        private final int upperBound;

        public BadgerTask(Item item, int lowerBound, int upperBound) {
            this.item = item;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        public int getRandomAmount() {
            return new Random().nextInt(lowerBound, upperBound + 1);
        }

        public Item getItem() {
            return item;
        }

        public String getItemKey() {
            return item.getTranslationKey();
        }

        public int getLowerBound() {
            return lowerBound;
        }

        public int getUpperBound() {
            return upperBound;
        }

        public String toString() {
            return item.getTranslationKey() + " " + lowerBound + " " + upperBound;
        }
    }
}


