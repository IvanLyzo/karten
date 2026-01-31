package lyzo.karten.feature.play.minigame.rowing;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lyzo.karten.utility.structures.minigame.GameState;
import lyzo.karten.utility.structures.minigame.MinigameViewBuilder;
import lyzo.karten.utility.ui.KRegions;

import java.util.function.Consumer;

public class RowingViewBuilder extends MinigameViewBuilder {

    private final RowingGameState gameState;
    private final Consumer<String> submitFlashcardAction;

    private final DoubleProperty canvasWidth;
    private final DoubleProperty canvasHeight;

    private GraphicsContext gc;
    private Timeline timeline;

    public RowingViewBuilder(RowingGameState gameState, Consumer<String> submitFlashcardAction, DoubleProperty canvasWidth, DoubleProperty canvasHeight) {

        this.gameState = gameState;
        this.submitFlashcardAction = submitFlashcardAction;

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    @Override
    public Region build() {
        // create base holder
        StackPane pane = KRegions.KStackPane("");

        // set expand properties
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);

        // build canvas
        buildCanvas(pane);

        // build overlay
        buildOverlay(pane, gameState);

        return pane;
    }

    private void buildCanvas(StackPane parent) {
        Canvas game = new Canvas();

        // get graphics context (paint brush)
        gc = game.getGraphicsContext2D();

        // bind canvas size to container
        game.widthProperty().bind(parent.widthProperty().subtract(50));
        game.heightProperty().bind(parent.heightProperty().subtract(50));

        // bind width/height references to canvas size
        canvasWidth.bind(game.widthProperty());
        canvasHeight.bind(game.heightProperty());

        // redraw whenever size changes
        game.widthProperty().addListener((_, _, _) -> drawGame());
        game.heightProperty().addListener((_, _, _) -> drawGame());

        // attach it to parent
        parent.getChildren().add(game);
    }

    @Override
    public void drawGame() {
        // save local reference to size
        double w = canvasWidth.get();
        double h = canvasHeight.get();

        // clear previous drawing
        gc.clearRect(0, 0, w, h);

        // paint background
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, w, h);

        // paint details
        gameState.details.forEach(this::drawGameObject);

        // paint players
        gameState.players.forEach(this::drawGameObject);
    }

    private void drawGameObject(RowingGameState.GameObject obj) {
        // save local references to canvas size
        double h = canvasHeight.get();

        // skip drawing if player not on screen
        if (obj.bounds.getY() + obj.bounds.getH() < 0 || obj.bounds.getY() > h) {
            return;
        }

        // draw player
        obj.draw(gc);
    }

    public void buildOverlay(StackPane parent, GameState gameState) {
        super.buildOverlay(parent, gameState, submitFlashcardAction);

        ProgressBar progress = new ProgressBar();

        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), _ -> submitFlashcardAction.accept(""), new KeyValue(progress.progressProperty(), 1))
        );
        timeline.setCycleCount(1);

        HBox container = KRegions.KHorizontalBox("", Pos.TOP_CENTER, 0, progress);

        gameState.overlayOn.addListener(((_, _, newV) -> {
            if (newV) {
                parent.getChildren().add(container);
                timeline.play();
            } else {
                parent.getChildren().remove(container);
                timeline.stop();
            }
        }));
    }
}
