package ViewController;


import Model.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import Model.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;

public class ClientMainSceneController {

   // public Client client.
    @FXML

    public Label premiumLabel;
    public ChoiceBox myAccountAgeField;//this field is about body information in my account
    public TextField myAccountWeightField;//this field is about body information in my account
    public TextField myAccountBMIField;//this field is about body information in my account
    public TextField myAccountHeightField;//this field is about body information in my account
    public TextField myAccountFattyField;//this field is about body information in my account
    public FlowPane mainPageFlowPane;//this plan hold classes in main page
    public TextArea mainPageNoticeTextArea;//this field provide notice on main page
    public FlowPane myClassFlowPane;//this plan hold classes in my class
    public TextArea mainPageOverviewText;
    public TextArea myClassOverviewText;
    public ChoiceBox mainPageFilterType;
    public Button mainPageSearchButton;
    public RadioButton mainPageClassButton;
    public RadioButton mainPageLiveButton;
    public Button myClassSearchButton;
    public ChoiceBox myClassFilterType;
    public RadioButton myClassClassButton;
    public RadioButton myClassLiveButton;
    final ToggleGroup group1 = new ToggleGroup();
    final ToggleGroup group2 = new ToggleGroup();
    public Label Normal;
    public Label FreeFor;
    public Label PayFor;
    public Label Discount;
    public Label Extra;
    public Label All;
    public Label Premier;
    public Label Off;
    public Label discountRatio;
    public Button buy;
    public ComboBox monthChoiceBox;
    public Label premierDiscountPriceLabel;
    public Label premierOriginalPriceLabel;
    public Label myAccountUserNameLabel;

    public Model.Client client;
    //public String id; no need --PZ


    // public Label premiumLabel;
    @FXML
    public void initialize() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        for(int i=8;i<=100;i++)
            myAccountAgeField.getItems().add(i);
        myAccountAgeField.setValue(20);

        for(String s : Policy.sport_type){
            mainPageFilterType.getItems().add(s);
            myClassFilterType.getItems().add(s);
        }
        mainPageFilterType.setValue(mainPageFilterType.getItems().get(0));
        myClassFilterType.setValue(myClassFilterType.getItems().get(0));


        mainPageClassButton.setToggleGroup(group1);
        mainPageLiveButton.setToggleGroup(group1);
        mainPageLiveButton.setUserData("live");
        mainPageClassButton.setUserData("class");
        myClassClassButton.setToggleGroup(group2);
        myClassLiveButton.setToggleGroup(group2);
        myClassLiveButton.setUserData("Live");
        myClassClassButton.setUserData("class");
        group1.selectToggle(mainPageClassButton);
        group2.selectToggle(myClassClassButton);


        updateNotice();

