package lyzo.karten.io.disk;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {

    public enum FILE_TYPE {
        TXT(".txt"),
        CSS(".css");

        final String ext;

        FILE_TYPE(String ext) {
            this.ext = ext;
        }
    }

    private final Path appDataPath;

    public FileAccess(Path appDataPath) {
        this.appDataPath = appDataPath;
    }

    public List<String> readFile(Path relPath, String filename) {
        Path filepath = appDataPath.resolve(relPath).resolve(filename);
        try {
            return Files.readAllLines(filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path writeFile(Path relPath, String filename, String... initialContent) {
        Path fileDir = appDataPath.resolve(relPath);

        try {
            Files.createDirectories(fileDir);

            List<String> lines = new ArrayList<>(List.of(initialContent));
            Files.write(fileDir.resolve(filename), lines);
        } catch (FileAlreadyExistsException e) {
            return fileDir;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }

        return fileDir;
    }
}
