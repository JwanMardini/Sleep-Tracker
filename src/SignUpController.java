import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_log_in;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_age;


    @FXML
    private TextField secQue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // convert the input to an integer
              try {
                  String ageString = tf_age.getText();
                  int age = Integer.parseInt(ageString);


                  if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !secQue.getText().trim().isEmpty() && tf_age.getText().isEmpty()) {
                      DBUtils.signUpUser(actionEvent, tf_username.getText(), tf_password.getText(), tf_email.getText(), secQue.getText(), age);
                  } else {
                      System.out.println("Please fill in all information");
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setContentText("Please fill in all information to sign up!");
                      alert.show();
                  }
              } catch (NumberFormatException e) {
                  // handle the exception by displaying an error message
                  Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid age.");
                  alert.showAndWait();
              }

            }
        });
        btn_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "resources/login.fxml", "Log in!", null);
            }
        });
    }
}