        discountRatio.setText(""+Policy.premium_discount*100+"%");
        for(int i=1;i<=12;i++)
            monthChoiceBox.getItems().add(i);
    }

    public void buildScene() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        myAccountAgeField.setValue(client.getAge());
        myAccountWeightField.setText(""+client.getWeight());
        myAccountHeightField.setText(""+client.getHeight());
        myAccountBMIField.setText(""+client.getBMI());
        myAccountFattyField.setText(""+client.getBody_fat_rate());
        updateClassesInMainPage();

        updateClassesInMyClass();
        myAccountUserNameLabel.setText(client.getName());
        premiumLabel.setText(client.getRank()==0?"Normal":"Premium");
    }

    public void updateClassesInMainPage() throws IOException, SAXException, ParserConfigurationException {
        mainPageFlowPane.getChildren().clear();
        ArrayList<Button> buttons;
        if(group1.getSelectedToggle().getUserData().equals("class"))
            buttons = getClassesButtonsForMainPage();
        else
            buttons = getLiveButtonsForMainPage();
        for(Button button:buttons)
            mainPageFlowPane.getChildren().add(button);
    }

    public void updateClassesInMyClass() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        myClassFlowPane.getChildren().clear();
        ArrayList<Button> buttons;
        if(group2.getSelectedToggle().getUserData().equals("class"))
            buttons = getClassesButtonsForMyClass();
        else
            buttons = getLiveButtonsForMyClass();
        for(Button button:buttons)
            myClassFlowPane.getChildren().add(button);
    }

    EventHandler<ActionEvent> classButtonClicked = new EventHandler<ActionEvent>() {
        /**
         * this function change to the course page according to the class button clicked.
         * @param actionEvent
         */
        @Override
        public void handle(ActionEvent actionEvent) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ClassScene.fxml"));
            Parent classSceneParent = null;

            try {
                classSceneParent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene classScene = new Scene(classSceneParent);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(classScene);


            ClassSceneController controller = loader.getController();
            Course course = (Course)(((Node) actionEvent.getSource()).getUserData());
            controller.setCourse(course);
            controller.setClient(client);
            controller.previousScene = ((Node)actionEvent.getSource()).getScene();
            try {
                controller.buildScene();//build course scene dynamically according to the course information
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    EventHandler<ActionEvent> liveButtonClieked = new EventHandler<ActionEvent>() {
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
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            LiveSceneController controller = loader.getController();
            controller.live = (Live) (((Node)actionEvent.getSource()).getUserData());
            controller.previousScene = ((Node)actionEvent.getSource()).getScene();
            controller.setClient(client);
            window.setScene(classScene);
            try {
                controller.buildScene();//build course scene dynamically according to the course information
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };


    /**
     * This method return a set of class buttons for Main pages.
     * details needed to be added --PZ
     * details added at 4.9 1412 --PZ
     * @return
     */
    public ArrayList<Button> getClassesButtonsForMainPage() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Button> buttons =new ArrayList<Button>();
        ArrayList <Course> classes = Control.getAllCourses();
        for(Course course :classes){
            Button button = new Button();
            button.setPrefSize(160,160);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(classButtonClicked);

            //add action on class button to show over view
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            mainPageOverviewText.setText(((Course)button.getUserData()).getInfo());;
                        }
                    });


            
            button.setUserData(course);//add course object to object
            button.setText("Trainner: "+course.getTrainer()+"\n"+course.getInfo());
            buttons.add(button);

        }
        return buttons;
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
        ArrayList <Course> classes = Control.getClientCourses(client);
        //System.out.println(classes.size());
        for(Course course :classes){
            Button button = new Button();
            button.setPrefSize(160,160);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(classButtonClicked);

            //add action on class button to show over view
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            myClassOverviewText.setText(((Course)button.getUserData()).getInfo());;
                        }
                    });



            button.setUserData(course);//add course object to object
            button.setText("Trainner: "+course.getTrainer()+"\n"+course.getInfo());
            buttons.add(button);

        }
        return buttons;
    }
    /**
     * This method return a set of live buttons for MyClass.
     * details needed to be added --PZ
     * @return
     */
    public ArrayList<Button> getLiveButtonsForMyClass() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

            ArrayList<Button> buttons =new ArrayList<Button>();
            Control controller = new Control();
            ArrayList <Live> lives = controller.getClientLives(client);
            for(Live live : lives){
                Button button = new Button();
                button.setPrefSize(160,160);
                //mainPageFlowPane.getChildren().add(button);
                button.setOnAction(liveButtonClieked);
                button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                        new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                myClassOverviewText.setText(((Live)button.getUserData()).getInfo());

                            }
                        });


                button.setUserData(live);
                button.setText("Trainner: "+live.getTrainer()+"\n"+live.getInfo());
                buttons.add(button);

            }
            return buttons;

    }
    /**
     * This method return a set of live buttons for Main pages.
     * details needed to be added --PZ
     * added at 4.9 --PZ
     * @return
     */
    public ArrayList<Button> getLiveButtonsForMainPage() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Button> buttons =new ArrayList<Button>();

        ArrayList <Live> lives = Control.getAllLives();
        for(Live live : lives){
            Button button = new Button();
            button.setPrefSize(160,160);
            //mainPageFlowPane.getChildren().add(button);
            button.setOnAction(liveButtonClieked);
            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            mainPageOverviewText.setText(((Live)button.getUserData()).getInfo());;
                        }
                    });


            button.setUserData(live);
            button.setText("Trainner: "+live.getTrainer()+"\n"+live.getInfo());
            buttons.add(button);

        }
        return buttons;
    }


    public void updateNotice() {
        String s = new String();
        for(int i=0;i<=100;i++)
            s+=("Line"+i+"\n");
        mainPageNoticeTextArea.setText(s);
    }

    public void changeEmailButtonCliecked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChangeEmailScene.fxml"));
        Parent changeEmailParent = loader.load();
        Scene changeEmailScene = new Scene(changeEmailParent);

        stage.setScene(changeEmailScene);
        ChangeEmailScene controller = loader.getController();
        controller.client = client;

        stage.show();
    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChangePassword.fxml"));
        Parent changePassWordParent = loader.load();
        Scene changePassWordScene = new Scene(changePassWordParent);

        stage.setScene(changePassWordScene);

        stage.show();
    }

    /**
     * this method save changes of client's body information's changes, and generate generic plan
     * @param actionEvent
     */
    public void myAccountSaveButtonClicked(ActionEvent actionEvent) {

    }

    /**
     * this method get what lessons and types user choose, and show them. It's in MainPage.
     * @param actionEvent
     */
    public void mainPageSearchClicked(ActionEvent actionEvent) throws ParserConfigurationException, SAXException, IOException {
        updateClassesInMainPage();
    }


    /**
     * this method get what lessons and types user choose, and show them. It's in MyClasses and Live.
     * @param actionEvent
     */
    public void myClassSearchClicked(ActionEvent actionEvent) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
        updateClassesInMyClass();
    }

    public void premierBuyClicked(ActionEvent actionEvent) throws IOException {
        Integer month = (Integer) (monthChoiceBox.getValue());
        Control.addPremiumToClient(client.getPhone_number(),month);

    }
    public void premierMonthSelected() throws IOException {
        Integer month = (Integer) (monthChoiceBox.getValue());
        //System.out.println(month);
        double originPrice = month * 50;
        double discountPrice = month * 50 * (1-0.1);
        premierOriginalPriceLabel.setText(originPrice+" $ ");
        premierDiscountPriceLabel.setText(discountPrice+" $ ");
    }










}
