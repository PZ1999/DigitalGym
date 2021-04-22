package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Date;


import Model.Client;
import Model.IO;
import Model.Policy;


public class TrainerMainSceneController {
    public Button TrainerMainAddClassButton;
    public Button TrainerMainAddLiveButton;
    public ChoiceBox myLiveFilterType;
    public Button Goto;
    public TextArea myLiveOverviewText;
    public Model.Trainer trainer;
    public TrainerMainSceneController local_controller;
    public FlowPane MyClassFlowPane;
    public ChoiceBox MyClassFilter;
    public Button MyClassSearch;
    public TextField MyClassOverView;
    public Button MyClassDelete;
    public Button MyClassChange;
    public Label MyAccountName;
    public Button MyAccountChangePassword;
    public Button MyAccountchangeEmail;
    public TextField MyAccountIntro;


    public void TrainerMainAddClassButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddClass.fxml"));
        Parent addClassParent = loader.load();
        Scene addClassScene = new Scene(addClassParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(addClassScene);
        window.show();
    }

    public void TrainerMainAddLiveButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddLive.fxml"));
        Parent addLiveParent = loader.load();
        Scene addLiveScene = new Scene(addLiveParent);

        AddLive controller = loader.getController();
        controller.setTrainer(trainer);
        stage.setScene(addLiveScene);
        stage.show();
//


    }

    public void MyClassSearchClicked(ActionEvent actionEvent) {
    }

    public void MyClassDeleteClicked(ActionEvent actionEvent) {
    }

    public void MyClassChangeClicked(ActionEvent actionEvent) {
    }

    public void MyAccountChangePasswordClicked(ActionEvent actionEvent) {
    }

    public void MyAccountChangeEmailClicked(ActionEvent actionEvent) {
    }
}

