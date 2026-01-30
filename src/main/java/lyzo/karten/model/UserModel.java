package lyzo.karten.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import lyzo.karten.handler.ProfileHandler;
import lyzo.karten.handler.ThemeHandler;

public class UserModel {

    private final ProfileHandler profileHandler;
    private final ThemeHandler themeHandler;

    public UserModel(ProfileHandler profileHandler, ThemeHandler themeHandler) {
        this.profileHandler = profileHandler;
        this.themeHandler = themeHandler;

        profileHandler.createProfileFile(username.get());

        themeHandler.createThemeFile(themeName.get());
        activeTheme.setAll(themeHandler.getThemeFile());
    }

    private final StringProperty username = new SimpleStringProperty("default");

    public StringProperty getUsername() {
        return username;
    }

    private final IntegerProperty balance = new SimpleIntegerProperty(0);

    public IntegerProperty getBalance() {
        return balance;
    }

    public void addBalance(int d) {
        balance.set(balance.get() + d);
        profileHandler.setProperty(ProfileHandler.PROPERTIES.BALANCE, balance.get());
    }

    private final StringProperty themeName = new SimpleStringProperty("custom");
    private final ObservableList<Pair<String, String>> activeTheme = FXCollections.observableArrayList();

    public StringProperty getThemeName() {
        return themeName;
    }

    public void setTheme(String name) {
        themeName.set(name);

        themeHandler.setThemeFile(themeName.get());
    }

    public ObservableList<Pair<String, String>> getActiveTheme() {
        return activeTheme;
    }

    public void setThemeProperty(ThemeHandler.PROPERTIES property, String value) {
        themeHandler.setProperty(property, value);
    }
}
