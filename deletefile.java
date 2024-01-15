import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class DeleteOldFiles {

    public static void main(String[] args) {
        // Specify the directory to clean
        String directoryPath = "/path/to/your/directory";

        // Calculate the cutoff date for files to be deleted
        LocalDate cutoffDate = LocalDate.now().minusDays(30);
        Instant cutoffInstant = cutoffDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        try {
            Files.walk(Paths.get(directoryPath))
                    .filter(Files::isRegularFile)  // Only consider regular files
                    .filter(path -> Files.getLastModifiedTime(path).toInstant().isBefore(cutoffInstant))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Deleted file: " + path);
                        } catch (Exception e) {
                            System.err.println("Error deleting file: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.err.println("Error walking the directory: " + e.getMessage());
        }
    }
}
