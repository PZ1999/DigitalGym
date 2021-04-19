package ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswdScene {
    public TextField phoneNumberTextField;
    public TextField verifyCodeTextField;
    public TextField newPasswordTextField;
    public TextField passwordAgainTextField;

    public void goBackButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent gobackParent = loader.load();
        Scene gobackScene = new Scene(gobackParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(gobackScene);
        window.show();
    }
}
