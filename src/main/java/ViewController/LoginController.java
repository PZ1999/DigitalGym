package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    public PasswordField passwordTextField;
    public Button RegisterButton;
    public Label errorLabel;


    public void loginButtionClicked(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        String login_info = Control.checkLoginInfo(nameTextField.getText(), passwordTextField.getText());
       if(login_info.equals("Client")){

           goToClientScene(actionEvent);

       }
       else if(login_info.equals("Trainer")){
           //trainer main GUI
           goToTrainerScene(actionEvent);
       }
       else if(login_info.equals("Manager")){
           //manager GUI
           goToManagerScene(actionEvent);
       }
       else if(login_info.equals("fail"))
           errorLabel.setText("Wrong password or user not exist.");

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
    public void goToClientScene(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ClientMainScene.fxml"));
        Parent afterLoginParent = loader.load();
        Scene afterLoginScene = new Scene(afterLoginParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        ClientMainSceneController controller = loader.getController();
        afterLoginScene.setUserData(controller);
        //System.out.println("test");
        controller.client = (Client)IO.read(new Client(),nameTextField.getText());
        controller.buildScene();
        //  controller.id = name;
        //System.out.println(controller.client.getName());
        window.show();
    }

    private void goToManagerScene(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {//further
        System.out.println("manager login");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ManagerMainScene.fxml"));
        Parent afterLoginParent = loader.load();
        Scene afterLoginScene = new Scene(afterLoginParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        ManagerMainSceneController controller = loader.getController();
        afterLoginScene.setUserData(controller);
        window.show();

    }

    private void goToTrainerScene(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {//further
        System.out.println("trainer login");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TrainerMainScene.fxml"));
        Parent afterLoginParent = loader.load();
        Scene afterLoginScene = new Scene(afterLoginParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        TrainerMainSceneController controller = loader.getController();
        controller.trainer = (Trainer) IO.read(new Trainer(),nameTextField.getText());
        afterLoginScene.setUserData(controller);
        controller.buildScene();
        window.show();
    }
}
