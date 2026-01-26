package lyzo.karten.feature.play.minigame.rowing;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import lyzo.karten.model.AppModel;
import lyzo.karten.utility.structures.minigame.MinigameController;

import java.util.Objects;
import java.util.function.Consumer;

public class RowingController extends MinigameController {

    private final AppModel appModel;
    private final RowingGameState gameState;

    private final DoubleProperty canvasWidth = new SimpleDoubleProperty(0);
    private final DoubleProperty canvasHeight = new SimpleDoubleProperty(0);

    private final Consumer<Boolean> winCondition;

    private Runnable drawGame;
    private AnimationTimer gameLoop;

    private final StringProperty response = new SimpleStringProperty("");
    private final IntegerProperty index = new SimpleIntegerProperty(0);

    public RowingController(AppModel appModel, RowingGameState gameState,
                            Consumer<Boolean> winCondition) {
        this.appModel = appModel;
        this.gameState = gameState;

        this.winCondition = winCondition;

        gameState.activeCard.set(appModel.getDeckCards().getFirst());

        index.addListener((_, _, newV) -> {
            if (newV.intValue() >= appModel.getDeckCards().size()) {
                index.set(0);
            }
            gameState.activeCard.set(appModel.getDeckCards().get(newV.intValue()));
        });
    }

    @Override
    public Region buildView() {
        RowingViewBuilder viewBuilder = new RowingViewBuilder(gameState, response, canvasWidth, canvasHeight);
        drawGame = viewBuilder::drawGame;

        Region region = viewBuilder.build();

        Platform.runLater(this::initGame);

        Platform.runLater(region::requestFocus);
        region.setOnMouseClicked(e -> region.requestFocus());

        region.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (gameState.overlayOn.get() && event.getCode() == KeyCode.ENTER) {
                submitResponse();
            }
        });

        gameLoop = createLoop();
        gameLoop.start();

        return region;
    }

    @Override
    public void initGame() {
        initDetailPositions();
        initPlayerPositions();
    }

    private void initDetailPositions() {
        Point2D finishLinePosition = new Point2D(0, canvasHeight.get() - gameState.courseLength - gameState.details.getFirst().bounds.getH());

        gameState.initPositions(gameState.details, finishLinePosition);
    }

    private void initPlayerPositions() {
        Point2D[] positions = new Point2D[gameState.playerCount];

        double wStep = canvasWidth.get() / gameState.playerCount;
        double height = canvasHeight.get() - RowingGameState.Player.HEIGHT - 50;

        for (int i = 0; i < gameState.playerCount; i++) {
            double leftBound = wStep * i + (wStep - RowingGameState.Player.WIDTH) / 2;

            positions[i] = new Point2D(leftBound, height);
        }

        gameState.initPositions(gameState.players, positions);
    }

    @Override
    public void updateGame(double delta) {
        if (gameState.overlayOn.get()) {
            return;
        }

        // update next flashcard cycle countdown
        gameState.overlayCountdown.set(gameState.overlayCountdown.get() - delta);

        // update player position and re-calculate player offset for this frame
        updateBoost(gameState.user, delta);
        double playerOffset = -(gameState.user.speed + gameState.user.boostEffect) * delta;

        gameState.details.forEach(detail -> detail.move(-playerOffset));

        gameState.players.forEach(player -> movePlayer(player, delta, playerOffset));
    }

    @Override
    public void submitResponse() {
        gameState.overlayOn.set(false);

        // activate boost
        if (Objects.equals(response.get(), gameState.activeCard.get().back())) {
            gameState.user.boostEffect = gameState.user.boostStrength;
            gameState.user.timeSinceBoost = 0;
        }

        index.set(index.get() + 1);
    }

    private void updateBoost(RowingGameState.Player player, double delta) {
        if (player.boostEffect == 0) {
            return;
        }

        player.timeSinceBoost += delta;

        player.boostEffect = player.boostStrength * (3 - player.timeSinceBoost) / 3;
        player.boostEffect = Math.max(player.boostEffect, 0);
    }

    private void movePlayer(RowingGameState.Player player, double delta, double playerOffset) {
        // move player
        double dy = -(player.speed + player.boostEffect) * delta;
        player.move(dy - playerOffset);

        // check if reached the end, end game if yes
        if (player.metersRowed >= gameState.courseLength) {
            gameLoop.stop();
            winCondition.accept(player.id == 0);
        }
    }

    @Override
    public void drawGame() {
        drawGame.run();
    }
}
