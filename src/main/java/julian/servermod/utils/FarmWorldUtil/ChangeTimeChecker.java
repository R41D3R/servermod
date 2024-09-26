package julian.servermod.utils.FarmWorldUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ChangeTimeChecker {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static boolean checkAndUpdateJSONChangeTime(String jsonFilePath) {
        try {
            Path path = Paths.get(jsonFilePath);
            File file = new File(jsonFilePath);
            JsonObject jsonObject;

            if (file.exists()) {
                jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
            } else {
                // Create directories if they don't exist
                Files.createDirectories(path.getParent());

                jsonObject = new JsonObject();
                // Set initial time to 4 weeks ago
                ZonedDateTime fourWeeksAgo = ZonedDateTime.now(ZoneId.of("Europe/Berlin")).minusWeeks(4);
                jsonObject.addProperty("changeTime", fourWeeksAgo.format(formatter));
                // Write the new file
                try (FileWriter fileWriter = new FileWriter(file)) {
                    gson.toJson(jsonObject, fileWriter);
                }
                System.out.println("New file created with initial time set to 4 weeks ago: " + fourWeeksAgo);
            }

            ZonedDateTime changeTime;
            if (jsonObject.has("changeTime")) {
                String changeTimeStr = jsonObject.get("changeTime").getAsString();
                changeTime = ZonedDateTime.parse(changeTimeStr, formatter);
            } else {
                changeTime = ZonedDateTime.now(ZoneId.of("Europe/Berlin")).minusYears(1); // Default to a year ago
            }

            // Get the last Saturday 2 PM
            ZonedDateTime lastSaturday2PM = getLastSaturday2PM();

            if (changeTime.isBefore(lastSaturday2PM)) {
                // If change time is before last Saturday 2 PM, update it to now
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
                jsonObject.addProperty("changeTime", now.format(formatter));

                try (FileWriter fileWriter = new FileWriter(file)) {
                    gson.toJson(jsonObject, fileWriter);
                }

                System.out.println("JSON change time updated to: " + now);
                return true;
            } else {
                System.out.println("JSON change time is after last Saturday 2 PM. No update needed.");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static ZonedDateTime getLastSaturday2PM() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        ZonedDateTime lastSaturday = now.with(DayOfWeek.SATURDAY).minusWeeks(1);
        return lastSaturday.withHour(14).withMinute(0).withSecond(0).withNano(0);
    }
}
