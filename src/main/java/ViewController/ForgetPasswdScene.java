package ViewController;

import Model.Client;
import Model.Control;
import Model.IO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public Label wrongPhoneNumberLabel;
    public Label errorLabelForPassword;
    public Label errorLabelForNotExist;


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
        wrongPhoneNumberLabel.setText("");
        errorLabelForPassword.setText("");
        errorLabelForNotExist.setText("");

        if((!newPasswordTextField.getText().equals(passwordAgainTextField.getText())))
            errorLabelForPassword.setText("Different input from first time.");
        if(!Control.checkPasswordFormat(newPasswordTextField.getText()))
            errorLabelForPassword.setText("Invalid password, it should be 6-20 bits, digits or letters");
        if(!Control.checkPhoneNumberFormat(phoneNumberTextField.getText()))
            wrongPhoneNumberLabel.setText("Wrong format of phone number. 11 bits digits expected");
        try{
            IO.read(new Client(),phoneNumberTextField.getText());
        }
        catch (Exception e){
            errorLabelForNotExist.setText("Account with this phone number has not been created.");
        }
        if(errorLabelForNotExist.getText().equals("")&&errorLabelForPassword.getText().equals("")&&wrongPhoneNumberLabel.getText().equals("")){
            Model.Control.changePassword(phoneNumberTextField.getText(), newPasswordTextField.getText()); //phone number & password right
            goBackButtonClicked(actionEvent);
        }
    }
}
