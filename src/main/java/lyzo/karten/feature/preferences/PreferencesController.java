package lyzo.karten.feature.preferences;

import javafx.scene.layout.Region;
import lyzo.karten.model.UserModel;
import lyzo.karten.utility.structures.Controller;

public class PreferencesController implements Controller {

    private final UserModel userModel;

    public PreferencesController(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Region buildView() {
        return new PreferencesViewBuilder().build();
    }
}
