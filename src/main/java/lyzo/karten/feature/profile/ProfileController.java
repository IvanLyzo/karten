package lyzo.karten.feature.profile;

import javafx.scene.layout.Region;
import lyzo.karten.model.UserModel;
import lyzo.karten.utility.structures.Controller;

public class ProfileController implements Controller {

    private final UserModel userModel;

    public ProfileController(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Region buildView() {
        return new ProfileViewBuilder(this::addMoneyAction).build();
    }

    public void addMoneyAction() {
        userModel.addBalance(10);
    }
}
