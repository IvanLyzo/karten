package lyzo.karten.feature.profile;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import lyzo.karten.disk.file.ResourceAccess;
import lyzo.karten.utility.structures.ViewBuilder;
import lyzo.karten.utility.ui.KControls;
import lyzo.karten.utility.ui.KRegions;

public class ProfileViewBuilder implements ViewBuilder {

    private final Runnable addMoneyAction;

    public ProfileViewBuilder(Runnable addMoneyAction) {
        this.addMoneyAction = addMoneyAction;
    }

    @Override
    public Region build() {
        ImageView imageView = new ImageView(ResourceAccess.RED_BOAT_GRAPHIC);
        Button addMoney = KControls.KButton("green-button", KControls.KLabel("heading", "add money"), addMoneyAction);

        HBox boatContainer = KRegions.KHorizontalBox("div", Pos.CENTER_LEFT, 20, imageView, addMoney);

        return boatContainer;
    }
}
