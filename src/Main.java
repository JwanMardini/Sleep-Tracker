
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;



public class Main extends Application {
    private static Stage stg;  //creating fake stage b/c we want the variables to change scenes
    @Override
    public void start(Stage primaryStage) throws Exception {

        stg = primaryStage; // the addition
        primaryStage.setResizable(false); //the addition

        Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
        primaryStage.setTitle("Sleep Tracker!");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void changeScene(String fxml, String username) throws IOException{
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxml));
        Parent pane = loader.load();
        LoggedInController loggedInController = loader.getController();
        loggedInController.setUserInfo(username);
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}