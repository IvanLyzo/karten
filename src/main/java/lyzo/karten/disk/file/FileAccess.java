package lyzo.karten.disk.file;

import lyzo.karten.model.UserModel;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileAccess {

    public final Path dirPath;
    public Path activeFilepath;

    public FileAccess(Path appDataPath) {
        dirPath = appDataPath.resolve("profiles");
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }
    }

    public void createProfile(String username) {
        String filename = username + ".txt";

        try {
            activeFilepath = dirPath.resolve(filename);

            List<String> lines = new ArrayList<>();
            lines.add("[" + UserModel.PROPERTY.BALANCE.uid + "]:");

            Files.write(activeFilepath, lines);
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("File already exists");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }
    }

    public String getString(UserModel.PROPERTY property) {
        try {
            // Read all lines into a List
            List<String> lines = Files.readAllLines(activeFilepath);

            Optional<String> optional = lines.stream().filter(s -> s.contains(property.uid)).findFirst();
            if (optional.isPresent()) {
              String[] line = optional.get().split(":");

              return line[line.length - 1];
            }
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }
        return "";
    }

    public int getInt(UserModel.PROPERTY property) {
        try {
            // Read all lines into a List
            List<String> lines = Files.readAllLines(activeFilepath);

            Optional<String> optional = lines.stream().filter(s -> s.contains(property.uid)).findFirst();
            if (optional.isPresent()) {
                String[] line = optional.get().split(":");

                return Integer.parseInt(line[line.length - 1]);
            }
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }
        return -1;
    }

    public <T> void setProperty(UserModel.PROPERTY property, T value) {
        try {
            // Read all lines into a List
            List<String> lines = Files.readAllLines(activeFilepath);

            String formattedString = "[" + property.uid + "]:" + value;

            Optional<String> optional = lines.stream().filter(s -> s.contains(property.uid)).findFirst();
            optional.ifPresentOrElse(s -> lines.set(lines.indexOf(s), formattedString), () -> lines.add(formattedString));

            Files.write(activeFilepath, lines);
        } catch (IOException e) {
            throw new RuntimeException("An I/O exception occurred: " + e.getMessage());
        }
    }
}