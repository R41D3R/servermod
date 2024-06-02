package julian.servermod.villagertrades;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import julian.servermod.ServerMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.TradeOffer;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TradeDataHandler implements AutoCloseable{

    private static final Logger LOGGER = ServerMod.LOGGER;
    private final Gson gson = new Gson();
    private static final File saveFile = new File("mods/servermod/player_trade_stock.json");
    private final Map<UUID, Map<String, Integer>> playerTradeStock;

    public TradeDataHandler() {
        try {
            playerTradeStock = new ConcurrentHashMap<>();
            saveFile.getParentFile().mkdirs();
            if (!saveFile.exists()) {

                saveTradeData();
            } else {
                loadTradeData();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load player trade stock data.", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        saveTradeData();
    }

//    public Map<UUID, Map<String, Integer>> getPlayerTradeStock() {
//        return playerTradeStock;
//    }

    public void saveTradeData() {
        try (FileWriter writer = new FileWriter(saveFile)) {
            gson.toJson(playerTradeStock, writer);
            LOGGER.info("Successfully saved player trade stock data.");
        } catch (IOException e) {
            LOGGER.error("Failed to save player trade stock data.", e);
        }
    }

    public void loadTradeData() throws IOException {
        if (saveFile.exists()) {
            try (FileReader reader = new FileReader(saveFile)) {
                Type type = new TypeToken<Map<UUID, Map<String, Integer>>>() {}.getType();
                Map<UUID, Map<String, Integer>> data = gson.fromJson(reader, type);
                if (data != null) {
                    playerTradeStock.putAll(data);
                    LOGGER.info("Successfully loaded player trade stock data.");
                }
            } catch (IOException | JsonSyntaxException e) {
                LOGGER.error("Failed to load player trade stock data.", e);
                throw new IOException(e);
            }
        } else {
            LOGGER.warn("Player trade stock data file not found.");
        }
    }

    public void restockTrades() {
        playerTradeStock.values().forEach(trades -> trades.replaceAll((key, value) -> 0));
        LOGGER.info("Restocked all trades for all players.");
    }

    public void updateTrade(UUID playerId, String tradeKey, int used) {
        playerTradeStock.putIfAbsent(playerId, new ConcurrentHashMap<>());
        Map<String, Integer> playerTrades = playerTradeStock.get(playerId);
        playerTrades.put(tradeKey, used);
        ServerMod.LOGGER.info("Updated trade for player " + playerId + " with key " + tradeKey + " to " + used);
    }

    public int getTradeUses(UUID playerId, String tradeKey) {
        playerTradeStock.putIfAbsent(playerId, new ConcurrentHashMap<>());
        Map<String, Integer> playerTrades = playerTradeStock.get(playerId);
        return playerTrades.getOrDefault(tradeKey, 0);
    }



}
