package lyzo.karten.feature.library;

import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

public class LibraryController implements Controller {

    @Override
    public Region buildView(AppModel appModel) {
        LibraryViewBuilder viewBuilder = new LibraryViewBuilder();

        return viewBuilder.build();
    }
}
