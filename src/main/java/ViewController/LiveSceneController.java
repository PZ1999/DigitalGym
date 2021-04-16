package ViewController;


import Model.Control;
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
import java.time.ZoneId;
import java.util.Date;

import Model.*;


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
    public TextArea personalPlanTextArea;
    public Label liveSessionNameLabel;

    @FXML
    public void initialize() {
        Date date = new Date();


        timePicker.getItems().add("8:00 am ~ 10:00 am");
        timePicker.getItems().add("10:00 am ~ 12:00 am");
        timePicker.getItems().add("13:00 pm ~ 15:00 pm");
        timePicker.getItems().add("15:00 pm ~ 17:00 pm");
        timePicker.setValue(timePicker.getItems().get(0));
    }

    /**
     * to be continue... --PZ
     */



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
        discountPriceLabel.setText(live.getPrice()*(1-Policy.live_discount)+"");


        for(String plan : live.getPlan()){
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
            parent = loader.load();
            sceneForPlan = new Scene(parent);
            controller = loader.getController();

            Tab tab = new Tab("Day"+i++);
            AnchorPane pane = controller.pane;
            tab.setContent(pane);//Node
            controller.textForPlanInfo.setText(plan);
            tabPane.getTabs().add(tab);
        }

        liveSessionNameLabel.setText(liveSessionNameLabel.getText()+live.getName());
        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            final long HOUR = 3600*1000;
            int selectedIndex = newValue.intValue();
            LivePlan plan = live.getLive_plan().get(selectedIndex);
            personalPlanTextArea.setText(plan.getPersonal_plan());
            //check if booked
            String text;
            if(plan.getLive_start_Date()==null)
                text = "Live not booked, please book a live first.";
            else{
                Date end_time = new Date(plan.getLive_start_Date().getTime()+2*HOUR);
                text = "Live session has been booked\n"+"from: "+plan.getLive_start_Date()+"\nto: "+end_time+"\nurl: "+plan.getLive_url();
            }
            liveInfoText.setText(text);
            //where index of the first tab is 0, while that of the second tab is 1 and so on.
        });
        //Date selected_date = Date.from(datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        //need more...
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
        controller.url = live.getLive_plan().get(index).getLive_url();
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
//need book logic

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
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Payment.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        Payment controller = loader.getController();

        controller.itemType = "Live";
        controller.live = live;
        controller.client = client;
        controller.buildScene();

        stage.show();
    }


    /**
     * called when date picker changed, update timeslot which is avaliable
     * @param actionEvent
     */
    public void bookDateChanged(ActionEvent actionEvent) {

    }
}
