package lyzo.karten.feature.play.minigame.rowing;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lyzo.karten.utility.structures.minigame.MinigameViewBuilder;
import lyzo.karten.utility.ui.KRegions;

public class RowingViewBuilder extends MinigameViewBuilder {

    private final RowingGameState gameState;

    private final StringProperty response;

    private final DoubleProperty canvasWidth;
    private final DoubleProperty canvasHeight;

    private GraphicsContext gc;

    public RowingViewBuilder(RowingGameState gameState, StringProperty response,
                             DoubleProperty canvasWidth, DoubleProperty canvasHeight) {
        this.gameState = gameState;
        this.response = response;

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
        buildOverlay(pane, gameState, response);

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
        gameState.details.forEach(this::drawObj);

        // paint players
        gameState.players.forEach(this::drawObj);
    }

    private void drawObj(RowingGameState.GameObject obj) {
        // save local references to canvas size
        double h = canvasHeight.get();

        // skip drawing if player not on screen
        if (obj.bounds.getY() + obj.bounds.getH() < 0 || obj.bounds.getY() > h) {
            return;
        }

        // draw player
        obj.draw(gc);
    }
}
