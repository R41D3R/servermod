package julian.servermod.utils.FarmWorldUtil;

import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

public class YamlSeedUpdater {
    public static void updateSeed(String filePath) {
        // Generate a random 10-digit seed
        Random random = new Random();
        long newSeed = 1000000000L + (long) (random.nextDouble() * 9000000000L); // 10-digit random number

        try {
            // Read the file into a list of strings
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Modify the seed line
            List<String> modifiedLines = lines.stream()
                    .map(line -> line.startsWith("seed:") ? "seed: " + newSeed : line)
                    .collect(Collectors.toList());

            // Write the modified content back to the file
            Files.write(Paths.get(filePath), modifiedLines);

            System.out.println("Seed updated successfully. New seed: " + newSeed);

        } catch (IOException e) {
            System.err.println("Error reading or writing the file: " + e.getMessage());
        }
    }
}
