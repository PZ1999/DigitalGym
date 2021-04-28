package ViewController;

import Model.*;
import Model.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class AddClass {
    public Button AddClassBack;
    public Button AddClassPublish;
    public ComboBox typePicker;
    public ComboBox dayPicker;
    public TabPane tabPane;
    public TextArea coursePlanTextArea;
    public TextField urlTextField;
    public Label urlLabel;
    public TextField nameTextField;

    public Scene previousScene;
    public Course course;



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
                course.setInfo(coursePlanTextArea.getText());
            }
            else{//save plan
                course.getPlan().set(old-1,coursePlanTextArea.getText());
                course.getVideo_path().set(old-1,urlTextField.getText());
            }
            if(newValue.intValue()==0){
                urlTextField.setVisible(false);
                urlLabel.setVisible(false);
                coursePlanTextArea.setText(course.getInfo());
            }
            else{
                urlTextField.setVisible(true);
                urlLabel.setVisible(true);
                coursePlanTextArea.setText(course.getPlan().get(newValue.intValue()-1));//new plan
                urlTextField.setText(course.getVideo_path().get(newValue.intValue()-1));//new url
            }
        });
        //22222222222AddClassChooseDays(new ActionEvent());
        //dayPicker.getSelectionModel().select(0);
    }

    public void buildScene(){

        tabPane.getSelectionModel().select(0);
        coursePlanTextArea.setText(course.getInfo());
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

        if(course.getPlan().size()<((Integer) dayPicker.getValue())){
            for(int i=course.getPlan().size();i<(Integer)dayPicker.getValue();i++){//more days
                //System.out.println("new "+i);
                course.getPlan().add("plan not modified");
                course.getVideo_path().add("test.mp4");
            }
        }
        else{//less day
            for(int i=course.getPlan().size();i>(Integer)dayPicker.getValue();i--){//less days
                course.getPlan().remove(i-1);
                course.getVideo_path().remove(i-1);
            }
        }
        tabPane.getSelectionModel().select(0);
        System.out.println(course.getPlan().size());
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
        course.setName(nameTextField.getText());
        course.setType(typePicker.getValue().toString());
        Control.addCourse(course);
        AddClassBackClicked(actionEvent);
    }

    public void AddClassChooseDays(ActionEvent actionEvent){
        System.out.println("info:"+course.getInfo());
        buildScene();
    }

}
