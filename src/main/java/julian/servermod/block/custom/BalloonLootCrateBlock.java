package julian.servermod.block.custom;

import com.mojang.serialization.MapCodec;
import julian.servermod.utils.AllCustomLootTables;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BalloonLootCrateBlock extends FallingBlock {
    public BalloonLootCrateBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return null;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {

        if (!EnchantmentHelper.hasAnyEnchantmentsWith(tool, EnchantmentEffectComponentTypes.BLOCK_EXPERIENCE)) {
            ServerWorld serverWorld = world.getServer().getWorld(world.getRegistryKey());
            List<ItemStack> randomLoot = AllCustomLootTables.URN_LOOT_TABLE.getRandomLoot(world.getRegistryManager(),6);
            for (ItemStack itemStack : randomLoot) {
                spawnItemStack(serverWorld, pos, itemStack);
            }
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);

    }

    public void spawnItemStack(ServerWorld serverWorld, BlockPos blockPos, ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
        itemEntity.setPickupDelay(20);
        serverWorld.spawnEntity(itemEntity);
    }
}
