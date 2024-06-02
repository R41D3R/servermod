package julian.servermod.mixin;

import julian.servermod.ServerMod;
import julian.servermod.villagertrades.VillagerTradeManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(Merchant.class)
public interface MerchantMixin {

    @Shadow
    public abstract @Nullable PlayerEntity getCustomer();

    @ModifyArg(
            method = "sendOffers(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/text/Text;I)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;sendTradeOffers(ILnet/minecraft/village/TradeOfferList;IIZZ)V"
            ),
            index = 1
    )
    private TradeOfferList modifyOffers(TradeOfferList offers) {
        ServerMod.LOGGER.info("Modifying offers for player: " + this.getCustomer().getUuid());
        ServerMod.LOGGER.info("Offers: " + offers);
        TradeOfferList offers1 = VillagerTradeManager.modifyOffersThatArePresent(this.getCustomer().getUuid(), offers);
        VillagerTradeManager.getTradeDataHandler().close();
        return offers1;
    }
}
