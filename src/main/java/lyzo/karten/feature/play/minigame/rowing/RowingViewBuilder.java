package lyzo.karten.feature.play.minigame.rowing;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lyzo.karten.utility.interfaces.MinigameViewBuilder;
import lyzo.karten.utility.ui.KRegions;

public class RowingViewBuilder implements MinigameViewBuilder {

    @Override
    public Region build() {
        // create base holder
        StackPane pane = KRegions.KStackPane("");
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);

        buildCanvas(pane);

        return pane;
    }

    private void buildCanvas(StackPane parent) {
        Canvas game = new Canvas();
        game.widthProperty().bind(parent.widthProperty().subtract(50));
        game.heightProperty().bind(parent.heightProperty().subtract(50));

        GraphicsContext gc = game.getGraphicsContext2D();

        // redraw whenever size changes
        game.widthProperty().addListener((_, _, _) -> draw(gc, game));
        game.heightProperty().addListener((_, _, _) -> draw(gc, game));

        // attach it to parent
        parent.getChildren().add(game);
    }

    private void draw(GraphicsContext gc, Canvas canvas) {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.BLUE);
        gc.fillRect(0, 0, w, h);
    }

    private Region buildOverlay() {
        return null;
    }
}
