package lyzo.karten.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppDataPath {

    public static Path createAppDir() {
        String os = System.getProperty("os.name").toLowerCase();

        Path baseDir;
        if (os.contains("win")) {
            String appData = System.getenv("APPDATA");
            baseDir = Paths.get(appData != null ? appData : System.getProperty("user.home"));
        } else if (os.contains("mac")) {
            baseDir = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        } else {
            String xdg = System.getenv("XDG_DATA_HOME");
            if (xdg != null) {
                baseDir = Paths.get(xdg);
            } else {
                baseDir = Paths.get(System.getProperty("user.home"), ".local", "share");
            }
        }

        Path dir = baseDir.resolve("karten");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dir;
    }
}
