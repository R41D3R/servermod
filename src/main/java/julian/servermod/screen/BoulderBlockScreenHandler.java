package julian.servermod.screen;


import julian.servermod.block.entity.BoulderBlockEntity;
import julian.servermod.block.entity.PhoenixBlockEntity;
import julian.servermod.item.ModItems;
import julian.servermod.screen.util.InventoryUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.village.Merchant;
import net.minecraft.village.MerchantInventory;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOfferList;

public class BoulderBlockScreenHandler extends ScreenHandler {
    public final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    private final PlayerInventory playerInv;
    public final BoulderBlockEntity blockEntity;

    /** Cost of item repair in rubies */
    private static final int cost_per_item = 25;
    public static final Item currencyItem = ModItems.RUBY;

    public BoulderBlockScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(10));
    }

    public BoulderBlockScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity, ArrayPropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.BOULDER_BLOCK_SCREEN_HANDLER, syncId);
        this.playerInv = playerInventory;
        checkSize(((Inventory) blockEntity), 10);
        this.inventory = ((Inventory) blockEntity);
        this.inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((BoulderBlockEntity) blockEntity);


        int m;
        int l;
        // Repairsmith inventory
        for (m = 0; m < 2; ++m) {
            for (l = 0; l < 5; ++l) {
                this.addSlot(new RepairSlot(this.inventory, l + m * 5, 8 + l * 18, 29 + m * 18));
            }
        }
        // The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        // The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }

    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

        }

        return newStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        // this.dropInventory(player, this.inventory);
    }

    public int getRepairCost() {
        int count = 0;
        // count damaged items
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.getStack(i).getDamage() > 0) {
                count++;
            }
        }

        return cost_per_item * count;
    }


    /**
     * Performs repair action on all damaged items in repairsmith inventory.
     * Must be called from server to persist changes.
     */
    public void repairAll() {
        // Check if player can afford
        if (!InventoryUtil.canAfford(this.playerInv, this.getRepairCost(), currencyItem))
            return;

        // Reset all damage
        for (int i = 0; i < 10; i++) {
            this.blockEntity.getStack(i).setDamage(0);
        }

        // decrease currencyItem count in player inventory
        int remaining = this.getRepairCost();

        for (final var stack : this.playerInv.offHand) {
            if (remaining <= 0)
                break;
            if (stack.getItem() == currencyItem) {
                int remove = Math.min(stack.getCount(), remaining);
                stack.decrement(remove);
                remaining -= remove;
            }
        }

        for (final var stack : this.playerInv.main) {
            if (remaining <= 0)
                break;
            if (stack.getItem() == currencyItem) {
                int remove = Math.min(stack.getCount(), remaining);
                stack.decrement(remove);
                remaining -= remove;
            }
        }

        // Play sound
        this.playerInv.player.playSound(SoundEvents.BLOCK_ANVIL_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);


    }

    /** Slot class for repair screen */
    private class RepairSlot extends Slot {

        public RepairSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if (stack.isDamageable()) return (stack.getDamage() > 0);
            return false;
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }

    }

}