package lyzo.karten.feature.play.minigame.rowing;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.interfaces.minigame.MinigameController;

public class RowingController implements MinigameController {

    private final AppModel appModel;
    private final RowingGameState gameState;

    private boolean continueGame = true;

    private final DoubleProperty canvasWidth = new SimpleDoubleProperty(0);
    private final DoubleProperty canvasHeight = new SimpleDoubleProperty(0);

    public RowingController(AppModel appModel, RowingGameState gameState) {
        this.appModel = appModel;
        this.gameState = gameState;
    }

    @Override
    public Region buildView() {
        RowingViewBuilder viewBuilder = new RowingViewBuilder(gameState, canvasWidth, canvasHeight,
                this::initGame, this::updateGame);

        return viewBuilder.build();
    }

    private void initGame() {
        initPlayerPositions();
    }

    private void initPlayerPositions() {
        Point2D[] positions = new Point2D[gameState.playerCount];

        double wStep = canvasWidth.get() / gameState.playerCount;
        double height = canvasHeight.get() - RowingGameState.Player.HEIGHT - 50;

        for (int i = 0; i < gameState.playerCount; i++) {
            double leftBound = wStep * i + (wStep - RowingGameState.Player.WIDTH) / 2;

            positions[i] = new Point2D(leftBound, height);
        }

        gameState.initPlayerPositions(positions);
    }

    private boolean updateGame(double delta) {
        // move players this frame
        double playerOffset = -gameState.user.speed * delta;
        gameState.players.forEach(player -> movePlayer(player, delta, playerOffset));

        return continueGame;
    }

    private void movePlayer(RowingGameState.Player player, double delta, double playerOffset) {
        // move player
        double dy = -player.speed * delta;
        player.move(dy - playerOffset);

        // check if reached the end
        if (player.metersRowed >= gameState.courseLength) {
            continueGame = false;
        }
    }
}
