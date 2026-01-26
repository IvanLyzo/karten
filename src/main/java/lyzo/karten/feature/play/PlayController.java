package lyzo.karten.feature.play;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Region;
import lyzo.karten.feature.play.minigame.rowing.RowingController;
import lyzo.karten.feature.play.minigame.rowing.RowingGameState;
import lyzo.karten.feature.play.minigame.rowing.RowingLobbyViewBuilder;
import lyzo.karten.feature.play.minigame.rowing.RowingResultViewBuilder;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.structures.Controller;
import lyzo.karten.utility.structures.minigame.GameState;
import lyzo.karten.utility.structures.minigame.MinigameController;

public class PlayController implements Controller {

    private final AppModel appModel;

    private final Runnable homeAction;

    // minigame properties
    private final ObjectProperty<MinigameController> minigameController = new SimpleObjectProperty<>();
    private final ObjectProperty<GameState> gameState = new SimpleObjectProperty<>();

    // region container for view
    private final ObjectProperty<Region> view = new SimpleObjectProperty<>();

    public PlayController(AppModel appModel, Runnable homeAction) {
        this.appModel = appModel;
        this.homeAction = homeAction;

        view.set(new MinigameSelectionViewBuilder(this::rowingGameAction).build());

        minigameController.addListener((_, _, newV) -> {
            if (newV == null) {
                view.set(new MinigameSelectionViewBuilder(this::rowingGameAction).build());
            } else {
                view.set(newV.buildView());
            }
        });
    }

    @Override
    public Region buildView() {
        PlayViewBuilder viewBuilder = new PlayViewBuilder(view);

        // return it
        return viewBuilder.build();
    }

    private void rowingGameAction() {
        Runnable playEvent = () -> minigameController.set(new RowingController(appModel, (RowingGameState) gameState.get(), this::winCondition));

        view.set(new RowingLobbyViewBuilder(gameState, playEvent).build());
    }

    private void winCondition(boolean won) {
        view.set(new RowingResultViewBuilder(won).build());
    }
}
