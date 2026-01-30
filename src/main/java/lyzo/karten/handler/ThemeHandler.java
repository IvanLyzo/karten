package lyzo.karten.handler;

import javafx.util.Pair;
import lyzo.karten.io.disk.FileAccess;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ThemeHandler {

    public enum PROPERTIES {

        WHITE_BASE("-karten-pure-white", ":#", "FDFDFD"),
        WHITE_TWO("-karten-off-white", ":#", "FFFFF4"),
        BLACK("-karten-black", ":#", "2F2F2F"),

        BLUE_MAIN("-karten-blue", ":#", "A9C9FF"),
        BLUE_SHADOW("-karten-blue-shadow", ":#", "70A5FF"),

        GREEN_MAIN("-karten-green", ":#", "C6E5A6"),
        GREEN_SHADOW("-karten-green-shadow", ":#", "70AE32"),

        YELLOW_MAIN("-karten-yellow", ":#", "FFEBA3"),
        YELLOW_SHADOW("-karten-yellow-shadow", ":#", "FFD747"),

        RED_MAIN("-karten-red", ":#", "F7B4A8"),
        RED_SHADOW("-karten-red-shadow", ":#", "EE5E44"),

        PURPLE_MAIN("-karten-purple", ":#", "D7C2F0"),
        PURPLE_SHADOW("-karten-purple-shadow", ":#", "B38CE3");

        public final String uid;
        public final String separator;
        public final String defaultValue;

        PROPERTIES(String uid, String separator, String defaultValue) {
            this.uid = uid;
            this.separator = separator;
            this.defaultValue = defaultValue;
        }
    }

    private final FileAccess fileAccess;
    private final BiConsumer<String, String> addStylesheetAction;

    private final Path themeDir;
    private String activeTheme;

    public ThemeHandler(FileAccess fileAccess, BiConsumer<String, String> addStylesheetAction) {
        this.fileAccess = fileAccess;
        this.addStylesheetAction = addStylesheetAction;

        themeDir = fileAccess.getAppDataPath().resolve("themes");
    }

    public void createThemeFile(String name) {
        activeTheme = name + ".theme.css";

        if (!Files.exists(themeDir.resolve(activeTheme))) {
            fileAccess.writeFile(themeDir, activeTheme, initThemeFile());
        }
        addStylesheetAction.accept("", themeDir.resolve(activeTheme).toUri().toString());
    }

    private String[] initThemeFile() {
        List<String> lines = new ArrayList<>();

        lines.add(".root {");

        for (PROPERTIES value : PROPERTIES.values()) {
            lines.add("\t" + value.uid + value.separator + value.defaultValue + ";");
        }

        lines.add("}");

        return lines.toArray(new String[0]);
    }

    public List<Pair<String, String>> getThemeFile() {
        List<String> lines = fileAccess.readFile(themeDir, activeTheme);

        List<Pair<String, String>> dict = new ArrayList<>();
        for (int i = 1; i < lines.size() - 1; i++) {
            String line = lines.get(i);

            String key = line.split(":")[0].strip();
            String value = line.substring(line.indexOf("#") + 1, line.indexOf(";"));

            dict.add(new Pair<>(key, value));
        }

        return dict;
    }

    public void setThemeFile(String newTheme) {
        addStylesheetAction.accept(themeDir.resolve(activeTheme).toUri().toString(), themeDir.resolve(newTheme).toUri().toString());
        activeTheme = newTheme;
    }

    public String getProperty(PROPERTIES property) {
        List<String> lines = fileAccess.readFile(themeDir, activeTheme);

        Optional<String> line = lines.stream().filter(s -> s.contains(property.uid)).findFirst();

        return line.orElse("");
    }

    public void setProperty(PROPERTIES property, String value) {
        List<String> lines = fileAccess.readFile(themeDir, activeTheme);

        lines.replaceAll(s -> {
            if (s.contains(property.uid + property.separator)) {
                return "\t" + property.uid + property.separator + value + ";";
            }
            return s;
        });

        fileAccess.writeFile(themeDir, activeTheme, lines.toArray(new String[0]));

        addStylesheetAction.accept(themeDir.resolve(activeTheme).toUri().toString(), themeDir.resolve(activeTheme).toUri().toString());
    }
}
