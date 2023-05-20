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
    Button btn_signup;

    @FXML
    Button btn_log_in;

    @FXML
    TextField tf_username;

    @FXML
    TextField tf_email;

    @FXML
    TextField tf_password;

    @FXML
    TextField tf_age;


    @FXML
    TextField secQue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                // convert the input to an integer
              try {
                  String ageString = tf_age.getText();
                  int age = Integer.parseInt(ageString);

                  if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !secQue.getText().trim().isEmpty() && !tf_age.getText().trim().isEmpty() && isPasswordValid(tf_password.getText())) {
                      DBUtils.signUpUser(actionEvent, tf_username.getText(), tf_password.getText(), tf_email.getText(), secQue.getText(), age);

                  }else if(!isPasswordValid(tf_password.getText())){
                      alert.setTitle("Invalid Password");
                      alert.setHeaderText(null);
                      alert.setContentText("Invalid password. Please make sure your password is at least 8 characters long and contains at least one digit.");
                      alert.show();
                  }else {
                      System.out.println("Please fill in all information");
                      alert.setContentText("Please fill in all information to sign up!");
                      alert.show();
                  }
              } catch (NumberFormatException e) {
                  // handle the exception by displaying an error message
                  alert.setContentText("Please enter a valid age.");
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

    public boolean isPasswordValid(String password) {
        if (password.length() >= 8 && password.matches(".*\\d.*")) {
            return true;
        }
        return false;
    }
}
