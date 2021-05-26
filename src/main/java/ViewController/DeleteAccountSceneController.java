package ViewController;

import Model.Control;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteAccountSceneController {
    public String client_id;
    public Stage stage;
    public void confirmButtonClicked(ActionEvent actionEvent) throws Exception {
        Control.deleteClient(client_id);
        ((Stage)(((Node)actionEvent.getSource()).getScene().getUserData())).close();
        ((Stage)(((Node)actionEvent.getSource()).getScene().getWindow())).close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
        stage.setTitle("iGym");
        Scene deleteAccountScene = new Scene(root, 800, 600);
        stage.setScene(deleteAccountScene);
        deleteAccountScene.getStylesheets().add
                (DeleteAccountSceneController.class.getResource("/web/login.css").toExternalForm());
        stage.show();
    }
}
