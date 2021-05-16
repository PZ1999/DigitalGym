package ViewController;

import Model.*;
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
    public Label errorLabelForTwiceInput;
    public Label errorLabelForPasswordFormat;

    public Client client;


    public void buttonClick(ActionEvent actionEvent) throws Exception {
        //It needs to be improved to determine D
        errorLabelForPasswordFormat.setText("");
        errorLabelForTwiceInput.setText("");

        if(!Control.checkPasswordFormat(newPasswordinput.getText()))
            errorLabelForPasswordFormat.setText("Invalid input!Please input again.");
        if(!newPasswordinput.getText().equals(againInput.getText()))
            errorLabelForTwiceInput.setText("Different input from first time.");

        if(!errorLabelForPasswordFormat.getText().equals("")||!errorLabelForTwiceInput.getText().equals(""))
            return ;

        Control.changePassword(client.getPhone_number(),newPasswordinput.getText());


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent afterChangeEmailParent = loader.load();
        Scene afterChangeEmailScene = new Scene(afterChangeEmailParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterChangeEmailScene);
        afterChangeEmailScene.getStylesheets().add
                (ChangePassword.class.getResource("/web/clientmainscene.css").toExternalForm());
        window.show();
    }

}
/**
 *
 */