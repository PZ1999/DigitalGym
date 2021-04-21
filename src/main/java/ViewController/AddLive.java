package ViewController;

import Model.*;
import Model.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AddLive {
    public ComboBox AddLiveTypeComboBox;
    public Button AddLiveBackButton;
    public Button AddLivePublishButton;
    public TextField AddLiveIntroTxtField;
    public TextField AddLiveIntroURL;
    public Tab introTag;
    public TabPane tabPane;
    public ComboBox addLiveDays;
    private Live live;
    private Trainer trainer;
    public Scene previousScene;
    public ArrayList<TextField> plan = new ArrayList<>();
    public ArrayList<TextField> URL = new ArrayList<>();



    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void initialize() throws IOException {
        for (int i = 1; i <= 22; i++)
            addLiveDays.getItems().add(i);
        AddLiveTypeComboBox.getItems().add("Yoga");
        AddLiveTypeComboBox.getItems().add("Bike");
        addLiveDays.setValue(0);
    }


    public void AddLiveBackButtonClicked(ActionEvent actionEvent) {
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        TrainerMainSceneController controller = (TrainerMainSceneController) previousScene.getUserData();//get controller of previous scene
        window.setScene(previousScene);
    }

    public void AddLivePublishButtonClicked(ActionEvent actionEvent) throws IOException {
        Integer day = Integer.parseInt(addLiveDays.getValue().toString());
        ArrayList p = new ArrayList();
        ArrayList U = new ArrayList();
        for (int i = 0; !plan.isEmpty() && !URL.isEmpty() && i < day.intValue(); i++) {
            if(plan.get(i).getText() != "") {
                p.add(plan.get(i).getText());
            }
            if(URL.get(i).getText() != "") {
                U.add(URL.get(i).getText());
            }
        }
        /*Trainer is null so this part contains problem may be right when have the login info------------*/
        //Control.createNewLive(trainer.getPhone_number(), AddLiveTypeComboBox.getValue().toString(),AddLiveIntroTxtField.getText(), p , U);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent publishLive = loader.load();
        Scene publishLiveScene = new Scene(publishLive);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(publishLiveScene);

        window.show();
    }

    public void AddLiveChooseDays(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddLive.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);

        AddLive controller = loader.getController();
        if(tabPane.getTabs().size()>1){
            tabPane.getTabs().remove(1,tabPane.getTabs().size());
        }
        addLiveDays.getValue();
        Integer day = Integer.parseInt(addLiveDays.getValue().toString());
        for (int i = 1; i <= day.intValue(); ) {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AddLive.fxml"));
            parent = loader.load();
            sceneForPlan = new Scene(parent);

            controller = loader.getController();

            Tab tab = new Tab("Day" + i++);
            AnchorPane pane = new AnchorPane();
            pane.setPrefWidth(456);
            pane.setPrefHeight(247);
            TextField tf = new TextField();
            tf.setPrefHeight(247);
            tf.setPrefWidth(324);
            plan.add(tf);
            TextField url = new TextField();
            url.setPrefHeight(209);
            url.setPrefWidth(122);
            Label lb = new Label();
            lb.setText("Video URL:");
            pane.getChildren().add(tf);
            pane.getChildren().add(url);
            pane.getChildren().add(lb);
            AnchorPane.setTopAnchor(url, 36.0);
            AnchorPane.setLeftAnchor(url, 330.0);
            AnchorPane.setTopAnchor(lb, 14.0);
            AnchorPane.setLeftAnchor(lb, 351.0);
            tab.setContent(pane);//Node
            tabPane.getTabs().add(tab);
        }
    }
}



