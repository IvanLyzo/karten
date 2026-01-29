package lyzo.karten.io.resources;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class GraphicLoader {

    public final static Image RED_BOAT_GRAPHIC;
    public final static Image GREEN_BOAT_GRAPHIC;
    public final static Image FINISH_LINE_GRAPHIC;

    static {
        try {
            RED_BOAT_GRAPHIC = new Image(Objects.requireNonNull(GraphicLoader.class.getResource("/graphics/red-boat.png")).openStream());
            GREEN_BOAT_GRAPHIC = new Image(Objects.requireNonNull(GraphicLoader.class.getResource("/graphics/green-boat.png")).openStream());
            FINISH_LINE_GRAPHIC = new Image(Objects.requireNonNull(GraphicLoader.class.getResource("/graphics/finish-line.png")).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
