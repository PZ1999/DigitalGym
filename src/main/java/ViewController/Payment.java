package ViewController;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Payment {
    public Button confirm;
    public Label itemLabel;
    public Label payment;
    public Label originPriceLabel;
    public Label discountPriceLabel;
    public String itemType;//"Live","Course","Premium"
    public Client client;
    public Live live;
    public Course course;
    public double origin_price;
    public double discount_price;
    public int premium_month;
    public ClientMainSceneController mainSceneController;

    /**
     * this method will do the payment logic including price decision.
     */
    public void buildScene() throws IOException {
        switch (itemType){
            case "Live":{
                itemLabel.setText(live.getName());
                origin_price = live.getPrice();
                originPriceLabel.setText(""+origin_price+" $");
                if(client.getRank()>0){//premium which lead to discount
                    Policy policy = (Policy)IO.read(new Policy(),"Policy");
                    discount_price = origin_price*(1.0-policy.live_discount);
                    discountPriceLabel.setText(""+discount_price+" $");
                }
                else discount_price = origin_price;
                break;
            }
            case "Course":{
                itemLabel.setText(course.getName());
                origin_price = course.getPrice();
                originPriceLabel.setText(""+origin_price+" $");
                if(client.getRank()>=course.getRank()){//premium which lead to free subscribe
                    discount_price = 0;
                    discountPriceLabel.setText(""+discount_price+" $");
                }
                else discount_price = origin_price;
                break;
            }
            case "Premium":{
                itemLabel.setText("Premium Access for"+premium_month+" month");
                Policy policy = (Policy) IO.read(new Policy(),"Policy");
                origin_price = policy.premium_price*premium_month;
                discount_price = origin_price*(1-policy.premium_discount);
                originPriceLabel.setText(""+origin_price+" $");
                discountPriceLabel.setText(""+discount_price+" $");
                break;
            }
            default:{
                System.out.println("out of switch coverage");
            }
        }
    }



    public void confirm(ActionEvent actionEvent) throws Exception {
        switch (itemType){
            case "Live":{
                Control.addLiveToClient(client.getPhone_number(),live.getCourse_id());
                break;
            }
            case "Course":{
                Control.addCourseToClient(client.getPhone_number(),course.getCourse_id());
                break;
            }
            case "Premium":{
                Control.addPremiumToClient(client.getPhone_number(),premium_month);
                mainSceneController.buildScene();
                break;
            }
            default:{
                System.out.println("out of switch coverage");
            }
        }
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();

    }
}
