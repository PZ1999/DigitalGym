package ViewController;


import Model.*;
import Model.Control;
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
import java.util.ArrayList;

public class ManagerMainSceneController {
    public FlowPane myClassFlowPane;
    public TextArea myClassOverviewText;
    public Button myClassSearchButton;
    public ChoiceBox myClassFilterType;
    public RadioButton myClassClassButton;
    public RadioButton myClassLiveButton;
    public ToggleGroup groupForType = new ToggleGroup();
    public ToggleGroup groupForClasses = new ToggleGroup();
    public TextField priceTextField;
    public ComboBox rankComboBox;
    public TextField premiumPriceTextField;
    public TextField premiumDiscountTextField;
    public TextField premiumLiveDiscountTextField;
    public Label premiumErrorLabel;

    //j&yy
    public TextField trainerNameTextField;
    public TextField trainerPhoneNumberTextField;
    public TextField trainerPasswordTextField;
    public TextField trainerPasswordAgainTextField;
    public Button addAccountButton;
    public Label errorLabelForTwicePassword;
    public Label errorLabelForPhoneNumebr;

    public void initialize() throws IOException {
        myClassClassButton.setToggleGroup(groupForType);//initialize first tab
        myClassLiveButton.setToggleGroup(groupForType);
        groupForType.selectToggle(myClassClassButton);
        myClassFilterType.getItems().add("All");
        myClassFilterType.getItems().add("Wait For Approval");
        myClassFilterType.getItems().add("Banned");
        myClassFilterType.getSelectionModel().select(0);
        rankComboBox.getItems().add("Normal");
        rankComboBox.getItems().add("Premium");
        premiumErrorLabel.setVisible(false);
        buildScene();

    }
    public void buildScene() throws IOException {
        //second tab
        Policy policy = (Policy) IO.read(new Policy(),"Policy");
        premiumPriceTextField.setText(String.valueOf(policy.premium_price));
        premiumDiscountTextField.setText(String.valueOf(policy.premium_discount*100));
        premiumLiveDiscountTextField.setText(String.valueOf(policy.live_discount*100));
        //
    }

    public void myClassSearchClicked(ActionEvent actionEvent) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        groupForClasses = new ToggleGroup();
        updateClassesInMyClass();
    }

    public void updateClassesInMyClass() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        myClassFlowPane.getChildren().clear();
        ArrayList<ToggleButton> buttons;
        if(((RadioButton)groupForType.getSelectedToggle()).getText().equals("Class")){
            System.out.println("class");
            buttons = getClasses();
        }

        else{
            System.out.println("live");
            buttons = getLives();
        }

        for(ToggleButton button:buttons){
            button.setToggleGroup(groupForClasses);
            myClassFlowPane.getChildren().add(button);
        }

    }

    private ArrayList<ToggleButton> getLives() throws IOException {
        ArrayList<ToggleButton> buttons =new ArrayList<ToggleButton>();
        Model.Control controller = new Model.Control();
        ArrayList <Live> lives = Control.getAllLives(myClassFilterType.getValue().toString(),null);
        //System.out.println(classes.size());
        for(Live live :lives){
            ToggleButton button = new ToggleButton();
            button.setPrefSize(160,160);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(liveButtonClicked);
            button.setUserData(live);//add course object to object
            button.setText("Trainner: "+live.getTrainer()+"\n"+live.getName());
            buttons.add(button);

        }
        return buttons;
    }

    private ArrayList<ToggleButton> getClasses() throws IOException {
        ArrayList<ToggleButton> buttons =new ArrayList<ToggleButton>();
        Model.Control controller = new Model.Control();
        ArrayList <Course> classes = Control.getAllCourses(myClassFilterType.getValue().toString(),null);
        //System.out.println(classes.size());
        for(Course course :classes){
            ToggleButton button = new ToggleButton();
            button.setPrefSize(160,160);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(classButtonClicked);
            button.setUserData(course);//add course object to object
            button.setText("Trainner: "+course.getTrainer()+"\n"+course.getName());
            buttons.add(button);

        }
        return buttons;
    }
    EventHandler<ActionEvent> classButtonClicked = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Course course =(Course)((Node)actionEvent.getSource()).getUserData();
            myClassOverviewText.setText(course.toString());
            rankComboBox.setValue(course.getRank()==0?"Normal":"Premium");
            priceTextField.setText(((Double)course.getPrice()).toString());
        }
    };
    EventHandler<ActionEvent> liveButtonClicked = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Live live =(Live)((Node)actionEvent.getSource()).getUserData();
            myClassOverviewText.setText(live.toString());
            rankComboBox.setValue(live.getRank()==0?"Normal":"Premium");
            priceTextField.setText(((Double)live.getPrice()).toString());
        }
    };


    public void approveButtonClicked(ActionEvent event) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        ToggleButton button = (ToggleButton)groupForClasses.getSelectedToggle();
        System.out.println(button.getUserData().getClass());
        if(button.getUserData().getClass().equals(new Course().getClass()))
            Control.managerApproveCourse((Course)button.getUserData(),rankComboBox.getValue().toString(),Double.parseDouble(priceTextField.getText()));

        else
            Control.managerApproveLive((Live)button.getUserData(),rankComboBox.getValue().toString(),Double.parseDouble(priceTextField.getText()));
        myClassSearchClicked(event);
    }

    public void banButtonClicked(ActionEvent event) {
    }

    public void discountSaveButtonClicked(ActionEvent event) {
        premiumErrorLabel.setVisible(false);
        try {
            if(!Control.changePolicy(Double.parseDouble(premiumPriceTextField.getText()),Double.parseDouble(premiumDiscountTextField.getText())/100.0,Double.parseDouble(premiumLiveDiscountTextField.getText())/100.0))
                premiumErrorLabel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            premiumErrorLabel.setVisible(true);
        }

    }

    public void addAccountButtonClicked(ActionEvent actionEvent) throws IOException {
//        public TextField trainerNameTextField;
//        public TextField trainerPhoneNumberTextField;
//        public TextField trainerPasswordTextField;
//        public TextField trainerPasswordAgainTextField;
//        public Button addAccountButton;
//        public Label errorLabelForTwicePassword;
//        public Label errorLabelForPhoneNumebr;

        errorLabelForTwicePassword.setText("");
        errorLabelForPhoneNumebr.setText("");

        if(!trainerPasswordAgainTextField.getText().equals(trainerPasswordTextField.getText()))
            errorLabelForTwicePassword.setText("Different input from first time.");
        if(!Control.checkPhoneNumberFormat(trainerPhoneNumberTextField.getText()))
            errorLabelForPhoneNumebr.setText("Wrong format of phone number.");

        if(!errorLabelForPhoneNumebr.getText().equals("")||!errorLabelForTwicePassword.getText().equals(""))
            return ;

        Model.Control.trainerAddAccount(trainerNameTextField.getText(),trainerPhoneNumberTextField.getText(),trainerPasswordTextField.getText());
        //jump back to login
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ManagerMainScene.fxml"));
        Parent afterLoginParent = loader.load();
        Scene afterLoginScene = new Scene(afterLoginParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(afterLoginScene);
        afterLoginScene.getStylesheets().add
                (ManagerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        window.show();

    }
}
