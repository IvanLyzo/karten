package lyzo.karten.utility.ui;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class KControls {

    public static Separator KSeparator(String className, Orientation orientation) {
        Separator separator = new Separator(orientation);

        separator.getStyleClass().add(className);

        return separator;
    }

    public static Label KLabel(String className, String message) {
        Label label = new Label(message);

        label.getStyleClass().add(className);

        return label;
    }

    public static TextField KTextField(String className, String initialContent) {
        TextField textField = new TextField(initialContent);

        textField.getStyleClass().add(className);

        return textField;
    }

    public static Button KButton(String className, Node text, EventHandler<MouseEvent> action) {
        Button button = new Button();

        button.setGraphic(text);
        button.setOnMouseClicked(action);

        button.getStyleClass().add(className);

        return button;
    }
}
