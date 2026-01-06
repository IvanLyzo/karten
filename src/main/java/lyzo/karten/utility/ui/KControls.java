package lyzo.karten.utility.ui;

import javafx.scene.control.Label;

public class KControls {

    public static Label KLabel(String className, String message) {
        Label label = new Label(message);

        label.getStyleClass().add(className);

        return label;
    }
}
