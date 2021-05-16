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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import Model.Client;
import Model.IO;
import Model.Policy;


public class TrainerMainSceneController {
    public Button TrainerMainAddClassButton;
    public Button TrainerMainAddLiveButton;
    public FlowPane MyClassFlowPane;
    public ChoiceBox MyClassFilter;
    public TextArea MyClassOverView;
    public Label MyAccountName;
    public Button MyAccountChangePassword;
    public DatePicker myLiveDatePicker;
    public Button live1SlotButton;
    public Button live2SlotButton;
    public Button live3SlotButton;
    public Button live4SlotButton;
    public TextArea liveIntroTextField;

    public Trainer trainer;
    public TrainerMainSceneController local_controller;


    public void initialize() throws IOException {
        LocalDate date = LocalDate.now();
        myLiveDatePicker.setValue(date);
        MyClassFilter.getItems().add("All");
        Policy policy = (Policy) IO.read(new Policy(),"Policy");
        for(String s : policy.sport_type)
            MyClassFilter.getItems().add(s);
        MyClassFilter.getItems().add("Wait For Approval");
        MyClassFilter.getItems().add("Banned");
        MyClassFilter.setValue(MyClassFilter.getItems().get(0));

    }


    public void buildScene() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        trainer = (Trainer) IO.read(trainer, trainer.getPhone_number());
        MyAccountName.setText(trainer.getName());
        myLiveDatePickerchanged(new ActionEvent());
        myCourseSearchButtonClicked(new ActionEvent());
    }

    public void TrainerMainAddClassButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddClass.fxml"));
        Parent addClassParent = loader.load();
        Scene addClassScene = new Scene(addClassParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        AddClass controller = loader.getController();
        controller.previousScene = ((Node)actionEvent.getSource()).getScene();
        controller.course = getNewCourse();
        controller.buildScene();
        window.setScene(addClassScene);
        addClassScene.getStylesheets().add
                (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        window.show();
    }

    public void TrainerMainAddLiveButtonClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddLive.fxml"));
        Parent addLiveParent = loader.load();
        Scene addLiveScene = new Scene(addLiveParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        AddLive controller = loader.getController();
        controller.previousScene = ((Node)actionEvent.getSource()).getScene();
        controller.live = getNewLive();
        controller.trainer = trainer;
        controller.buildScene();
        window.setScene(addLiveScene);
        addLiveScene.getStylesheets().add
                (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        window.show();

//


    }




    //go to new page
    EventHandler<ActionEvent> liveButtonClicked = new EventHandler<ActionEvent>() {
        /**
         * this function change to the live page according to the live button clicked.
         * @param actionEvent
         */
        @Override
        public void handle(ActionEvent actionEvent) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/LiveScene.fxml"));
            Parent classSceneParent = null;

            try {
                classSceneParent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene classScene = new Scene(classSceneParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            LiveSceneController controller = loader.getController();
            controller.live = (Live) (((Node) actionEvent.getSource()).getUserData());
            controller.previousScene = ((Node) actionEvent.getSource()).getScene();
            //controller.setTrainer(trainer);

            window.setScene(classScene);
            classScene.getStylesheets().add
                    (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
            try {
                controller.buildScene();//build course scene dynamically according to the course information
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    public void MyClassSearchClicked(ActionEvent actionEvent) {
    }

    public void MyClassDeleteClicked(ActionEvent actionEvent) {
    }

    public void MyClassChangeClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AddLive.fxml"));
        Parent addLiveParent = loader.load();
        Scene addLiveScene = new Scene(addLiveParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        AddLive controller = loader.getController();
        controller.previousScene = ((Node)actionEvent.getSource()).getScene();
        window.setScene(addLiveScene);
        addLiveScene.getStylesheets().add
                (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        window.show();
    }

    public void MyAccountChangePasswordClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChangePassword.fxml"));
        Parent changePassWordParent = loader.load();
        Scene changePassWordScene = new Scene(changePassWordParent);

        stage.setScene(changePassWordScene);
        changePassWordScene.getStylesheets().add
                (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        stage.show();
    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) {
    }

    public void myLiveDatePickerchanged(ActionEvent actionEvent) throws IOException {
        //Control.getTrainerLiveSession(trainer.getPhone_number(),myLiveDatePicker.getValue(),)
        live1SlotButton.setText("8:00~10:00");
        live2SlotButton.setText("10:00~12:00");
        live3SlotButton.setText("13:00~15:00");
        live4SlotButton.setText("15:00~17:00");
        LocalDate localDate = myLiveDatePicker.getValue();
        Calendar calendar = new GregorianCalendar(localDate.getYear(),localDate.getMonthValue()-1,localDate.getDayOfMonth());
        Date date = calendar.getTime();
        ArrayList<LivePlan> livePlans = Control.getTrainerLiveSession(trainer.getPhone_number(),date);//wrong parameter
        /*for(LivePlan l : livePlans){
            System.out.println(l.toString());
        }*/
            addLiveToButton(live1SlotButton,livePlans.get(0));
            addLiveToButton(live2SlotButton,livePlans.get(1));
            addLiveToButton(live3SlotButton,livePlans.get(2));
            addLiveToButton(live4SlotButton,livePlans.get(3));


    }
    public void addLiveToButton(Button button,LivePlan live_plan){
        button.setUserData(live_plan);

        if(live_plan==null)
            button.setText(button.getText()+" Free");
        else
            button.setText(button.getText()+" Occupied");
    }

    public void liveSlotClicked(ActionEvent actionEvent) {
        if(((Node)actionEvent.getSource()).getUserData()==null)
            return ;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/LiveSceneForTrainer.fxml"));
        Parent classSceneParent = null;

        try {
            classSceneParent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene classScene = new Scene(classSceneParent);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        LiveSceneForTrainerController controller = loader.getController();
        controller.trainer = this.trainer;
        controller.live_plan = (LivePlan)((Node) actionEvent.getSource()).getUserData();
        controller.previousScene = ((Node)actionEvent.getSource()).getScene();

        classScene.setUserData(controller);
        window.setScene(classScene);
        classScene.getStylesheets().add
                (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
        try {
            controller.buildScene();//build course scene dynamically according to the course information
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void slotMouseIn(MouseEvent mouseEvent) {
        Button button = (Button)mouseEvent.getSource();
        if(button.getUserData()==null) liveIntroTextField.setText("No live session in this interval");
        else liveIntroTextField.setText(button.getUserData().toString());
    }
    public Course getNewCourse() throws IOException {
        Course course = new Course("C"+IO.showAllCourse().size(),"new course",trainer.getPhone_number(),trainer.getName(),"new type","new info",0,0.0,new ArrayList<String>());
        return course;
    }
    private Live getNewLive() throws IOException {
        Live live = new Live("L"+IO.showAllLive().size(),"new course",trainer.getPhone_number(),trainer.getName(),"new type","new info",0,0.0,new ArrayList<LivePlan>(),"");
        return live;
    }

    public void refreshButtonClicked(ActionEvent actionEvent) throws IOException {
        myLiveDatePickerchanged(actionEvent);
    }

    public void myCourseSearchButtonClicked(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        updateClassesInMainPage();
    }
    public void updateClassesInMainPage() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        MyClassFlowPane.getChildren().clear();
        ArrayList<Button> buttons;
        buttons = getClassesButtonsForMyClass();//with filter --PZ
        for(Button button:buttons) {
            MyClassFlowPane.getChildren().add(button);
            button.setPrefSize(170,50);
        }
    }
    /**
     * This method return a set of class buttons for myClass pages.
     * details needed to be added --PZ
     * not usable for id Issue
     * @return
     */
    public ArrayList<Button> getClassesButtonsForMyClass() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        ArrayList<Button> buttons =new ArrayList<Button>();
        Control controller = new Control();
        trainer = (Trainer)IO.read(trainer,trainer.getPhone_number());
        ArrayList <Course> classes = Control.getTrainerCourses(trainer,MyClassFilter.getValue().toString());
        //System.out.println(classes.size());
        for(Course course :classes){
            Button button = new Button();
            button.setPrefSize(170,50);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(classButtonClicked);

            //add action on class button to show over view
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            MyClassOverView.setText(((Course)button.getUserData()).getInfo());;
                        }
                    });
            button.setUserData(course);//add course object to object
            button.setText("Trainner: "+course.getTrainer()+"\n"+course.getName());
            buttons.add(button);

        }
        return buttons;
    }
    EventHandler<ActionEvent> classButtonClicked = new EventHandler<ActionEvent>() {
        /**
         * this function change to the course page according to the class button clicked.
         * @param actionEvent
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            Course course = (Course)(((Node) actionEvent.getSource()).getUserData());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AddClass.fxml"));
            Parent addClassParent = null;
            try {
                addClassParent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene addClassScene = new Scene(addClassParent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            AddClass controller = loader.getController();
            controller.previousScene = ((Node)actionEvent.getSource()).getScene();
            controller.course = course;
            controller.buildSceneForChange();
            window.setScene(addClassScene);
            addClassScene.getStylesheets().add
                    (TrainerMainSceneController.class.getResource("/web/trainer.css").toExternalForm());
            window.show();
        }
    };
}

