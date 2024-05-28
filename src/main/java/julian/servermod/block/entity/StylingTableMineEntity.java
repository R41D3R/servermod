package julian.servermod.block.entity;

import julian.servermod.item.ModItems;
import julian.servermod.screen.PhoenixBlockScreenHandler;
import julian.servermod.screen.StylingTableMineScreenHandler;
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

public class StylingTableMineEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int DESIGN_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;




    public StylingTableMineEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STYLING_TABLE_MINE_ENTITY, pos, state);
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
        return Text.literal("Styling Table");
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

        return new StylingTableMineScreenHandler(syncId, playerInventory, this, null);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            return;
        }

        this.setPossibleOutput();
    }

    private void setPossibleOutput() {
        ItemStack designStack = getStack(DESIGN_SLOT);
        ItemStack inputStack = getStack(INPUT_SLOT);
        // String designName = designItem.;
        if (designStack.hasNbt() && getStack(INPUT_SLOT).hasNbt()) {
            NbtCompound designNBT = designStack.getNbt();

            if (designStack.getItem().toString() == "design") {
                String designID = designNBT.getCompound("Design").getString("Id");
                String forItemType = designID.split(":")[1].split("_")[0];
                String inputItemID = inputStack.getItem().toString() ;

                if (inputItemID.contains(forItemType)) {
                    this.setStack(OUTPUT_SLOT, this.buildOutputStack(inputStack, designStack));
                }


            }
        }
        else {
            setStack(OUTPUT_SLOT, ItemStack.EMPTY);
        }
//        int count = countInputBooks();
//        ItemStack result = new ItemStack(ModItems.ASHES_O_ENCHANTMENT);
//
//        if (count > 0) {
//            this.setStack(OUTPUT_SLOT, new ItemStack(result.getItem(), count));
//        }
//        else {
//            this.setStack(OUTPUT_SLOT, ItemStack.EMPTY);
//        }
    }

    private ItemStack buildOutputStack(ItemStack input, ItemStack design) {
        NbtCompound designTag = design.getNbt();

        // add default style tag to NBT Compound
        // designTag.putString("Style", "default");

        ItemStack copyOfInput = input.copy();
        copyOfInput.getNbt().put("Design", designTag.getCompound("Design"));
        copyOfInput.getNbt().getCompound("Design").putString("Style", "default");

        return copyOfInput;
    }

//    private int countInputBooks() {
//        int count = 0;
//        for (int i = 0; i <= INPUT_SLOTS ; i++) {
//            Item inputItem = getStack(i).getItem();
//            if (inputItem instanceof EnchantedBookItem) {
//                count++;
//            }
//        }
//        return count;
//    }
//
//    public void removeBooksAfterTaking() {
//        for (int i = 0; i < INPUT_SLOTS ; i++) {
//            Item inputItem = getStack(i).getItem();
//            if (inputItem instanceof EnchantedBookItem) {
//                this.removeStack(i, 1);
//            }
//        }
//    }



//    private boolean isOutputSlotEmptyOrReceivable() {
//        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
//    }
}
