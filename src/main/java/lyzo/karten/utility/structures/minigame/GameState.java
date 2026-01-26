package lyzo.karten.utility.structures.minigame;

import javafx.beans.property.*;
import lyzo.karten.model.Card;

public abstract class GameState {

    public static final double FLASHCARD_COUNTDOWN = 5;

    public final BooleanProperty overlayOn = new SimpleBooleanProperty(false);
    public final DoubleProperty overlayCountdown = new SimpleDoubleProperty(FLASHCARD_COUNTDOWN);

    public final ObjectProperty<Card> activeCard = new SimpleObjectProperty<>();
}
