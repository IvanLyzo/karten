package lyzo.karten.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lyzo.karten.handler.ProfileHandler;
import lyzo.karten.handler.ThemeHandler;

public class UserModel {

    private final ProfileHandler profileHandler;
    private final ThemeHandler themeHandler;

    public UserModel(ProfileHandler profileHandler, ThemeHandler themeHandler) {
        this.profileHandler = profileHandler;
        this.themeHandler = themeHandler;

        profileHandler.createProfileFile(username.get());
        themeHandler.createThemeFile(activeTheme.get());
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

    private final StringProperty activeTheme = new SimpleStringProperty("custom");

    public StringProperty getActiveTheme() {
        return activeTheme;
    }

    public void setActiveTheme(String name) {
        activeTheme.set(name);
        // TODO: add persistence
    }
}
