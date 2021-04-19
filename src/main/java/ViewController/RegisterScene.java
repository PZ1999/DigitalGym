package ViewController;

import Model.Client;
import Model.Control;
import Model.IO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterScene {
    public TextField UsernameTextField;
    public TextField PhoneNumberTextField;
    public TextField CodeTextField;
    public TextField PasswordTextField;
    public RadioButton ClientRadio;
    public RadioButton TrainerRadio;
    public TextField PasswordAgainTextField;
    public Button RegisterButton;
    public TextField SexTextField;

    public void RegisterButtonClicked(ActionEvent actionEvent) throws Exception {
        if(!PasswordAgainTextField.getText().equals(PasswordAgainTextField.getText())){
            System.out.println("dif password twice");
            return ;
        }
        else{

            Control.register(UsernameTextField.getText(),PhoneNumberTextField.getText(),PasswordTextField.getText(),SexTextField.getText());

            //jump back to login
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
            Parent afterLoginParent = loader.load();
            Scene afterLoginScene = new Scene(afterLoginParent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(afterLoginScene);
            window.show();

        }

    }

    public void gobackButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent gobackParent = loader.load();
        Scene gobackScene = new Scene(gobackParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(gobackScene);
        window.show();
    }
}
