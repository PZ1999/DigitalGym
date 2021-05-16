import Model.Client;
import Model.IO;
import Model.Trainer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/web/icon1.jpg"));
        scene.getStylesheets().add
                (Main.class.getResource("/web/login.css").toExternalForm());
        primaryStage.show();
    }
    //
    public static void main(String[] args) throws IOException {
        //IO.main(args);
        launch(args);
    }
}
/**
 * 布局被弄乱了，sorry
 * pane 的透明度
 * 文本框鼠标消失
 * manager button
 */