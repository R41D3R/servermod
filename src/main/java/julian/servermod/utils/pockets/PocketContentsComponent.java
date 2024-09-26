package julian.servermod.utils.pockets;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import julian.servermod.ModDataComponents;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PocketContentsComponent implements TooltipData {
    public static final PocketContentsComponent DEFAULT = new PocketContentsComponent(List.of());
    public static final Codec<PocketContentsComponent> CODEC = ItemStack.CODEC.listOf().xmap(PocketContentsComponent::new, component -> component.stacks);
    public static final PacketCodec<RegistryByteBuf, PocketContentsComponent> PACKET_CODEC = ItemStack.PACKET_CODEC
            .collect(PacketCodecs.toList())
            .xmap(PocketContentsComponent::new, component -> component.stacks);
    private static final Fraction NESTED_BUNDLE_OCCUPANCY = Fraction.getFraction(1, 16);
    private static final int ADD_TO_NEW_SLOT = -1;
    public List<ItemStack> stacks;
    public final Fraction occupancy;

    PocketContentsComponent(List<ItemStack> stacks, Fraction occupancy) {
        this.stacks = stacks;
        this.occupancy = occupancy;
    }

    public PocketContentsComponent(List<ItemStack> stacks) {
        this(stacks, calculateOccupancy(stacks));
    }

    private static Fraction calculateOccupancy(List<ItemStack> stacks) {
        Fraction fraction = Fraction.ZERO;

        for (ItemStack itemStack : stacks) {
            fraction = fraction.add(getOccupancy(itemStack).multiplyBy(Fraction.getFraction(itemStack.getCount(), 1)));
        }

        return fraction;
    }

    static Fraction getOccupancy(ItemStack stack) {
        PocketContentsComponent bundleContentsComponent = stack.get(ModDataComponents.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null) {
            Fraction originalOccupancy = NESTED_BUNDLE_OCCUPANCY.add(bundleContentsComponent.getOccupancy());
            return originalOccupancy;//originalOccupancy.multiplyBy(Fraction.getFraction(64, PocketUtil.getIncreasedCapacity(stack)));
        } else {
            List<BeehiveBlockEntity.BeeData> list = stack.getOrDefault(DataComponentTypes.BEES, List.of());
            Fraction originalOccupancy = !list.isEmpty() ? Fraction.ONE : Fraction.getFraction(1, stack.getMaxCount());
            return originalOccupancy; //originalOccupancy.multiplyBy(Fraction.getFraction(64, PocketUtil.getIncreasedCapacity(stack)));
        }
    }

    public ItemStack get(int index) {
        return (ItemStack)this.stacks.get(index);
    }

    public Stream<ItemStack> stream() {
        return this.stacks.stream().map(ItemStack::copy);
    }

    public Iterable<ItemStack> iterate() {
        return this.stacks;
    }

    public Iterable<ItemStack> iterateCopy() {
        return Lists.<ItemStack, ItemStack>transform(this.stacks, ItemStack::copy);
    }

    public int size() {
        return this.stacks.size();
    }

    public Fraction getOccupancy() {
        return this.occupancy;
    }

    public boolean isEmpty() {
        return this.stacks.isEmpty();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else {
            return !(o instanceof PocketContentsComponent bundleContentsComponent)
                    ? false
                    : this.occupancy.equals(bundleContentsComponent.occupancy) && ItemStack.stacksEqual(this.stacks, bundleContentsComponent.stacks);
        }
    }

    public int hashCode() {
        return ItemStack.listHashCode(this.stacks);
    }

    public String toString() {
        return "BundleContents" + this.stacks;
    }

    public static class Builder {
        private final List<ItemStack> stacks;
        private Fraction occupancy;

        public Builder(PocketContentsComponent base) {
            this.stacks = new ArrayList(base.stacks);
            this.occupancy = base.occupancy;
        }

        public PocketContentsComponent.Builder clear() {
            this.stacks.clear();
            this.occupancy = Fraction.ZERO;
            return this;
        }

        private int addInternal(ItemStack stack) {
            if (!stack.isStackable()) {
                return -1;
            } else {
                for (int i = 0; i < this.stacks.size(); i++) {
                    if (ItemStack.areItemsAndComponentsEqual((ItemStack)this.stacks.get(i), stack)) {
                        return i;
                    }
                }

                return -1;
            }
        }

        private int getMaxAllowed(ItemStack stack, ItemStack bundleStack) {
            Fraction fraction = Fraction.ONE.subtract(this.occupancy);
            int originalMax = Math.max(fraction.divideBy(PocketContentsComponent.getOccupancy(stack)).intValue(), 0);
            return originalMax; //Math.min(INCREASED_CAPACITY, Math.max(originalMax, stack.getCount()));
        }

        public int add(ItemStack stack, ItemStack bundleStack) {
//            int INCREASED_CAPACITY = PocketUtil.getIncreasedCapacity(bundleStack);
//            ItemStack stackToAdd = stack;
//            if (stack.getCount() > 64 && stack.getCount() <= INCREASED_CAPACITY) {
//                stackToAdd = stack.copy();
//                stackToAdd.setCount(INCREASED_CAPACITY);
//            }
            if (!stack.isEmpty() && stack.getItem().canBeNested()) {
                int i = Math.min(stack.getCount(), this.getMaxAllowed(stack, bundleStack));
                if (i == 0) {
                    return 0;
                } else {
                    this.occupancy = this.occupancy.add(PocketContentsComponent.getOccupancy(stack).multiplyBy(Fraction.getFraction(i, 1)));
                    int j = this.addInternal(stack);
                    if (j != -1) {
                        ItemStack itemStack = (ItemStack)this.stacks.remove(j);
                        ItemStack itemStack2 = itemStack.copyWithCount(itemStack.getCount() + i);
                        stack.decrement(i);
                        this.stacks.add(0, itemStack2);
                    } else {
                        this.stacks.add(0, stack.split(i));
                    }

                    return i;
                }
            } else {
                return 0;
            }
        }

        public int add(Slot slot, PlayerEntity player, ItemStack bundleStack) {
            ItemStack itemStack = slot.getStack();
            int i = this.getMaxAllowed(itemStack, bundleStack);
            return this.add(slot.takeStackRange(itemStack.getCount(), i, player), bundleStack);
        }

        @Nullable
        public ItemStack removeFirst() {
            if (this.stacks.isEmpty()) {
                return null;
            } else {
                ItemStack itemStack = ((ItemStack)this.stacks.remove(0)).copy();
                this.occupancy = this.occupancy.subtract(PocketContentsComponent.getOccupancy(itemStack).multiplyBy(Fraction.getFraction(itemStack.getCount(), 1)));
                return itemStack;
            }
        }

        public Fraction getOccupancy() {
            return this.occupancy;
        }

        public PocketContentsComponent build() {
            return new PocketContentsComponent(List.copyOf(this.stacks), this.occupancy);
        }
    }
}
