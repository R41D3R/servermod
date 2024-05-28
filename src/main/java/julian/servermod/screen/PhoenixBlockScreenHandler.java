package julian.servermod.screen;

import julian.servermod.block.entity.PhoenixBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class PhoenixBlockScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final PhoenixBlockEntity blockEntity;

    public PhoenixBlockScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(28));
    }

    public PhoenixBlockScreenHandler(int syncId, PlayerInventory playerInventory,
                                     BlockEntity blockEntity, ArrayPropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.PHOENIX_BLOCK_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 28);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((PhoenixBlockEntity) blockEntity);
        
        // Output Slot
        this.addSlot(new Slot(this.inventory, 27, 80, (124-29 - 20)){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                int count = 0;
                for (int i = 0; i < 26; i++) {
                    if (PhoenixBlockScreenHandler.this.inventory.getStack(i).getItem() instanceof EnchantedBookItem) {
                        PhoenixBlockScreenHandler.this.inventory.setStack(i, ItemStack.EMPTY);
                        count++;
                    }

                }
                if (count > 0) {
                    player.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
                }

            }
        });
        
        // Input Slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                this.addSlot(new Slot(this.inventory, index, 8 + (j*18), (52 - 29 - 2 - 18) + (i*18)));
            }
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
            } else if (!this.insertItem(originalStack, 1, this.inventory.size(), false)) {
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
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, (156 - 29 - 2 - 18) + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, (214 - 29 - 20)));
        }
    }
}
