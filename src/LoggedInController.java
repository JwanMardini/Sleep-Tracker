import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;

    @FXML
    private Label label_welcome;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private  Alert alert;
    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)) {
                Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();

                btn_logout.getScene().getWindow().hide();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUserInformation(String username){
        label_welcome.setText("Welcome  " + username + "!");
    }
}
