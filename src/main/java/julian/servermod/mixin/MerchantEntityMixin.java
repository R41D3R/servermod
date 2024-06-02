package julian.servermod.mixin;


import julian.servermod.ServerMod;
import julian.servermod.utils.IEntityDataSaver;
import julian.servermod.villagertrades.VillagerTradeManager;
import julian.servermod.villagertrades.VillagerTradesBackupData;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantEntity.class)
public class MerchantEntityMixin {

    @Inject(method = "trade", at = @At("HEAD"))
    public void trade(TradeOffer offer, CallbackInfo ci) {
        ServerMod.LOGGER.info("Trade event");
        VillagerTradesBackupData.doTradeInBackupData((IEntityDataSaver) this, offer);
        VillagerTradeManager.useTrade(((MerchantEntity) (Object) this).getCustomer().getUuid(), offer);
        VillagerTradeManager.getTradeDataHandler().close();
    }
}
