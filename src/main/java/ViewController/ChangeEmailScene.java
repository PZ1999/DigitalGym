package ViewController;


import Model.Client;

import Model.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ChangeEmailScene {

    public TextField varify;
    public String userEmail;
    public Button OkButton;
    public Button sendVarifyButton;
    public Label errorLabel;
    public Client client;
    public String id;
    public String client_id;
    public TextField newPhoneNumber;

    /**
     * This function is called OkButtonclicked, it is clicked once user want to change their e-mail.After being clicked,the new e-mail will replace original data and jump to the success interface.
     * @param actionEvent
     */
    public void OkButtonclicked(ActionEvent actionEvent) throws Exception, ParserConfigurationException, SAXException, XPathExpressionException {

        if(newPhoneNumber.getText().length()!=11||newPhoneNumber.getText().charAt(0)!='1'||!checkPhoneNumber()){
            errorLabel.setText("Invalid phone number!");
            return ;
        }
        if(!checkVarificationCode()){
            errorLabel.setText("Verification code is wrong!");
            return ;
        }
        Control.changePhoneNumber(client_id,newPhoneNumber.getText());

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent afterChangeEmailParent = loader.load();
        Scene afterChangeEmailScene = new Scene(afterChangeEmailParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterChangeEmailScene);

        window.show();
    }

    private boolean checkPhoneNumber() {
        int count=0;
        for(int i=0;i<11;i++) {
            if (newPhoneNumber.getText().charAt(i)<'1'||newPhoneNumber.getText().charAt(i)>'9'){
                count++;
            }
        }
        if(count==0)    return true;
        else return false;
    }

    private boolean checkVarificationCode() {
        //Suppose the verification code is 1234
        if(varify.getText().equals("1234")) return true;
        else return false;
    }

    /**
     * this function is called sendVarifyClicked, once user click it,user will receive a code to varify.
     * @param actionEvent
     */
    public void sendVarifyClicked(ActionEvent actionEvent) {
    }
}
