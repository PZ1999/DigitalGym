package ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ForgetPasswdScene {

    public TextField phoneNumberTextField;
    public TextField verifyCodeTextField;
    public TextField newPasswordTextField;
    public TextField passwordAgainTextField;
    public Label difReminderLabel;
    public Label wrongPhoneNumberLabel;


    public void goBackButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent gobackParent = loader.load();
        Scene gobackScene = new Scene(gobackParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(gobackScene);
        gobackScene.getStylesheets().add
                (ForgetPasswdScene.class.getResource("/web/login.css").toExternalForm());
        window.show();
    }

    public void forgetPasswordOkClicked(ActionEvent actionEvent) throws IOException {
        //System.out.println(Model.Control.checkLoginInfo(phoneNumberTextField.getText(),newPasswordTextField.getText()));
        if((!newPasswordTextField.getText().equals(passwordAgainTextField.getText()))){
            difReminderLabel.setVisible(true);  //password dif error

        }else if((Model.Control.checkLoginInfo(phoneNumberTextField.getText(),newPasswordTextField.getText())).equals("Account not exist")){
            wrongPhoneNumberLabel.setVisible(true); //phone number not exists in the json
        }else{
            Model.Control.changePassword(phoneNumberTextField.getText(), newPasswordTextField.getText()); //phone number & password right
            goBackButtonClicked(actionEvent);
        }

    }
}
