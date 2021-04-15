package ViewController;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SuccessScene {
    /**
     * this function is called OkButtonClicked, once user is informed of their changing action successfully, he click it to next steps.
     * @param actionEvent
     */
    public void OkButtonClicked(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
