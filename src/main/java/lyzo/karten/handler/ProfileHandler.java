package lyzo.karten.handler;

import lyzo.karten.io.disk.FileAccess;

import java.nio.file.Path;
import java.util.List;

public class ProfileHandler {

    public enum PROPERTIES {

        BALANCE("BALANCE");

        public final String uid;

        PROPERTIES(String uid) {
            this.uid = uid;
        }
    }

    private final FileAccess fileAccess;

    private final Path profileDir;
    private String activeProfile;

    public ProfileHandler(FileAccess fileAccess) {
        this.fileAccess = fileAccess;
        profileDir = fileAccess.getAppDataPath().resolve("profiles");
    }

    public void createProfileFile(String name) {
        activeProfile = name + ".profile";

        fileAccess.writeFile(profileDir, activeProfile, initProfileFile());
    }

    private String[] initProfileFile() {
        String[] lines = new String[PROPERTIES.values().length];

        for (PROPERTIES value : PROPERTIES.values()) {
            lines[value.ordinal()] = "[" + value.uid + "]:";
        }

        return lines;
    }

    public void setProperty(PROPERTIES property, int value) {
        List<String> lines = fileAccess.readFile(profileDir, activeProfile);

        lines.replaceAll(s -> {
            if (s.contains(property.uid)) {
                return "[" + property.uid + "]:" + value;
            }
            return s;
        });

        fileAccess.writeFile(profileDir, activeProfile, lines.toArray(new String[0]));
    }
}
