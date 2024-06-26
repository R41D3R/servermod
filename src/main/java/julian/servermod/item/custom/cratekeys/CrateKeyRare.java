package julian.servermod.item.custom.cratekeys;

import julian.servermod.block.ModBlocks;
import julian.servermod.item.ModItems;
import julian.servermod.utils.AllCustomLootTables;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CrateKeyRare extends CrateKey {
    public CrateKeyRare(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        user.getStackInHand(hand).decrement(1);

        if (world.isClient()) {
            playBeginningSounds(user);
        }
        showCustomParticles(user);

        if (!world.isClient()) {
            List<ItemStack> list = AllCustomLootTables.CRATE_KEY_RARE_LOOT_TABLE.getRandomLoot(1);
            doWinningRoutine(user, new ArrayList<>(list));
        }

        setCooldownForKeys(user);
//        showFloatingItem1(new ItemStack(ModItems.CRATE_KEY_RARE));
//        showFloatingItem2(new ItemStack(ModBlocks.RARE_CHEST));

        if (world.isClient()) {
            playEndingSounds(user);
        }


        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
