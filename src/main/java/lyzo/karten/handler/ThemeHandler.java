package lyzo.karten.handler;

import lyzo.karten.io.disk.FileAccess;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ThemeHandler {

    public enum PROPERTIES {

        WHITE_BASE("-karten-pure-white", "#FDFDFD"),
        WHITE_TWO("-karten-off-white", "#FFFFF4"),
        BLACK("-karten-black", "#2F2F2F"),

        BLUE_MAIN("-karten-blue", "#A9C9FF"),
        BLUE_SHADOW("-karten-blue-shadow", "#70A5FF"),

        GREEN_MAIN("-karten-green", "#C6E5A6"),
        GREEN_SHADOW("-karten-green-shadow", "#70AE32"),

        YELLOW_MAIN("-karten-yellow", "#FFEBA3"),
        YELLOW_SHADOW("-karten-yellow-shadow", "#FFD747"),

        RED_MAIN("-karten-red", "#F7B4A8"),
        RED_SHADOW("-karten-red-shadow", "#EE5E44"),

        PURPLE_MAIN("-karten-purple", "#D7C2F0"),
        PURPLE_SHADOW("-karten-purple-shadow", "#B38CE3");

        public final String uid;
        public final String defaultValue;

        PROPERTIES(String uid, String defaultValue) {
            this.uid = uid;
            this.defaultValue = defaultValue;
        }
    }

    private final FileAccess fileAccess;
    private final Consumer<String> addStylesheetAction;

    private final Path themeDir;
    private String activeTheme;

    public ThemeHandler(FileAccess fileAccess, Consumer<String> addStylesheetAction) {
        this.fileAccess = fileAccess;
        this.addStylesheetAction = addStylesheetAction;

        themeDir = fileAccess.getAppDataPath().resolve("themes");
    }

    public void createThemeFile(String name) {
        activeTheme = name + ".css";

        fileAccess.writeFile(themeDir, activeTheme, initThemeFile());
        System.out.println(fileAccess.readFile(themeDir, activeTheme));
        addStylesheetAction.accept(themeDir.resolve(activeTheme).toUri().toString());
    }

    private String[] initThemeFile() {
        List<String> lines = new ArrayList<>();

        lines.add(".root {");

        for (PROPERTIES value : PROPERTIES.values()) {
            lines.add("\t" + value.uid + ":" + value.defaultValue + ";");
        }

        lines.add("}");

        return lines.toArray(new String[0]);
    }

    public void setProperty(PROPERTIES property, String value) {
        List<String> lines = fileAccess.readFile(themeDir, activeTheme);

        lines.replaceAll(s -> {
            if (s.contains(property.uid)) {
                return "\t" + property.uid + ":" + value + ";";
            }
            return s;
        });

        fileAccess.writeFile(themeDir, activeTheme, lines.toArray(new String[0]));
    }
}
