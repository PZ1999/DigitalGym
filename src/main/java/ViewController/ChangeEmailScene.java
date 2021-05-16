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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ChangeEmailScene {

    public TextField varify;
    public TextField newemail;
    public String userEmail;
    public Button OkButton;
    public Button sendVarifyButton;
    public Label errorLabel;
    public Client client;
    public String id;
    public TextField newPhoneNumber;
    public ClientMainSceneController mainSceneController;

    /**
     * This function is called OkButtonclicked, it is clicked once user want to change their e-mail.After being clicked,the new e-mail will replace original data and jump to the success interface.
     * @param actionEvent
     */
    public void OkButtonclicked(ActionEvent actionEvent) throws Exception, ParserConfigurationException, SAXException, XPathExpressionException {
        errorLabel.setText("");
        if(!Control.checkPhoneNumberFormat(newPhoneNumber.getText())){
            errorLabel.setText("Invalid phone number!");
            return ;
        }

       //
        //System.out.println(client_id);
        Control.changePhoneNumber(client.getPhone_number(),newPhoneNumber.getText());

        mainSceneController.client = (Client) IO.read(new Client(),newPhoneNumber.getText());//refresh main scene
        mainSceneController.buildScene();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent afterChangeEmailParent = loader.load();
        Scene afterChangeEmailScene = new Scene(afterChangeEmailParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterChangeEmailScene);
        afterChangeEmailScene.getStylesheets().add
                (ChangeEmailScene.class.getResource("/web/clientmainscene.css").toExternalForm());
        window.show();
    }



    /**
     * this function is called sendVarifyClicked, once user click it,user will receive a code to varify.
     * @param actionEvent
     */
    public void sendVarifyClicked(ActionEvent actionEvent) {
    }
}
