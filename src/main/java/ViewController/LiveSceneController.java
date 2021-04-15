package ViewController;

import Model.Client;
import Model.Control;
import Model.Live;
import Model.Plan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class LiveSceneController {
    public DatePicker datePicker;
    public ChoiceBox timePicker;
    public Tab introTag;

    public TabPane tabPane;
    public Scene previousScene;
    public Label discountPriceLabel;
    public Label priceLabel;
    public TextArea liveInfoText;
    public Live live;//should be Live Session
    public Client client;
    @FXML
    public void initialize() {
        timePicker.getItems().add("8:00 am ~ 10:00 am");
        timePicker.getItems().add("10:00 am ~ 12:00 am");
        timePicker.getItems().add("13:00 pm ~ 15:00 pm");
        timePicker.getItems().add("15:00 pm ~ 17:00 pm");
        timePicker.setValue(timePicker.getItems().get(0));
    }
    public void setLive(Live live){this.live=live;}
    public void setClient(Client client){this.client = client;}
    public void buildScene() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);
        SceneForClassesPlan controller = loader.getController();
        introTag.setContent(controller.pane);
        controller.textForPlanInfo.setText(live.getInfo());
        int i=1;
        priceLabel.setText(live.getPrice()+"");
        discountPriceLabel.setText(live.getPrice()*0.9+"");
        for(Plan plan : live.getDay_Plans()){

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
            parent = loader.load();
            sceneForPlan = new Scene(parent);
            controller = loader.getController();

            Tab tab = new Tab("Day"+i++);
            AnchorPane pane = controller.pane;
            tab.setContent(pane);//Node
            controller.textForPlanInfo.setText(plan.getPlan());
            tabPane.getTabs().add(tab);
        }
        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            int selectedIndex = newValue.intValue();
            //check if booked
            Plan plan = live.getDay_Plans().get(selectedIndex);
            liveInfoText.setText("Live session has been booked\n"+"from: "+plan.getStart_time()+"\nto: "+plan.getEnd_time()+"\nurl: "+plan.getVideo_path());
            //where index of the first tab is 0, while that of the second tab is 1 and so on.
        });
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {


        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);

    }

    public void goLiveButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LiveShowScene.fxml"));
        Parent LiveShowSceneParent = loader.load();
        Scene LiveShowScene = new Scene(LiveShowSceneParent);

        stage.setScene(LiveShowScene);
        LiveShowScene controller = loader.getController();
        int index = tabPane.getSelectionModel().getSelectedIndex();
        controller.dayLabel.setText("Day: "+index);
        controller.url = live.getDay_Plans().get(index).getVideo_path();
        controller.urlLabel.setText(controller.url);

        stage.show();
    }

    /**
     * this method is called when book button clicked, need check method in future
     * go to success window.
     * @param actionEvent
     */
    public void bookbuttonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);

        stage.setScene(PaymentScene);

        stage.show();
    }
    /**
     * this method is called when subscribe button clicked, need check method in future
     * go to payment window.
     * @param actionEvent
     */
    public void subscribeButtonClicked(ActionEvent actionEvent) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
        changeToPayment(live.getInfo(),live.getPrice()+"$");
        Control control = new Control();
        control.AddLive(client.getId(),live.getId());
    }
    public void changeToPayment(String item,String price) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Payment.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        Payment controller = loader.getController();
        controller.buildScene(item,price);
        stage.show();
    }
}
