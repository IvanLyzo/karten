package lyzo.karten.feature.themes;

import javafx.scene.layout.Region;
import lyzo.karten.model.UserModel;
import lyzo.karten.utility.structures.Controller;

public class ThemesController implements Controller {

    private final UserModel userModel;

    public ThemesController(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Region buildView() {
        return new ThemesViewBuilder(userModel.getActiveTheme(), userModel::setThemeProperty).build();
    }
}
