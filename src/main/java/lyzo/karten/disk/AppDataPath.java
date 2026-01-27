package lyzo.karten.disk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppDataPath {

    // function to determine app data path based on operating system
    public static Path createAppDir() {
        // find operating system
        String os = System.getProperty("os.name").toLowerCase();

        Path baseDir;

        // operating system is windows
        if (os.contains("win")) {
            String appData = System.getenv("APPDATA");
            baseDir = Paths.get(appData != null ? appData : System.getProperty("user.home"));
        }

        // operating system is macOS
        else if (os.contains("mac")) {
            baseDir = Paths.get(System.getProperty("user.home"), "Library", "Application Support");
        }

        // fallback to linux (find SOME industry-standard directory and use first found)
        else {
            String xdg = System.getenv("XDG_DATA_HOME");
            if (xdg != null) {
                baseDir = Paths.get(xdg);
            } else {
                baseDir = Paths.get(System.getProperty("user.home"), ".local", "share");
            }
        }

        // create a "karten" folder at app data directory
        Path dir = baseDir.resolve("karten");
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // return directory to "karten" folder
        return dir;
    }
}
