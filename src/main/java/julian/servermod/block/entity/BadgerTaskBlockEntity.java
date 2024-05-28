package julian.servermod.block.entity;

import julian.servermod.ServerMod;
import julian.servermod.badgertasks.ActiveBadgerTask;
import julian.servermod.badgertasks.ActiveBadgerTaskList;
import julian.servermod.badgertasks.BadgerTaskManager;
import julian.servermod.item.ModItems;
import julian.servermod.screen.BadgerTaskBlockScreenHandler;
import julian.servermod.screen.util.BadgerTaskClientNetworkUtil;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BadgerTaskBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private static final int INPUT_SLOT = 0;

    private PlayerEntity player = null;
    private ActiveBadgerTaskList tasks = null;


    public BadgerTaskBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BADGER_TASK_BLOCK_ENTITY, pos, state);
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
        return Text.literal("Badger Task Block");
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
        this.player = player;
        this.tasks = BadgerTaskManager.getActiveBadgerTasks(player.getUuid());
        ServerMod.LOGGER.info("Got tasks from DataStorage: " + tasks.toString() + tasks.size()) ;
        ServerMod.LOGGER.info("Create Menu for Badger Task Block");
        return new BadgerTaskBlockScreenHandler(syncId, playerInventory, this, null);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.player == null) {
            return;
        }
        if (world.isClient()) {
            // BadgerTaskClientNetworkUtil.askForBadgerTasksSync((BadgerTaskBlockScreenHandler) this.player.currentScreenHandler);
            return;
        }
        renderTaskSlots();
        checkInputSlot();
    }

    private void renderTaskSlots() {
        int slot = 1;
        for (ActiveBadgerTask task : tasks) {
            if (!task.getIsCompleted())
                this.setStack(slot, new ItemStack(task.getItem(), task.getRequiredAmount()));
            else this.setStack(slot, ItemStack.EMPTY);
            slot++;
        }
    }

    private void checkInputSlot() {


        for (ActiveBadgerTask task : tasks) {
            ItemStack inputStack = this.getStack(INPUT_SLOT);
            if (!task.getIsCompleted() && task.getItem() == inputStack.getItem() && task.getRequiredAmount() <= inputStack.getCount()) {
                BadgerTaskManager.completeBadgerTask(player.getUuid(), task.getItem());
                this.removeStack(INPUT_SLOT, task.getRequiredAmount());
                ServerMod.LOGGER.info("Remove stack" + task.getRequiredAmount());
                // inputStack.decrement(task.getRequiredAmount());

                task.setCompleted();
                ServerMod.LOGGER.info("Task Completed");

                if (BadgerTaskManager.checkIfAllTasksCompleted(player.getUuid())) {
                    ServerMod.LOGGER.info("All tasks completed");
                    player.sendMessage(Text.of("All tasks completed!").copy().formatted(Formatting.GREEN), true);
                    List<ItemStack> rewards = BadgerTaskManager.getRewardForPlayer(player.getUuid());
                    String rewardMessage = "You have received: \n";
                    for (ItemStack rewardStack : rewards) {
                        rewardMessage += rewardStack.getCount() + " x " + rewardStack.getItem().getName().getString();
                        // if rewardstack is not last item in list add \n to string
                        if (rewards.indexOf(rewardStack) != rewards.size() - 1) {
                            rewardMessage += "\n";
                        }
                        player.giveItemStack(rewardStack);

                    }
                    player.sendMessage(Text.of(rewardMessage).copy().formatted(Formatting.GREEN), false);
                }
            }
        }
    }
}



