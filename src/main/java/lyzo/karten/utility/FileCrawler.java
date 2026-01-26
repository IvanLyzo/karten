package lyzo.karten.utility;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class FileCrawler {

    public final static Image BOAT_GRAPHIC;
    public final static Image FINISH_LINE_GRPAHIC;

    static {
        try {
            BOAT_GRAPHIC = new Image(Objects.requireNonNull(FileCrawler.class.getResource("/graphics/boat.png")).openStream());
            FINISH_LINE_GRPAHIC = new Image(Objects.requireNonNull(FileCrawler.class.getResource("/graphics/finish_line.png")).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
