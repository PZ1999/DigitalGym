import Model.Client;
import Model.IO;
import Model.Trainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static Model.IO.read;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
        primaryStage.setTitle("iGym");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    //
    public static void main(String[] args) throws IOException {
        //IO.main(args);
        launch(args);


    }
}
