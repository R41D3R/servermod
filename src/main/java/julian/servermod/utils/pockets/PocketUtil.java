package julian.servermod.utils.pockets;

import julian.servermod.ModDataComponents;
import julian.servermod.ServerMod;
import julian.servermod.item.ModItemTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class PocketUtil {

    public static final int INCREASED_CAPACITY = 384;
    public static final int MAX_POCKETS = 4;

    public static int getIncreasedCapacity(ItemStack stack) {
        int capacity = getPocketCount(stack) * 64;
        int maxCapacity = Math.max(capacity, 64);
        ServerMod.LOGGER.info("maxCapacity: " + maxCapacity);
        ServerMod.LOGGER.info("capacity: " + capacity);
        return maxCapacity;
    }

    public static boolean wearsPockets(ItemStack stack, PlayerEntity player) {
        return stack.equals(player.getInventory().getArmorStack(1));
    }

    public static boolean arePocketsEmpty(ItemStack stack) {
        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
        return bundleContentsComponent.isEmpty();
    }

    public static boolean wearsEmptyPockets(PlayerEntity player) {
        ItemStack stack = player.getInventory().getArmorStack(1);
        if (stack.isEmpty()) {
            return true;
        }
        if (hasPockets(stack)) {
            if (!arePocketsEmpty(stack)) {
                return false;
            }
        }
        return true;
    }

    public static int getPocketCount(ItemStack stack) {
        boolean hasPockets = stack.getOrDefault(ModDataComponents.HAS_POCKETS, false);
        //ServerMod.LOGGER.info("pocketCount: " + pocketCount);
        return hasPockets ? 1 : 0;
    }

    public static boolean canHaveMorePockets(ItemStack stack) {
        return getPocketCount(stack) + 1 < MAX_POCKETS;
    }

    public static ItemStack addPocket(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.set(ModDataComponents.HAS_POCKETS, true);
        return copy;
    }

    public static boolean isAllowedInPockets(ItemStack stack) {
        return !ModItemTags.POCKETS_BLACKLIST.contains(stack.getItem());
    }

    public static boolean hasPockets(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ModItemTags.HAS_POCKETS.contains(stack.getItem()) && getPocketCount(stack) > 0;
    }

    public static boolean canHavePockets(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem && ModItemTags.HAS_POCKETS.contains(stack.getItem());
    }

    public static int getMaxPocketSlots(ItemStack stack) {
        return 128;
    }

    public static void shiftBundle(ItemStack stack, int shift) {
        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
        int length = bundleContentsComponent.size();
        if (length == 0) {
            return;
        }
        shift = Math.floorMod(shift, length);
        ArrayList<ItemStack> mutableStacks = new ArrayList<>(bundleContentsComponent.stacks);

        for (int i = 0; i < shift; i++) {
            mutableStacks.add(mutableStacks.remove(0).copy());
        }

        // Create a new PocketContentsComponent with the modified list
        PocketContentsComponent.Builder builder = new PocketContentsComponent.Builder(new PocketContentsComponent(mutableStacks));
        stack.set(ModDataComponents.BUNDLE_CONTENTS, builder.build());
    }


//    public static int addToPockets(ItemStack pocketItem, ItemStack stack) {
//        if (!stack.isEmpty() && stack.getItem().canBeNested()) {
//            NbtCompound nbtCompound = pocketItem.getOrCreateNbt();
//            if (!nbtCompound.contains("Items")) {
//                nbtCompound.put("Items", new NbtList());
//            }
//
//            int i = getPocketOccupancy(pocketItem);
//            int j = getItemOccupancy(stack);
//            int k = Math.min(stack.getCount(), (64 - i) / j);
//            if (k == 0) {
//                return 0;
//            } else {
//                NbtList nbtList = nbtCompound.getList("Items", 10);
//                Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
//                if (optional.isPresent()) {
//                    NbtCompound nbtCompound2 = optional.get();
//                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
//                    itemStack.increment(k);
//                    itemStack.writeNbt(nbtCompound2);
//                    nbtList.remove(nbtCompound2);
//                    nbtList.add(0, nbtCompound2);
//                } else {
//                    ItemStack nbtCompound2 = stack.copy();
//                    nbtCompound2.setCount(k);
//                    NbtCompound itemStack = new NbtCompound();
//                    nbtCompound2.writeNbt(itemStack);
//                    nbtList.add(0, itemStack);
//                }
//
//                return k;
//            }
//        } else {
//            return 0;
//        }
//    }

//    public static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
//        if (stack.isOf(Items.BUNDLE)) {
//            return Optional.empty();
//        } else {
//            Stream<NbtElement> itemStream = items.stream();
//            return itemStream
//                .filter(elem -> elem instanceof NbtCompound)
//                .map(nbt -> (NbtCompound)nbt).filter(nbtCompound -> {
//                ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
//                return ItemStack.canCombine(itemStack, stack);
//            }).findFirst();
//        }
//    }

//    private static int getItemOccupancy(ItemStack stack) {
//        if(PocketUtil.hasPockets(stack)) {
//            return (64 / stack.getMaxCount()) + PocketUtil.getPocketOccupancy(stack);
//        }
//        if (stack.isOf(Items.BUNDLE)) {
//            return 4 + getPocketOccupancy(stack);
//        } else {
//            if ((stack.isOf(Items.BEEHIVE) || stack.isOf(Items.BEE_NEST)) && stack.hasNbt()) {
//                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
//                if (nbtCompound != null && !nbtCompound.getList("Bees", 10).isEmpty()) {
//                    return 64;
//                }
//            }
//
//            return 64 / stack.getMaxCount();
//        }
//    }

    public static int getPocketOccupancy(ItemStack stack) {
        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
        return bundleContentsComponent.getOccupancy().intValue();
    }

//    public static Optional<ItemStack> removeFirstStack(ItemStack stack) {
//        NbtCompound nbtCompound = stack.getOrCreateNbt();
//        if (!nbtCompound.contains("Items")) {
//            return Optional.empty();
//        } else {
//            NbtList nbtList = nbtCompound.getList("Items", 10);
//            if (nbtList.isEmpty()) {
//                return Optional.empty();
//            } else {
//                NbtCompound nbtCompound2 = nbtList.getCompound(0);
//                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
//                nbtList.remove(0);
//                if (nbtList.isEmpty()) {
//                    stack.removeSubNbt("Items");
//                }
//
//                return Optional.of(itemStack);
//            }
//        }
//    }
//
    public static boolean dropAllPocketedItems(ItemStack stack, PlayerEntity player) {
        PocketContentsComponent bundleContentsComponent = stack.get(ModDataComponents.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null && !bundleContentsComponent.isEmpty()) {
            stack.set(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
            if (player instanceof ServerPlayerEntity) {
                bundleContentsComponent.iterateCopy().forEach(stackx -> player.dropItem(stackx, true));
            }

            return true;
        } else {
            return false;
        }
    }
//
//    public static Stream<ItemStack> getPocketedStacks(ItemStack stack) {
//        NbtCompound nbtCompound = stack.getNbt();
//        if (nbtCompound == null) {
//            return Stream.empty();
//        } else {
//            NbtList nbtList = nbtCompound.getList("Items", 10);
//            Stream<NbtElement> var10000 = nbtList.stream();
//            Objects.requireNonNull(NbtCompound.class);
//            return var10000.map(elem -> (NbtCompound)elem).map(ItemStack::fromNbt);
//        }
//    }
}
