package lyzo.karten.handler;

import lyzo.karten.io.disk.FileAccess;
import lyzo.karten.model.UserModel;

import java.nio.file.Path;
import java.util.List;

public class ProfileHandler {

    private final FileAccess fileAccess;

    private Path activeProfileDir;
    private String filename;

    public ProfileHandler(FileAccess fileAccess) {
        this.fileAccess = fileAccess;
    }

    public void createProfileFile(String name) {
        activeProfileDir = fileAccess.writeFile(Path.of("profiles"), name + ".profile", initProfileFile());
        filename = name + ".profile";
    }

    private String[] initProfileFile() {
        String[] lines = new String[UserModel.PROPERTY.values().length];

        for (UserModel.PROPERTY value : UserModel.PROPERTY.values()) {
            lines[value.ordinal()] = "[" + value.uid + "]:";
        }

        return lines;
    }

    public void setProperty(UserModel.PROPERTY property, int value) {
        List<String> lines = fileAccess.readFile(activeProfileDir, filename);

        lines.replaceAll(s -> {
            if (s.contains(property.uid)) {
                return "[" + property.uid + "]:" + value;
            }
            return s;
        });

        fileAccess.writeFile(activeProfileDir, filename, lines.toArray(new String[0]));
    }
}
