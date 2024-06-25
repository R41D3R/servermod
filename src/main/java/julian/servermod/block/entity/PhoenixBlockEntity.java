package julian.servermod.block.entity;

import julian.servermod.item.ModItems;
import julian.servermod.screen.PhoenixBlockScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PhoenixBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(28, ItemStack.EMPTY);

    public static final int INPUT_SLOTS = 26;
    public static final int OUTPUT_SLOT = 27;




    public PhoenixBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PHOENIX_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Phoenix Block");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PhoenixBlockScreenHandler(syncId, playerInventory, this, null);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        this.setPossibleOutput();
    }

    private int countInputBooks() {
        int count = 0;
        for (int i = 0; i <= INPUT_SLOTS ; i++) {
            Item inputItem = getStack(i).getItem();
            if (inputItem instanceof EnchantedBookItem) {
                count++;
            }
        }
        return count;
    }

    public void removeBooksAfterTaking() {
        for (int i = 0; i < INPUT_SLOTS ; i++) {
            Item inputItem = getStack(i).getItem();
            if (inputItem instanceof EnchantedBookItem) {
                this.removeStack(i, 1);
            }
        }
    }

    private void setPossibleOutput() {
        int count = countInputBooks();
        ItemStack result = new ItemStack(ModItems.ASHES_O_ENCHANTMENT);

        if (count > 0) {
            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), count));
        }
        else {
            this.setStack(OUTPUT_SLOT, ItemStack.EMPTY);
        }
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }
}
