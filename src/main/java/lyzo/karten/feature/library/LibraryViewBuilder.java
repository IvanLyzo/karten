package lyzo.karten.feature.library;

import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;

public class LibraryViewBuilder implements ViewBuilder {

    @Override
    public Region build() {
        return KControls.KLabel("", "Nothing to see here! (Library view)");
    }
}
