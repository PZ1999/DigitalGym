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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
    public Label errorLabelForGoLive;
    public Label errorLabelForBookLive;

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
        //update live
        client = (Client)IO.read(client,client.getPhone_number());
        for(Live l:client.getMy_live())
            if(l.getCourse_id().equals(live.getCourse_id()))
                live = l;

        errorLabelForGoLive.setText("");
        errorLabelForBookLive.setText("");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);
        SceneForClassesPlan controller = loader.getController();
        tabPane.getTabs().remove(1,tabPane.getTabs().size());
        introTag.setContent(controller.pane);
        controller.textForPlanInfo.setText(live.getInfo());
        int i=1;
        priceLabel.setText(live.getPrice()+"");

        Policy policy = (Policy)IO.read(new Policy(),"Policy");
        discountPriceLabel.setText(live.getPrice()*(1-policy.live_discount)+"");


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
            final long HOUR = 3600L*1000;
            int selectedIndex = newValue.intValue();
            String text;
            if (selectedIndex==0){
                liveInfoText.setText("Intro page has no live session");
                return;
            }
            else selectedIndex--;
            LivePlan plan = live.getLive_plan().get(selectedIndex);
            personalPlanTextArea.setText(plan.getPersonal_plan());
            System.out.println(selectedIndex);
            //check if booked

            if(plan.getLive_start_Date()==null){

                text = "Live not booked, please book a live first.";
            }

            else{
                Date end_time = new Date(plan.getLive_start_Date().getTime()+2*HOUR);
                text = "Live session has been booked\n"+"from: "+plan.getLive_start_Date()+"\nto: "+end_time+"\nurl: "+plan.getLive_url();
                if(plan.getFinish())
                    text = "Live session has been finished\n"+"from: "+plan.getLive_start_Date()+"\nto: "+end_time+"\nurl: "+plan.getLive_url();
            }
            liveInfoText.setText(text);
            errorLabelForBookLive.setText("");
            errorLabelForGoLive.setText("");
            //where index of the first tab is 0, while that of the second tab is 1 and so on.
        });
        LocalDate date = LocalDate.now();
        datePicker.setValue(date);
        updateTimePicker();
    }

    public void backButtonClicked(ActionEvent actionEvent) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {


        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);

    }

    public void goLiveButtonClicked(ActionEvent actionEvent) throws IOException {

        int index = tabPane.getSelectionModel().getSelectedIndex();
        if(index==0) return;//intro, no live
        index--;
        if(live.getLive_plan().get(index).getFinish()){
            errorLabelForGoLive.setText("Live session has finished, cannot go live again.");
            return;
        }
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LiveShowScene.fxml"));
        Parent LiveShowSceneParent = loader.load();
        Scene liveShowScene = new Scene(LiveShowSceneParent);
        liveShowScene.setUserData(((Node)actionEvent.getSource()).getScene().getUserData());
        stage.setScene(liveShowScene);
        LiveShowScene controller = loader.getController();


        controller.dayLabel.setText(""+(index+1));
        controller.url = live.getLive_plan().get(index).getLive_url();
        controller.urlLabel.setText(controller.url);
        controller.live_plan = live.getLive_plan().get(index);
        stage.show();
    }

    /**
     * this method is called when book button clicked, need check method in future
     * go to success window.
     * @param actionEvent
     */
    public void bookButtonClicked(ActionEvent actionEvent) throws IOException {

        try {
            Control.bookLiveSession(live,tabPane.getSelectionModel().getSelectedIndex(),datePicker.getValue(),(String)timePicker.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            errorLabelForBookLive.setText(e.toString());
            return ;
        }


//need book logic
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SuccessScene.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        stage.show();
        buildScene();
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
    public void bookDateChanged(ActionEvent actionEvent) throws IOException {
        updateTimePicker();
    }
    public void updateTimePicker() throws IOException {
        LocalDate date = datePicker.getValue();
        Calendar calendar1 = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),8,0);
        Calendar calendar2 = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),10,0);
        Calendar calendar3 = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),13,0);
        Calendar calendar4 = new GregorianCalendar(date.getYear(),date.getMonthValue()-1,date.getDayOfMonth(),15,0);
        Date date1 = calendar1.getTime();
        Date date2 = calendar2.getTime();
        Date date3 = calendar3.getTime();
        Date date4 = calendar4.getTime();
        ArrayList<String> avaliableTimeSlot = Control.findAvaliableBookTimeSlot(live.getTrainer_id(),date1,date2,date3,date4);
        timePicker.getItems().clear();
        for(String s:avaliableTimeSlot){
            timePicker.getItems().add(s);
        }
        if(!timePicker.getItems().isEmpty())
            timePicker.setValue(timePicker.getItems().get(0));
    }

    public void deleteButtonClicked(ActionEvent actionEvent) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        Control.deleteClientLive(client.getPhone_number(),live);

        //back to previous page
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ClientMainSceneController controller = (ClientMainSceneController) previousScene.getUserData();//get controller of previous scene
        controller.updateClassesInMyClass();
        controller.updateClassesInMainPage();
        window.setScene(previousScene);
    }

    public void cancelLiveButtonClicked(ActionEvent actionEvent) throws IOException {
        int index = tabPane.getSelectionModel().getSelectedIndex();
        if(index==0) return;//intro, no live
        if(live.getLive_plan().get(index-1).getFinish()){
            errorLabelForBookLive.setText("Session has been finished, cannot cancel.");
            return;
        }
        Control.cancelLiveSession(live.getLive_plan().get(index-1));
        buildScene();
    }
}
