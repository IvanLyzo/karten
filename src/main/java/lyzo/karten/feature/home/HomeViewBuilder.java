package lyzo.karten.feature.home;

import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;

public class HomeViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        return KControls.KLabel("", "Nothing to see here! (Home view)");
    }
}
