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
import javafx.scene.layout.VBox;
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
    public ComboBox myAccountAgeField;//this field is about body information in my account
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
    public ClientMainSceneController local_controller;

    public Model.Client client;
    public TextArea myAccountShowPlanTextArea;
    public TextField twistlineTextField;
    public Label Overview;
    public VBox OverviewVbox;
    //public String id; no need --PZ


    // public Label premiumLabel;
    @FXML
    public void initialize() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        for(int i=8;i<=100;i++)
            myAccountAgeField.getItems().add(i);
        myAccountAgeField.setVisibleRowCount(10);
        myAccountAgeField.getSelectionModel().select(20);

        mainPageFilterType.getItems().add("All");
        myClassFilterType.getItems().add("All");
        mainPageFilterType.getItems().add("Discount");
        Policy policy = (Policy) IO.read(new Policy(),"Policy");
        for(String s : policy.sport_type){
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

        discountRatio.setText(""+policy.premium_discount*100+"%");
        for(int i=1;i<=12;i++)
            monthChoiceBox.getItems().add(i);
    }

    public void buildScene() throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {

        local_controller = (ClientMainSceneController)buy.getScene().getUserData();
        client = (Client)IO.read(client,client.getPhone_number());
        //System.out.println(client.getRank());
        //client.getRank();
        myAccountAgeField.setValue(client.getAge());
        myAccountWeightField.setText(""+client.getWeight());
        myAccountHeightField.setText(""+client.getHeight());
        twistlineTextField.setText(""+client.getWaistline());
        myAccountBMIField.setText(""+client.getBMI());
        myAccountFattyField.setText(""+client.getBody_fat_rate());

        updateClassesInMainPage();
        updateClassesInMyClass();

        myAccountUserNameLabel.setText(client.getName());
        premiumLabel.setText(client.getRank()==0?"Normal":"Premium");
        Policy policy = (Policy)IO.read(new Policy(),"Policy");
        System.out.println(policy.premium_discount);
        discountRatio.setText(policy.premium_discount*100+"%");
        myAccountSaveButtonClicked(new ActionEvent());
        myAccountShowPlanTextArea.setText(client.getGeneric_plan());

    }

    public void updateClassesInMainPage() throws IOException, SAXException, ParserConfigurationException {
        mainPageFlowPane.getChildren().clear();
        ArrayList<Button> buttons;
        if(group1.getSelectedToggle().getUserData().equals("class"))
            buttons = getClassesButtonsForMainPage();//with filter --PZ
        else
            buttons = getLiveButtonsForMainPage();
        for(Button button:buttons)
            mainPageFlowPane.getChildren().add(button);
    }

    public void updateClassesInMyClass() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        myClassFlowPane.getChildren().clear();
        ArrayList<Button> buttons;
        if(group2.getSelectedToggle().getUserData().equals("class")){
            System.out.println("class");
            buttons = getClassesButtonsForMyClass();
        }

        else{
            System.out.println("live");
            buttons = getLiveButtonsForMyClass();
        }

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
            classScene.getStylesheets().add
                    (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
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
            classScene.setUserData(controller);
            window.setScene(classScene);
            classScene.getStylesheets().add
                    (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
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
     * a filter added to check availability --PZ
     * @return
     *
     */
    public ArrayList<Button> getClassesButtonsForMainPage() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Button> buttons =new ArrayList<Button>();
        ArrayList <Course> classes = Control.getAllCourses(mainPageFilterType.getValue().toString(),client.getPhone_number());

            for(Course course:classes){
                if(!course.getState().equals("alive"))
                    continue ;//not available
                Button button = new Button();
                button.setPrefSize(170,50);
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
                button.setText("Trainner: "+course.getTrainer()+"\n"+course.getName());
                buttons.add(button);
            }
        return buttons;
    }

    /**
     * This method return a set of live buttons for Main pages.
     * details needed to be added --PZ
     * added at 4.9 --PZ
     * a filter added to check availability --PZ
     * @return
     *
     */
    public ArrayList<Button> getLiveButtonsForMainPage() throws ParserConfigurationException, SAXException, IOException {
        ArrayList<Button> buttons =new ArrayList<Button>();

        ArrayList <Live> lives = Control.getAllLives(mainPageFilterType.getValue().toString(),client.getPhone_number());
        for(Live live : lives){
            if(!live.getState().equals("alive"))
                continue ; //not available
            Button button = new Button();
            button.setPrefSize(170,50);
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
            button.setText("Trainner: "+live.getTrainer()+"\n"+live.getName());
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
        client = (Client)IO.read(client,client.getPhone_number());
        ArrayList <Course> classes = Control.getClientCourses(client,myClassFilterType.getValue().toString());
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
                            myClassOverviewText.setText(((Course)button.getUserData()).getInfo());;
                        }
                    });
            button.setUserData(course);//add course object to object
            button.setText("Trainner: "+course.getTrainer()+"\n"+course.getName());
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
            client = (Client)IO.read(client,client.getPhone_number());
            ArrayList <Live> lives = controller.getClientLives(client,myClassFilterType.getValue().toString());
            for(Live live : lives){
                Button button = new Button();
                button.setPrefSize(170,50);
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
                button.setText("Trainner: "+live.getTrainer()+"\n"+live.getName());
                buttons.add(button);

            }
            return buttons;

    }

    public void updateNotice() throws IOException {
        String s = new String();
        Policy policy = (Policy)IO.read(new Policy(),"policy");
        s = "Welcome to IGym\n" +
                "Today's Discount:\n" +
                "Premium Discount: "+ policy.premium_discount*100+" % off\n" +
                "Live Session for Premium: "+policy.live_discount*100+" % off\n";
        mainPageNoticeTextArea.setText(s);
    }

    public void changeEmailButtonClicked(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChangeEmailScene.fxml"));
        Parent changeEmailParent = loader.load();
        Scene changeEmailScene = new Scene(changeEmailParent);

        stage.setScene(changeEmailScene);
        ChangeEmailScene controller = loader.getController();
        controller.client = client;
        controller.mainSceneController = (ClientMainSceneController)(((Node)actionEvent.getSource()).getScene()).getUserData();
        changeEmailScene.getStylesheets().add
                (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
        stage.show();


    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ChangePassword.fxml"));
        Parent changePassWordParent = loader.load();
        Scene changePassWordScene = new Scene(changePassWordParent);
        ChangePassword controller = loader.getController();
        controller.client = this.client;
        stage.setScene(changePassWordScene);
        changePassWordScene.getStylesheets().add
                (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
        stage.show();
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

    public void premierBuyClicked(ActionEvent actionEvent) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Payment.fxml"));
        Parent PaymentParent = loader.load();
        Scene PaymentScene = new Scene(PaymentParent);
        stage.setScene(PaymentScene);
        Payment controller = loader.getController();

        controller.itemType = "Premium";
        controller.premium_month = (Integer) (monthChoiceBox.getValue());
        controller.client = client;
        controller.buildScene();
        controller.mainSceneController = local_controller;
        PaymentScene.getStylesheets().add
                (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());

        stage.show();

      //  Integer month = (Integer) (monthChoiceBox.getValue());
        //Control.addPremiumToClient(client.getPhone_number(),month);
        buildScene();

    }

    /**
     * show price of premium
     * @throws IOException
     */
    public void premierMonthSelected() throws IOException {
        Integer month = (Integer) (monthChoiceBox.getValue());
        //System.out.println(month);
        Policy policy = (Policy)IO.read(new Policy(),"Policy");
        double originPrice = month * policy.premium_price;
        double discountPrice = month * policy.premium_price * (1-policy.premium_discount);
        premierOriginalPriceLabel.setText(String.format("%.2f $",originPrice));
        premierDiscountPriceLabel.setText(String.format("%.2f $",discountPrice));

    }
    /**
     * @author WD,WHY
     *
     * this method save changes of client's body information's changes, and generate generic plan
     * modified by PZ at 4.19 to read client IO again.
     * @param actionEvent
     */
    public void myAccountSaveButtonClicked(ActionEvent actionEvent) throws IOException {
        try{Control.updateMyAccountPage(client.getPhone_number(),myAccountAgeField.getValue().toString(),myAccountWeightField.getText(), myAccountHeightField.getText(),twistlineTextField.getText());}
        catch (Exception e){
            return ;
        }
        client = (Client)IO.read(client,client.getPhone_number());

        myAccountBMIField.setText(String.format("%.2f",client.getBMI()));
        myAccountFattyField.setText(String.format("%.2f",client.getBody_fat_rate()));
        myAccountShowPlanTextArea.setText(client.getGeneric_plan());


    }

    public void deleteAccountClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/DeleteAccountScene.fxml"));
        Parent parent = loader.load();
        Scene deleteAccountScene = new Scene(parent);
        DeleteAccountSceneController controller = loader.getController();
        controller.client_id = this.client.getPhone_number();
        controller.stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(deleteAccountScene);
        deleteAccountScene.setUserData(((Node)actionEvent.getSource()).getScene().getWindow());
        deleteAccountScene.getStylesheets().add
                (ClientMainSceneController.class.getResource("/web/clientmainscene.css").toExternalForm());
        stage.show();
    }
}
