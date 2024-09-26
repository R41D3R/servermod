package julian.servermod.mixin;


import julian.servermod.ModDataComponents;
import julian.servermod.ServerMod;
import julian.servermod.sound.PocketSound;
import julian.servermod.utils.pockets.BundleRefer;
import julian.servermod.utils.pockets.PocketContentsComponent;
import julian.servermod.utils.pockets.PocketTooltipData;
import julian.servermod.utils.pockets.PocketUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin extends Item {

    public ArmorItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", cancellable = true, at = @At("HEAD"))
    private void disableEquip(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!PocketUtil.wearsEmptyPockets(user)) {
            ItemStack stackInHand = user.getStackInHand(hand);
            Equipment equipment = Equipment.fromStack(stackInHand);
            if (equipment.getSlotType() == EquipmentSlot.LEGS) {
                cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
            }

        }

    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
            if (bundleContentsComponent == null) {
                return false;
            } else {
                if (!PocketUtil.wearsPockets(stack, player)) {
                    return false;
                }
                ItemStack itemStack = slot.getStack();
                PocketContentsComponent.Builder builder = new PocketContentsComponent.Builder(bundleContentsComponent);
                boolean pocketable = PocketUtil.hasPockets(stack) && PocketUtil.isAllowedInPockets(stack);

                if (itemStack.isEmpty()) {
                    PocketSound.playRemoveOneSound(player);
                    ItemStack itemStack2 = builder.removeFirst();
                    if (itemStack2 != null) {
                        ItemStack itemStack3 = slot.insertStack(itemStack2);
                        builder.add(itemStack3, stack);
                    }
                } else if (itemStack.getItem().canBeNested() && pocketable) {
                    int i = builder.add(slot, player, stack);
                    if (i > 0) {
                        PocketSound.playInsertSound(player);
                    }
                }

                stack.set(ModDataComponents.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
            if (bundleContentsComponent == null) {
                return false;
            } else {
                if (!PocketUtil.wearsPockets(stack, player)) {
                    return false;
                }
                PocketContentsComponent.Builder builder = new PocketContentsComponent.Builder(bundleContentsComponent);
                boolean pocketable = PocketUtil.hasPockets(stack) && PocketUtil.isAllowedInPockets(otherStack);
                if (otherStack.isEmpty()) {
                    ItemStack itemStack = builder.removeFirst();
                    if (itemStack != null) {
                        PocketSound.playRemoveOneSound(player);
                        cursorStackReference.set(itemStack);
                    }
                } else if (pocketable){
                    int i = builder.add(otherStack, stack);
                    if (i > 0) {
                        PocketSound.playInsertSound(player);
                    }
                }

                stack.set(ModDataComponents.BUNDLE_CONTENTS, builder.build());
                return true;
            }
        } else {
            return false;
        }
    }

//    @Override
//    public boolean isItemBarVisible(ItemStack stack) {
//        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
//        return bundleContentsComponent.getOccupancy().compareTo(Fraction.ZERO) > 0;
//    }
//
//    @Override
//    public int getItemBarStep(ItemStack stack) {
//        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
//        return Math.min(1 + MathHelper.multiplyFraction(bundleContentsComponent.getOccupancy(), 12), 13);
//    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        if(!PocketUtil.hasPockets(stack)) {
            return Optional.empty();
        }
        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
        BundleContentsComponent bundleContentsComponent2 = new BundleContentsComponent(bundleContentsComponent.stacks);
        return !stack.contains(DataComponentTypes.HIDE_TOOLTIP) && !stack.contains(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP)
                ? Optional.ofNullable(bundleContentsComponent2).map(BundleTooltipData::new)
                : Optional.empty();
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type)  {
        if(!PocketUtil.hasPockets(stack)) {
//            if (PocketUtil.canHavePockets(stack)) {
//                int pocketCount = PocketUtil.getPocketCount(stack);
//                tooltip.add(Text.of("Pockets " + pocketCount + " / " + PocketUtil.MAX_POCKETS).copy()
//                        .formatted(Formatting.GRAY)
//                );
//            }
            return;
        }
        PocketContentsComponent bundleContentsComponent = stack.getOrDefault(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
        // ServerMod.LOGGER.info("bundleContentsComponent: " + bundleContentsComponent);
        //ServerMod.LOGGER.info("bundle refer: " + BundleRefer.scroll);
        if (bundleContentsComponent != null) {
            int capacity = PocketUtil.INCREASED_CAPACITY;
            int i = MathHelper.multiplyFraction(bundleContentsComponent.getOccupancy(), capacity);
            tooltip.add(Text.translatable("item.minecraft.bundle.fullness", i, capacity).formatted(Formatting.GRAY));
            // ServerMod.LOGGER.info("bundleContentsComponent.getOccupancy(): " + bundleContentsComponent.getOccupancy());
        }
//        int pocketCount = PocketUtil.getPocketCount(stack);
//        tooltip.add(Text.of("Pockets " + pocketCount + " / " + PocketUtil.MAX_POCKETS).copy()
//                .formatted(Formatting.GRAY)
//        );
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        PocketSound.playDropContentsSound(entity);
        PocketContentsComponent bundleContentsComponent = entity.getStack().get(ModDataComponents.BUNDLE_CONTENTS);
        if (bundleContentsComponent != null) {
            entity.getStack().set(ModDataComponents.BUNDLE_CONTENTS, PocketContentsComponent.DEFAULT);
            ItemUsage.spawnItemContents(entity, bundleContentsComponent.iterateCopy());
        };
    }
}
