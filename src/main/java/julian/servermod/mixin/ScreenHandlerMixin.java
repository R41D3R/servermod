package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.utils.pockets.PocketUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo cir) {
        ServerMod.LOGGER.info("onSlotClick" + slotIndex);
        ScreenHandler screenHandler = (ScreenHandler) (Object) this;
        if (slotIndex >= 0 && slotIndex < screenHandler.slots.size()) {
            ItemStack clickedStack = screenHandler.slots.get(slotIndex).getStack();
            ItemStack cursorStack = player.currentScreenHandler.getCursorStack();
            ClickType clickType = button == 0 ? ClickType.LEFT : ClickType.RIGHT;
            if (isArmorSlot(slotIndex)) {
                if (cursorStack.isEmpty()) {

                    if (clickType == ClickType.LEFT && !PocketUtil.wearsEmptyPockets(player)) {
                        cir.cancel();
                    }
                    return;
                }
                if (isArmorItem(clickedStack) && !PocketUtil.wearsEmptyPockets(player)) {
                    if (clickType == ClickType.LEFT) {
                        cir.cancel();
                    }
                    return;
                } else if (isArmorItem(cursorStack) && !PocketUtil.wearsEmptyPockets(player)) {
                    if (clickType == ClickType.LEFT) {
                        cir.cancel();
                    }
                    return;
                }
            } else if (actionType == SlotActionType.QUICK_MOVE && isArmorItem(clickedStack) && !PocketUtil.wearsEmptyPockets(player)) {
                cir.cancel();
            }
        }
    }

    private boolean isArmorSlot(int slotIndex) {
        return slotIndex == 7;
    }

    private boolean isArmorItem(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ArmorItem;
    }
}
