package lyzo.karten.io.disk;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {

    private final Path appDataPath;

    public FileAccess(Path appDataPath) {
        this.appDataPath = appDataPath;
    }

    public Path getAppDataPath() {
        return appDataPath;
    }

    public List<String> readFile(Path relPath, String filename) {
        Path filepath = appDataPath.resolve(relPath).resolve(filename);
        try {
            return Files.readAllLines(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFile(Path relPath, String filename, String... content) {
        Path fileDir = appDataPath.resolve(relPath);

        try {
            Files.createDirectories(fileDir);

            List<String> lines = new ArrayList<>(List.of(content));
            Files.write(fileDir.resolve(filename), lines);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage(), e);
        }
    }
}
