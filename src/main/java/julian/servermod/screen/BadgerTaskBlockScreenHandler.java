package julian.servermod.screen;

import julian.servermod.badgertasks.ActiveBadgerTask;
import julian.servermod.badgertasks.PossibleTasks;
import julian.servermod.block.entity.BadgerTaskBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class BadgerTaskBlockScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final BadgerTaskBlockEntity blockEntity;
    public PlayerEntity player;
    public List<ActiveBadgerTask> tasks;
    public List<ItemStack> rewards;
    public int streak = 0;

    public BadgerTaskBlockScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(6));
    }

    public BadgerTaskBlockScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity, ArrayPropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.BADGER_TASK_BLOCK_SCREEN_HANDLER, syncId);
        this.player = playerInventory.player;
        checkSize(((Inventory) blockEntity), 6);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((BadgerTaskBlockEntity) blockEntity);


        // Input Slot
        this.addSlot(new Slot(this.inventory, 0, 80, 89 + 1 - 62-5));

        // Task Slots
        int current_x = 44;
        int current_y = 125+1-62-5;
        for (int i = 1; i < 6; i++) {
            this.addSlot(new BadgerTaskItemSlot(this.inventory, i, current_x, current_y));
            current_x += 18;
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            slot.onTakeItem(player, newStack);
        }

        return newStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);

        if (this.inventory.getStack(0) != ItemStack.EMPTY) {
            player.giveItemStack(this.inventory.getStack(0));
            this.inventory.removeStack(0);
        }
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (156 - 29 - 2 - 18 - 20+4) + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, (214 - 29 - 20-20+4)));
        }
    }

    public class BadgerTaskItemSlot extends Slot {
        public BadgerTaskItemSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false;
        }
    }
}
