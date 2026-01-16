package lyzo.karten.feature.play.minigame.rowing;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lyzo.karten.utility.interfaces.minigame.MinigameViewBuilder;
import lyzo.karten.utility.ui.KRegions;

import java.util.function.Function;

public class RowingViewBuilder implements MinigameViewBuilder {

    private final RowingGameState gameState;

    private final DoubleProperty canvasWidth;
    private final DoubleProperty canvasHeight;

    private final Runnable initGame;
    private final Function<Double, Boolean> updateGame;

    public RowingViewBuilder(RowingGameState gameState, DoubleProperty canvasWidth, DoubleProperty canvasHeight,
                             Runnable initGame, Function<Double, Boolean> updateGame) {
        this.gameState = gameState;

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        this.initGame = initGame;
        this.updateGame = updateGame;
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

        return pane;
    }

    private void buildCanvas(StackPane parent) {
        Canvas game = new Canvas();

        // bind canvas size to container
        game.widthProperty().bind(parent.widthProperty().subtract(50));
        game.heightProperty().bind(parent.heightProperty().subtract(50));

        // bind width/height references to canvas size
        canvasWidth.bind(game.widthProperty());
        canvasHeight.bind(game.heightProperty());

        // add listener for controller player position initialization
        ChangeListener<Bounds> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> obs, Bounds oldB, Bounds newB) {
                if (newB.getWidth() > 0 && newB.getHeight() > 0) {
                    initGame.run();
                    game.layoutBoundsProperty().removeListener(this);
                }
            }
        };
        game.layoutBoundsProperty().addListener(listener);

        // get graphics context (paint brush)
        GraphicsContext gc = game.getGraphicsContext2D();

        // redraw whenever size changes
        game.widthProperty().addListener((_, _, _) -> redraw(gc, 0));
        game.heightProperty().addListener((_, _, _) -> redraw(gc, 0));

        // redraw based on animation timer (ticks)
        new AnimationTimer() {
            private long lastTick = 0;

            @Override
            public void handle(long now) {
                // edge case for first tick run to not break delta limits
                if (lastTick == 0) {
                    lastTick = now;
                    return;
                }

                // calculate time since last tick
                double deltaTime = (now - lastTick) / 1_000_000_000.0;

                // update controller (stop if needed)
                if (!updateGame.apply(deltaTime)) {
                    stop();
                }

                // redraw view
                redraw(gc, deltaTime);

                lastTick = now;
            }
        }.start();

        // attach it to parent
        parent.getChildren().add(game);
    }

    private void redraw(GraphicsContext gc, double delta) {
        // save local reference to size
        double w = canvasWidth.get();
        double h = canvasHeight.get();

        // clear previous drawing
        gc.clearRect(0, 0, w, h);

        // paint background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, w, h);

        // paint players
        for (RowingGameState.Player player : gameState.players) {
            drawPlayer(gc, player);
        }
    }

    private void drawPlayer(GraphicsContext gc, RowingGameState.Player player) {
        // save local references to size
        double w = canvasWidth.get();
        double h = canvasHeight.get();

        // skip drawing if player not on screen
        if (player.bounds.getY() + player.bounds.getH() < 0 || player.bounds.getY() > h) {
            return;
        }

        // draw player at position
        gc.setFill(Color.WHITE);
        gc.fillRect(player.bounds.getX(), player.bounds.getY(), player.bounds.getW(), player.bounds.getH());
    }

    private Region buildOverlay() {
        return null;
    }
}
