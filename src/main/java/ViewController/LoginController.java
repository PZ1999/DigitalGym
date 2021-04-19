package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import Model.*;

public class LoginController {

    public TextField nameTextField;
    public TextField passwordTextField;
    public Button RegisterButton;


    public void loginButtionClicked(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        Control con = new Control();
       if((Control.checkLoginInfo(nameTextField.getText(), passwordTextField.getText())).equals("Client")){

           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/fxml/ClientMainScene.fxml"));
           Parent afterLoginParent = loader.load();
           Scene afterLoginScene = new Scene(afterLoginParent);
           Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
           window.setScene(afterLoginScene);
           ClientMainSceneController controller = loader.getController();
           afterLoginScene.setUserData(controller);
           controller.client = (Client)IO.read(new Client(),nameTextField.getText());
           controller.buildScene();
         //  controller.id = name;
           //System.out.println(controller.client.getName());
           window.show();
       }

    }

    public void RegisterButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/RegisterScene.fxml"));
        Parent afterRegisterParent = loader.load();
        Scene afterRegisterScene = new Scene(afterRegisterParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterRegisterScene);
        window.show();

    }

    public void forgetPasswordButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ForgetPasswdScene.fxml"));
        Parent forgetPasswdParent = loader.load();
        Scene forgetPasswdScene = new Scene(forgetPasswdParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(forgetPasswdScene);
        window.show();
    }
}
