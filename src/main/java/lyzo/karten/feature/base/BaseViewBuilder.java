package lyzo.karten.feature.base;

import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.ViewBuilder;

public class BaseViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        return new Label("Hello, World!");
    }
}
