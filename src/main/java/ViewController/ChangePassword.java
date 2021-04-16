package ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePassword {

    public Button ok;
    public Button sendVerified;
    public Label email;
    public Label again;
    public Label newPassword;
    public Label verifyCode;
    public TextField emailInput;
    public TextField verifyCodepoint;
    public TextField againInput;
    public TextField newPasswordinput;


    public void buttonClick(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent afterChangeEmailParent = loader.load();
        Scene afterChangeEmailScene = new Scene(afterChangeEmailParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterChangeEmailScene);
        window.show();
    }
}
/**
 *
 */