package lyzo.karten.utility.ui;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class KControls {

    public static Label KLabel(String className, String message) {
        Label label = new Label(message);

        label.getStyleClass().add(className);

        return label;
    }

    public static Button KButton(String className, Node text, EventHandler<MouseEvent> action) {
        Button button = new Button();

        button.setGraphic(text);
        button.setOnMouseClicked(action);

        button.getStyleClass().add(className);

        return button;
    }
}
