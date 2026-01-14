package lyzo.karten.feature.play;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.feature.play.minigame.rowing.RowingController;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.Controller;

public class PlayController implements Controller {

    private final AppModel appModel;

    // region container for view
    private final ObjectProperty<Region> view = new SimpleObjectProperty<>();

    public PlayController(AppModel appModel) {
        this.appModel = appModel;

        view.set(new MinigameSelectionViewBuilder(this::rowingGameAction).build());
    }

    @Override
    public Region buildView() {
        PlayViewBuilder viewBuilder = new PlayViewBuilder(view);

        // return it
        return viewBuilder.build();
    }

    private void rowingGameAction(MouseEvent event) {
        view.set(new RowingController().buildView());
    }
}
