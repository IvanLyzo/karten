package lyzo.karten.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lyzo.karten.handler.ProfileHandler;

public class UserModel {

    public enum PROPERTY {

        BALANCE("BALANCE");

        public final String uid;

        PROPERTY(String uid) {
            this.uid = uid;
        }
    }

    private final ProfileHandler profileHandler;

    public UserModel(ProfileHandler profileHandler) {
        this.profileHandler = profileHandler;

        profileHandler.createProfileFile("default");
    }

    private final StringProperty username = new SimpleStringProperty("default");

    public StringProperty getUsername() {
        return username;
    }

    private final IntegerProperty balance = new SimpleIntegerProperty(0);

    public IntegerProperty getBalance() {
        return balance;
    }

    public void changeBalance(int d) {
        balance.set(balance.get() + d);
        profileHandler.setProperty(PROPERTY.BALANCE, balance.get());
    }
}
