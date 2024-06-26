package julian.servermod.item.custom.cratekeys;

import julian.servermod.ServerMod;
import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import julian.servermod.screen.CrateRewardScreen;
import julian.servermod.sound.ModSounds;
import julian.servermod.utils.AllCustomLootTables;
import julian.servermod.utils.CrateParticleAnimationSystem;
import julian.servermod.utils.CrateParticles;
import julian.servermod.utils.CustomLootTable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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

//                player.sendMessage(Text.literal("Congratulations!").copy()
//                                .formatted(Formatting.GREEN)
//                                .formatted(Formatting.BOLD),
//                        true);
//
//                player.sendMessage(Text.of("You have received:").copy()
//                        .formatted(Formatting.GREEN));
                for (ItemStack itemStack : rewards) {
//                    player.sendMessage(Text.of( " - " + itemStack.getCount() + " x " + itemStack.getName().getString()).copy()
//                            .formatted(Formatting.GREEN));

                    player.giveItemStack(itemStack.copy());
                }
                ItemStack reward = rewards.get(0).copy();
                ServerMod.LOGGER.info("Reward: " + reward.getName().getString());
                MinecraftClient.getInstance().execute(() ->
                        MinecraftClient.getInstance().setScreen(new CrateRewardScreen(reward, this))
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void playEndingSounds(PlayerEntity player) {
        new Thread(() -> {
            try {
                Thread.sleep(4500);
                player.playSound(ModSounds.CRATE_OPEN_MAGIC, 1f, 1f);
                Thread.sleep(500);
                player.playSound(ModSounds.CRATE_OPEN_CHIMES, 0.4f, 1f);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void showCustomParticles(PlayerEntity player) {
//        if (!player.getWorld().isClient()) {
//            CrateParticleAnimationSystem.startAnimation((ServerPlayerEntity)player, player.getSteppingPos().up(1));
//        }
    }


    public void playBeginningSounds(PlayerEntity player) {
        // player.playSound(ModSounds.BEGINNING_SONG, 1f, 1f);
        player.playSound(ModSounds.CRATE_OPEN, 1f, 1f);
        player.playSound(ModSounds.CRATE_BEGINNING_CHOIR, 0.5f, 1f);
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Right-click to open crate").copy().formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
