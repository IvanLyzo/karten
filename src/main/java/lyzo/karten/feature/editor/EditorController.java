package lyzo.karten.feature.editor;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.model.Card;
import lyzo.karten.utility.interfaces.Controller;

public class EditorController implements Controller {

    private final AppModel appModel;

    public EditorController(AppModel appModel) {
        this.appModel = appModel;
    }

    @Override
    public Region buildView() {
        EditorViewBuilder viewBuilder = new EditorViewBuilder(appModel.getActiveDeck(), appModel.getDeckCards(), appModel.getActiveCard(), this::newDeckAction);

        return viewBuilder.build();
    }

    private void newDeckAction(MouseEvent mouseEvent) {
        Card card = appModel.addCard();

        appModel.setActiveCard(card);
    }
}
