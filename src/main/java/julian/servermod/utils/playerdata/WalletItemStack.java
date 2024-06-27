package julian.servermod.utils.playerdata;

import java.util.List;
import java.util.Objects;

import julian.servermod.ServerMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class WalletItemStack implements WalletItemStackInterface {

    private ItemStack itemStack;
    private long count;
    public static final WalletItemStack EMPTY = new WalletItemStack(ItemStack.EMPTY);

    public WalletItemStack(ItemStack item) {
        this.itemStack = item.copy();
        this.count = item.getCount();
    }

    public WalletItemStack(ItemConvertible item) {
        this(item, 1);
    }

    public WalletItemStack(RegistryEntry<Item> entry) {
        this((ItemConvertible)entry.value(), 1);
    }

    public WalletItemStack(RegistryEntry<Item> itemEntry, long count) {
        this((ItemConvertible)itemEntry.value(), count);
    }

    public WalletItemStack(ItemConvertible item, long count) {
        this.itemStack = new ItemStack(item, 1);
        this.count = count;
    }

    private WalletItemStack(NbtCompound nbt) {
        this.itemStack = ItemStack.fromNbt(nbt);
        this.count = nbt.getLong("Count");
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY || this.itemStack.isEmpty() || this.count <= 0;
    }

    public WalletItemStack split(long amount) {
        long i = Math.min(amount, this.getItemCount());
        WalletItemStack itemStack = this.copyWithCount(i);
        this.decrement(i);
        return itemStack;
    }

    public static WalletItemStack fromVanillaItemStack(ItemStack itemStack) {
        return new WalletItemStack(itemStack);
    }

    public static WalletItemStack fromNbt(NbtCompound nbt) {
        try {
            return new WalletItemStack(nbt);
        } catch (RuntimeException runtimeException) {
            ServerMod.LOGGER.debug("Tried to load invalid item: {}", (Object) nbt, (Object) runtimeException);
            return EMPTY;
        }
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        this.itemStack.writeNbt(nbt);
        nbt.putLong("Count", this.count);
        return nbt;
    }

    public long getMaxItemCount() {
        return Long.MAX_VALUE;
    }

    public boolean isStackable() {
        return this.itemStack.isStackable();
    }

    public WalletItemStack copy() {
        if (this.isEmpty()) return EMPTY;
        WalletItemStack copy = new WalletItemStack(this.itemStack);
        copy.setCount(this.count);
        return copy;
    }

    public WalletItemStack copyWithCount(long count) {
        if (this.isEmpty()) {
            return EMPTY;
        }
        WalletItemStack itemStack = this.copy();
        itemStack.setCount(count);
        return itemStack;
    }

    public static boolean areEqual(WalletItemStack left, WalletItemStack right) {
        if (left == right) return true;
        if (left.getItemCount() != right.getItemCount()) return false;
        return ItemStack.canCombine(left.itemStack, right.itemStack);
    }

    public static boolean areItemsEqual(WalletItemStack left, WalletItemStack right) {
        return left.itemStack.isOf(right.itemStack.getItem());
    }

    public static boolean canCombine(WalletItemStack stack, WalletItemStack otherStack) {
        return ItemStack.canCombine(stack.itemStack, otherStack.itemStack);
    }

    public boolean hasNbt() {
        return this.itemStack.hasNbt();
    }

    public NbtCompound getNbt() {
        return this.itemStack.getNbt();
    }

    public void setNbt(NbtCompound nbt) {
        this.itemStack.setNbt(nbt);
    }

    public int getCount() {
        return (int) Math.min(getItemCount(), 64);
    }

    public long getItemCount() {
        return this.isEmpty() ? 0 : this.count;
    }

    public String getItemCountShort() {
        List<String> numAbbrv = List.of("", "K","M","B","T","Qa","Qi");
        for (int i = 0; i < numAbbrv.size(); i++) {
            double c = Math.floor(this.count / Math.pow(1000L, i));
            if (c < 1000) return ((int) c) + numAbbrv.get(i);
        }
        return null;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void increment(long amount) {
        this.setCount(this.getItemCount() + amount);
    }

    public void decrement(long amount) {
        this.increment(-amount);
    }

    public ItemStack toItemStack() {
        int count = (int) Math.min(this.getItemCount(), 64);
        ItemStack stack = this.itemStack.copy();
        stack.setCount(count);
        return stack;
    }

    // Delegate methods to itemStack
    public Item getItem() {
        return this.itemStack.getItem();
    }

    public boolean isOf(Item item) {
        return this.itemStack.isOf(item);
    }

    public boolean isDamageable() {
        return this.itemStack.isDamageable();
    }

    public boolean isDamaged() {
        return this.itemStack.isDamaged();
    }

    public int getDamage() {
        return this.itemStack.getDamage();
    }

    public void setDamage(int damage) {
        this.itemStack.setDamage(damage);
    }

    public int getMaxDamage() {
        return this.itemStack.getMaxDamage();
    }

    public boolean isItemBarVisible() {
        return this.itemStack.isItemBarVisible();
    }

    public int getItemBarStep() {
        return this.itemStack.getItemBarStep();
    }

    public int getItemBarColor() {
        return this.itemStack.getItemBarColor();
    }

    public boolean isFood() {
        return this.itemStack.isFood();
    }

    // Add any other methods you need to delegate to itemStack
}