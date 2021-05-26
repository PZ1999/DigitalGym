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
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class ClassSceneController {
    public Tab introTag;
    public TabPane tabPane;
    public Button goBack;
    public Label freefor;
    public Label forsingle;
    public Button watchVideo;
    public Button delete;
    public Button subscibe;

    public Scene previousScene;
    public Label accountType;
    public Label price;
    public Label nameLabel;
    public Client client;
    public Course course;
    public Label courseNameLabel;

    public void setCourse(Course course){
        this.course = course;
    }
    public void setClient(Client client) {this.client = client;}
    public void buildScene() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);
        SceneForClassesPlan controller = loader.getController();
        introTag.setContent(controller.pane);
        controller.textForPlanInfo.setText(course.getInfo());
        int i=1;
        for(String s: course.getPlan()){

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
            parent = loader.load();
            sceneForPlan = new Scene(parent);
            controller = loader.getController();

            Tab tab = new Tab("Day"+i++);
            AnchorPane pane = controller.pane;
            tab.setContent(pane);//Node
            controller.textForPlanInfo.setText(s);
            tabPane.getTabs().add(tab);
        }
        accountType.setText((course.getRank()==0)?"Standard":"Premier");
        price.setText(course.getPrice()+"");
        courseNameLabel.setText(course.getName());
        //accountType
        //accountPrice


    }

    public void goBack(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {

        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();//get controller of previous scene
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);
        previousScene.getStylesheets().add
                (ClassSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
    }

    public void watchVideo(ActionEvent actionEvent) throws IOException {
        if(!client.getMy_course().contains(course.getCourse_id()))
            return ;
        int index = tabPane.getSelectionModel().getSelectedIndex();//get day
        if(index==0) return;//intro page selected no vedio
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/WatchVideo.fxml"));
        Parent WatchVideoParent = loader.load();
        Scene WatchVideoScene = new Scene(WatchVideoParent);
        stage.setScene(WatchVideoScene);
        WatchVideo controller = loader.getController();
        controller.dayLabel.setText("Day: "+index);
        controller.url = course.getVideo_path().get(index-1);
        //controller.urlLabel.setText(controller.url);
        controller.courseNameLabel.setText(course.getName());
        stage.show();
        WatchVideoScene.getStylesheets().add
                (ClassSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
        controller.url = "/test.mp4";
        controller.playVedio();
    }

    public void subscribeButtionClicked(ActionEvent actionEvent) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Payment.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        Payment controller = loader.getController();

        controller.itemType = "Course";
        controller.course = course;
        controller.client = client;
        controller.buildScene();
        PaymentScene.getStylesheets().add
                (ClassSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
        stage.show();
    }


    public void deleteButtonClicked(ActionEvent actionEvent) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {

        Control.deleteClientCourse(client.getPhone_number(),course.getCourse_id());

        //back to previous page
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();//get controller of previous scene
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);
        previousScene.getStylesheets().add
                (ClassSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
    }
}
