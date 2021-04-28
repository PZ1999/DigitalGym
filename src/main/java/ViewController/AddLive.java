package ViewController;

import Model.*;
import Model.Control;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class AddLive {
    public Button AddClassBack;
    public Button AddClassPublish;
    public ComboBox typePicker;
    public ComboBox dayPicker;
    public TabPane tabPane;
    public TextArea coursePlanTextArea;
    public TextField nameTextField;
    public Trainer trainer;

    public Scene previousScene;
    public Live live;



    public void initialize(){

        for(int i=1;i<=14;i++)
            dayPicker.getItems().add(i);
        for(String s: Policy.sport_type){
            typePicker.getItems().add(s);
        }
        //dayPicker.setValue(dayPicker.getItems().get(0));
        typePicker.setValue(typePicker.getItems().get(0));
        dayPicker.getSelectionModel().select(0);
        //tabPane.getTabs().add(new Tab("Intro"));

        tabPane.getSelectionModel().selectedIndexProperty().addListener( (observable, oldValue, newValue) -> {
            int old = oldValue.intValue();
            System.out.println("new tab selected: "+old+"->"+newValue.intValue());
            if(old==0){//save intro
                live.setInfo(coursePlanTextArea.getText());
            }
            else{//save plan
                live.getPlan().set(old-1,coursePlanTextArea.getText());
            }
            if(newValue.intValue()==0){
                coursePlanTextArea.setText(live.getInfo());
            }
            else{
                coursePlanTextArea.setText(live.getPlan().get(newValue.intValue()-1));//new plan
            }
        });
        //22222222222AddClassChooseDays(new ActionEvent());
        //dayPicker.getSelectionModel().select(0);
    }

    public void buildScene(){

        tabPane.getSelectionModel().select(0);
        coursePlanTextArea.setText(live.getInfo());
        //tabPane.getSelectionModel().select(1);
        //tabPane.getSelectionModel().select(0);
        int tabSize = tabPane.getTabs().size();
        //System.out.println(tabSize);
        for(int i=1;i<tabSize;i++){
            tabPane.getTabs().remove(1);
        }
        tabPane.getSelectionModel().select(0);

        for(int i=0;i<(Integer) dayPicker.getValue();i++){
            Tab tab = new Tab("Day"+(i+1));
            //System.out.println(tab.getText());
            tabPane.getTabs().add(tab);
        }

        if(live.getPlan().size()<((Integer) dayPicker.getValue())){
            for(int i=live.getPlan().size();i<(Integer)dayPicker.getValue();i++){//more days,add plan and Live_Plan
                //System.out.println("new "+i);
                live.getPlan().add("plan not modified");
                live.getLive_plan().add(new LivePlan(live.getCourse_id(),"",trainer.getPhone_number()));
            }
        }
        else{//less day
            for(int i=live.getPlan().size();i>(Integer)dayPicker.getValue();i--){//less days
                live.getPlan().remove(i-1);
                live.getLive_plan().remove(i-1);
            }
        }
        tabPane.getSelectionModel().select(0);
    }

    public void AddClassBackClicked(ActionEvent actionEvent) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        TrainerMainSceneController controller = (TrainerMainSceneController) previousScene.getUserData();
        controller.buildScene();
        window.setScene(previousScene);
    }

    public void AddClassPublishClicked(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        tabPane.getSelectionModel().select(1);
        tabPane.getSelectionModel().select(0);
        live.setName(nameTextField.getText());
        live.setType(typePicker.getValue().toString());
        Control.addLive(live);
        AddClassBackClicked(actionEvent);
    }

    public void AddClassChooseDays(ActionEvent actionEvent){
        System.out.println("info:"+live.getInfo());
        buildScene();
    }

}
