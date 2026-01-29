package lyzo.karten.handler;

import lyzo.karten.io.disk.FileAccess;

public class PreferencesHandler {

    private final FileAccess fileAccess;

    public PreferencesHandler(FileAccess fileAccess) {
        this.fileAccess = fileAccess;
    }
}
