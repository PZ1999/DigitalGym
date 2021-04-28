package ViewController;

import Model.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Model.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Date;

public class LiveSceneForTrainerController {

    public TextArea LiveClient;
    public Button Go;
    public Button CancelLive;
    public TextArea personalPlanTextArea;
    public Tab introTag;
    public TextArea liveInfoText;
    public Label errorLabel;
    public TabPane tabPane;
    public Label liveClientLabel;

    public Live live;
    public Scene previousScene;
    public Trainer trainer;
    public LivePlan live_plan;

    int day;

    public void buildScene() throws IOException {
        trainer =(Trainer) IO.read(trainer,trainer.getPhone_number());
        System.out.println(live_plan.getCourse_id());
        System.out.println(live_plan.getClient_id());
        for(Live l:trainer.getMy_live()){
            System.out.println(l.getCourse_id());
            System.out.println(l.getClient_id());
            if(l.getCourse_id().equals(live_plan.getCourse_id())&&l.getClient_id().equals(live_plan.getClient_id())){

                live = l;
            }

        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SceneForClassesPlan.fxml"));
        Parent parent = loader.load();
        Scene sceneForPlan = new Scene(parent);
        SceneForClassesPlan controller = loader.getController();
        tabPane.getTabs().remove(1,tabPane.getTabs().size());
        introTag.setContent(controller.pane);
        controller.textForPlanInfo.setText(live.getInfo());
        int i=1;

        for(String plan : live.getPlan()){//generate all tab
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

        for(int j=0;j<live.getLive_plan().size();j++){
            if(live.getLive_plan().get(j).getLive_start_Date()==null) continue;
            if(live.getLive_plan().get(j).getLive_start_Date().equals(live_plan.getLive_start_Date())){
                tabPane.getSelectionModel().select(j+1);//change to target tab
                day = j;
            }
        }
        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            if(newValue.intValue()==day+1)return;
            System.out.println(oldValue+"\n"+newValue);
            tabPane.getSelectionModel().select(oldValue.intValue());//pin down the tab of a date from live_plan
        });
        live_plan = live.getLive_plan().get(day);
        final long HOUR = 3600L*1000;
        String text;
        Date end_time = new Date(live_plan.getLive_start_Date().getTime()+2*HOUR);
        text = "Live session has been booked\n"+"from: "+live_plan.getLive_start_Date()+"\nto: "+end_time+"\nurl: "+live_plan.getLive_url();
        if(live_plan.getFinish())
            text = "Live session has been finished\n"+"from: "+live_plan.getLive_start_Date()+"\nto: "+end_time+"\nurl: "+live_plan.getLive_url();
        liveInfoText.setText(text);
        personalPlanTextArea.setText(live_plan.getPersonal_plan());
        liveClientLabel.setText(((Client)IO.read(new Client(),live_plan.getClient_id())).getName());
    }


    public void GoLive(ActionEvent actionEvent) throws IOException {

        int index = tabPane.getSelectionModel().getSelectedIndex();
        if(index==0) return;//intro, no live
        index--;
        if(live.getLive_plan().get(index).getFinish()){
            errorLabel.setText("Live session has finished, cannot go live again.");
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

    public void CancelLive(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        int index = tabPane.getSelectionModel().getSelectedIndex();
        if(index==0) return;//intro, no live
        if(live.getLive_plan().get(index-1).getFinish()){
            errorLabel.setText("Session has been finished, cannot cancel.");
            return;
        }
        Control.cancelLiveSession(live.getLive_plan().get(index-1));
        goBackButtonClicked(actionEvent);
    }

    public void goBackButtonClicked(ActionEvent actionEvent) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        TrainerMainSceneController controller = (TrainerMainSceneController) previousScene.getUserData();
        controller.buildScene();
        window.setScene(previousScene);
    }

    public void savePersonalPlanButtonCliked(ActionEvent actionEvent) throws IOException {
        live.getALivePlan(day).setPersonal_plan(personalPlanTextArea.getText());
        Control.updatePeronalLive(live,day);
        buildScene();
        errorLabel.setText("presonal plan saved.");
    }
}
