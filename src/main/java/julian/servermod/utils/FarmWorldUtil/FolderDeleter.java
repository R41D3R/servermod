package julian.servermod.utils.FarmWorldUtil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class FolderDeleter {
    public static void deleteFolder(String folderPath) {
        Path path = Path.of(folderPath);
        if (Files.exists(path)) {
            try {
                Files.walk(path)
                        .sorted((p1, p2) -> -p1.compareTo(p2))
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("Folder deleted successfully: " + folderPath);
            } catch (IOException e) {
                System.err.println("Error deleting folder: " + e.getMessage());
            }
        } else {
            System.out.println("Folder does not exist: " + folderPath);
        }
    }

    public static void main(String[] args) {
        String folderToDelete = "/path/to/your/folder";
        deleteFolder(folderToDelete);
    }
}