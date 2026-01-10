package lyzo.karten.feature.editor;

import javafx.scene.layout.Region;
import lyzo.karten.utility.interfaces.ViewBuilder;
import lyzo.karten.utility.ui.KControls;

public class EditorViewBuilder implements ViewBuilder {

    private final String name;

    public EditorViewBuilder(String name) {
        this.name = name;
    }

    @Override
    public Region build() {
        return KControls.KLabel("", name);
    }
}
