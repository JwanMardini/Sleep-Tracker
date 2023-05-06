import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Label about_label;

    @FXML
    private Text about_text;
    @FXML
    private Label recommend_label;

    @FXML
    private Text recommend_text;

    @FXML
    private ChoiceBox<String> mychoiceBox;
    private String[] options = {"About App","Recommendation"};

    @FXML
    private Button btn_logout;

    @FXML
    private Label label_welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mychoiceBox.setValue("Home");
        mychoiceBox.getItems().addAll(options);

        mychoiceBox.setOnAction(this::getOption);
    }
    public void getOption(ActionEvent event) {

        String myOption = mychoiceBox.getValue();

        if (myOption.equals("About App")) {
            about_label.setVisible(true);
            about_text.setVisible(true);
            recommend_label.setVisible(false);
            recommend_text.setVisible(false);
        } else if (myOption.equals("Recommendation")) {
            about_label.setVisible(false);
            about_text.setVisible(false);
            recommend_label.setVisible(true);
            recommend_text.setVisible(true);
        }else {
            about_label.setVisible(false);
            about_text.setVisible(false);
            recommend_label.setVisible(false);
            recommend_text.setVisible(false);}
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
