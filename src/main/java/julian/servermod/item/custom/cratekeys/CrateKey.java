package julian.servermod.item.custom.cratekeys;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import julian.servermod.sound.ModSounds;
import julian.servermod.utils.AllCustomLootTables;
import julian.servermod.utils.CustomLootTable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CrateKey extends Item {


    public CrateKey(Settings settings) {
        super(settings);
    }



    public void doWinningRoutine(PlayerEntity player, List<ItemStack> rewards) {

        new Thread(() -> {
            try {
                Thread.sleep(4500);

                player.sendMessage(Text.literal("Congratulations!").copy()
                                .formatted(Formatting.GREEN)
                                .formatted(Formatting.BOLD),
                        true);

                player.sendMessage(Text.of("You have received:").copy()
                        .formatted(Formatting.GREEN));
                for (ItemStack itemStack : rewards) {
                    player.sendMessage(Text.of( " - " + itemStack.getCount() + " x " + itemStack.getName().getString()).copy()
                            .formatted(Formatting.GREEN));

                    player.giveItemStack(itemStack);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void playEndingSounds(PlayerEntity player) {
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                player.playSound(ModSounds.CRATE_OPEN_MAGIC, 1f, 1f);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void playBeginningSounds(PlayerEntity player) {
        player.playSound(ModSounds.BEGINNING_SONG, 1f, 1f);
        player.playSound(ModSounds.CRATE_OPEN, 1f, 1f);
    }


    public void showFloatingItem1(ItemStack itemStack) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.gameRenderer.showFloatingItem(itemStack);

    }

    public void showFloatingItem2(ItemStack itemStack) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);

                MinecraftClient client = MinecraftClient.getInstance();
                client.gameRenderer.showFloatingItem(itemStack);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setCooldownForKeys(PlayerEntity player) {
        player.getItemCooldownManager().set(ModItems.CRATE_KEY_LEGENDARY, 20 * 6);
        player.getItemCooldownManager().set(ModItems.CRATE_KEY_RARE, 20 * 6);
        player.getItemCooldownManager().set(ModItems.CRATE_KEY_BADGER, 20 * 6);
    }
}
