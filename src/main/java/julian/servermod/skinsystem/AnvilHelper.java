package julian.servermod.skinsystem;

import com.mojang.authlib.GameProfile;
import julian.servermod.ServerMod;
import julian.servermod.mixin.AnvilScreenHandlerAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

import java.util.Optional;

public class AnvilHelper {


    public static boolean canApplySkin(PlayerEntity player, DefaultedList<Slot> slots, final String playerName) {
        ItemStack leftStack = slots.get(0).getStack();
        ItemStack rightStack = slots.get(1).getStack();

        ServerMod.LOGGER.info("Checking if player can apply skin");
        if (!isDesign(rightStack) || !designMatchesItemType(rightStack, leftStack) || !(player instanceof final ServerPlayerEntity serverPlayer)) return false;

        ServerMod.LOGGER.info("left stack is " + leftStack.getItem().toString());
        ServerMod.LOGGER.info("right stack is " + rightStack.getItem().toString());

        var handler = serverPlayer.currentScreenHandler;

        ItemStack resultStack = getItemStackWithDesign(leftStack, rightStack);
        if(handler instanceof AnvilScreenHandler anvilHandler && runScreenUpdate(anvilHandler, resultStack)) {
            // anvilHandler.updateResult();
        }
        ServerMod.LOGGER.info("Player can apply skin");

        return true;

    }

    private static boolean isDesign(ItemStack itemStack) {
        if (itemStack.hasNbt()) {
            if (itemStack.getItem().toString() == "design") {
                ServerMod.LOGGER.info("ItemStack is a Design");
                return true;
            }
        }
        ServerMod.LOGGER.info("ItemStack is not a Design");
        return false;
    }

    private static boolean designMatchesItemType(ItemStack design, ItemStack item) {
        if (design.hasNbt() && item.hasNbt()) {
            NbtCompound designNBT = design.getNbt();
            String designID = designNBT.getCompound("Design").getString("Id");
            String forItemType = designID.split(":")[1].split("_")[0];
            String inputItemID = item.getItem().toString() ;

            if (inputItemID.contains(forItemType)) {
                ServerMod.LOGGER.info("Design matches item type");
                return true;
            }
        }
        ServerMod.LOGGER.info("Design does not match item type");
        return false;
    }

    private static ItemStack getItemStackWithDesign(ItemStack item, ItemStack design) {
        NbtCompound designTag = design.getNbt();

        // add default style tag to NBT Compound
        // designTag.putString("Style", "default");

        ItemStack copyOfInput = item.copy();
        copyOfInput.getNbt().put("Design", designTag.getCompound("Design"));
        copyOfInput.getNbt().getCompound("Design").putString("Style", "default");
        ServerMod.LOGGER.info("Copied ItemStack with Design");

        return copyOfInput;
    }

    private static boolean runScreenUpdate(AnvilScreenHandler anvilScreenHandler, ItemStack resultStack) {
        var slots = anvilScreenHandler.slots;

        ((AnvilScreenHandlerAccessor) anvilScreenHandler).aph$getLevelCost().set(0);

        slots.get(2).setStack(resultStack);

        return false;
    }
}
