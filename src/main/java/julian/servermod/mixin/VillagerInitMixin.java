package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.utils.IEntityDataSaver;
import julian.servermod.villagertrades.VillagerTradeManager;
import julian.servermod.villagertrades.VillagerTradesBackupData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerInitMixin extends MerchantEntity {

    public VillagerInitMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;Lnet/minecraft/village/VillagerType;)V",
            at = @At(value = "TAIL"))
    private void inject(EntityType<? extends VillagerEntity> entityType, World world, VillagerType type, CallbackInfo ci) {
        this.goalSelector.add(2, new TemptGoal(this, .4D, Ingredient.ofItems(Items.EMERALD_BLOCK, Items.EMERALD_ORE,Items.DEEPSLATE_EMERALD_ORE), false));
    }

    @Inject(method = "beginTradeWith" , at = @At("HEAD"))
    public void loadOrSetupTradeBackup(CallbackInfo ci) {
        NbtCompound backUpData = VillagerTradesBackupData.getBackupTradeData((IEntityDataSaver) this);
        if (backUpData != null) {
            TradeOfferList backedUpTrades = new TradeOfferList(backUpData);

            if (this.offers == null) {
                return;
            }

            for (TradeOffer offer : this.offers) {
                if (!VillagerTradesBackupData.tradeIsInTradeOfferList(backedUpTrades, offer)) {
                    ServerMod.LOGGER.info("Trade not in backup data: " + VillagerTradeManager.generateTradeKey(offer));
                    backedUpTrades.add(offer);
                }
            }

            this.offers = backedUpTrades;
        }
        VillagerTradesBackupData.setBackupTradeData((IEntityDataSaver) this, this.offers.toNbt());
    }

}