package lyzo.karten.disk.file;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class ResourceAccess {

    public final static Image RED_BOAT_GRAPHIC;
    public final static Image GREEN_BOAT_GRAPHIC;
    public final static Image FINISH_LINE_GRPAHIC;

    static {
        try {
            RED_BOAT_GRAPHIC = new Image(Objects.requireNonNull(ResourceAccess.class.getResource("/graphics/red-boat.png")).openStream());
            GREEN_BOAT_GRAPHIC = new Image(Objects.requireNonNull(ResourceAccess.class.getResource("/graphics/green-boat.png")).openStream());
            FINISH_LINE_GRPAHIC = new Image(Objects.requireNonNull(ResourceAccess.class.getResource("/graphics/finish-line.png")).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
