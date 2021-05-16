package ViewController;

import Model.Client;
import Model.Control;
import Model.IO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public Label errorLabelForTwicePassword;
    public Label errorLabelForPhoneNumebr;
    public ChoiceBox sexChoiceBox;

    public void initialize(){
        sexChoiceBox.getItems().add("Male");
        sexChoiceBox.getItems().add("Female");
        sexChoiceBox.setValue("Male");
    }


    public void RegisterButtonClicked(ActionEvent actionEvent) throws Exception {
        errorLabelForTwicePassword.setText("");
        errorLabelForPhoneNumebr.setText("");

        if(!PasswordAgainTextField.getText().equals(PasswordTextField.getText()))
            errorLabelForTwicePassword.setText("Different input from first time.");
        if(!Control.checkPhoneNumberFormat(PhoneNumberTextField.getText()))
            errorLabelForPhoneNumebr.setText("Wrong format of phone number.");

        if(!errorLabelForPhoneNumebr.getText().equals("")||!errorLabelForTwicePassword.getText().equals(""))
            return ;

        Model.Control.register(UsernameTextField.getText(),PhoneNumberTextField.getText(),PasswordTextField.getText(),sexChoiceBox.getValue().toString());
        //jump back to login
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent afterLoginParent = loader.load();
        Scene afterLoginScene = new Scene(afterLoginParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        afterLoginScene.getStylesheets().add
                (RegisterScene.class.getResource("/web/login.css").toExternalForm());
        window.show();

    }

    public void gobackButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LoginScene.fxml"));
        Parent gobackParent = loader.load();
        Scene gobackScene = new Scene(gobackParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(gobackScene);
        gobackScene.getStylesheets().add
                (RegisterScene.class.getResource("/web/login.css").toExternalForm());
        window.show();

    }
}
