package julian.servermod.utils.pockets;

import julian.servermod.ServerMod;
import julian.servermod.mixin.AnvilScreenHandlerAccessor;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

public class AnvilHelper {

    public static boolean canApplyPocket(PlayerEntity player, DefaultedList<Slot> slots, final String playerName) {
        ItemStack leftStack = slots.get(0).getStack();
        ItemStack rightStack = slots.get(1).getStack();
        ServerMod.LOGGER.info("Checking if player can apply bundle to armor item");
        ServerMod.LOGGER.info("left stack is " + leftStack.getItem().toString());
        ServerMod.LOGGER.info("right stack is " + rightStack.getItem().toString());

        if (!PocketUtil.canHavePockets(leftStack) || !PocketUtil.canHaveMorePockets(leftStack)
                || !isEmptyBundle(rightStack)
                || !(player instanceof final ServerPlayerEntity serverPlayer)) return false;

        var handler = serverPlayer.currentScreenHandler;

        ItemStack resultStack = PocketUtil.addPocket(leftStack);
        if(handler instanceof AnvilScreenHandler anvilHandler && runScreenUpdate(anvilHandler, resultStack)) {
            anvilHandler.updateResult();
        }

        return true;
    }

    private static boolean isEmptyBundle(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BundleItem) {
            BundleContentsComponent bundleContentsComponent = itemStack.get(DataComponentTypes.BUNDLE_CONTENTS);
            return bundleContentsComponent.isEmpty();
        }
        return false;
    }

    private static boolean runScreenUpdate(AnvilScreenHandler anvilScreenHandler, ItemStack resultStack) {
        var slots = anvilScreenHandler.slots;

        ((AnvilScreenHandlerAccessor) anvilScreenHandler).aph$getLevelCost().set(10);

        slots.get(2).setStack(resultStack);

        return false;
    }
}
