package lyzo.karten.utility.interfaces;

import javafx.scene.layout.Region;
import javafx.util.Builder;

public interface ViewBuilder extends Builder<Region> {

    @Override
    Region build();
}
