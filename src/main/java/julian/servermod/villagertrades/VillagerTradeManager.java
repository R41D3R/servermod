package julian.servermod.villagertrades;

import julian.servermod.ServerMod;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.UUID;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class VillagerTradeManager {
    private static final TradeDataHandler tradeDataHandler = new TradeDataHandler();

    public static TradeDataHandler getTradeDataHandler() {
        return tradeDataHandler;
    }

    public static TradeOfferList modifyOffersThatArePresent(UUID player, TradeOfferList offers) {
        for (TradeOffer offer : offers) {
            String tradeKey = generateTradeKey(offer);
            int uses = tradeDataHandler.getTradeUses(player, tradeKey);
            int targetUses = max(uses, offer.getUses());
            ServerMod.LOGGER.info("tradeKey: " + tradeKey);
            ServerMod.LOGGER.info(("current_uses: " + uses + " target_uses: " + targetUses + " offer_uses: " + offer.getUses()));
            while (offer.getUses() < targetUses) {
                offer.use();
                ServerMod.LOGGER.info("decrease offer: " + generateTradeKey(offer));
            }
        }
        return offers;
    }

    public static void useTrade(UUID playerId, TradeOffer offer) {
        String tradeKey = generateTradeKey(offer);
        tradeDataHandler.updateTrade(playerId, tradeKey, offer.getUses()+1);
    }


    public static String generateTradeKey(TradeOffer offer) {
        return offer.getOriginalFirstBuyItem().getTranslationKey() + ":" +
                (offer.getSecondBuyItem() != null ? offer.getSecondBuyItem().getTranslationKey() + ":" : "") +
                offer.getSellItem().getTranslationKey();
    }
}
