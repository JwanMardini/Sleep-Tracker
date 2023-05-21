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

    DBUtils errorMessage = new DBUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoggedInController errorAlert = new LoggedInController();

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                // convert the input to an integer
              try {
                  String ageString = tf_age.getText();
                  int age = Integer.parseInt(ageString);

                  if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !secQue.getText().trim().isEmpty() && !tf_age.getText().trim().isEmpty() && isPasswordValid(tf_password.getText()) && isEmailValid(tf_email.getText())) {
                      DBUtils.signUpUser(actionEvent, tf_username.getText(), tf_password.getText(), tf_email.getText(), secQue.getText(), age);

                  }else if(tf_username.getText().trim().isEmpty() || tf_password.getText().trim().isEmpty() || tf_email.getText().trim().isEmpty() || secQue.getText().trim().isEmpty() || tf_age.getText().trim().isEmpty()){
                      errorMessage.showErrorAlert("Error", null, "Please fill in all information to sign up!");

                  }else if (!isPasswordValid(tf_password.getText())){
                      errorMessage.showErrorAlert("Invalid Password", null, "Invalid password. Please make sure your password is at least 8 characters long and contains at least one digit.");

                  } else if (!isEmailValid(tf_email.getText())) {
                      errorMessage.showErrorAlert("Invalid Password", null, "Invalid email address. Please enter a valid email address.");
                  }
              } catch (NumberFormatException e) {
                  // handle the exception by displaying an error message
                  errorMessage.showErrorAlert("Error!", null, "Please enter valid entry.");
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

    public boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

}
