package ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Payment {
    public Button confirm;
    public Label priceLabel;
    public Label itemLabel;
    public Label payment;


    public void buildScene(String item,String price){
        itemLabel.setText(item);
        priceLabel.setText(price);
    }


    public void confirm(ActionEvent actionEvent) {
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
