import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_sign_up;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private Hyperlink forgot_password;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            tf_password.setPromptText(tf_password.getText());
            tf_password.setText("");
        } else {
            tf_password.setText(tf_password.getPromptText());
            tf_password.setPromptText("Password");
        }
        tf_password.setDisable(showPasswordCheckBox.isSelected());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.logInUser(actionEvent, tf_username.getText(), tf_password.getText());
            }
        }));

        btn_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "resources/signup.fxml", "Sign up!", null);
            }
        });

        /*forgot_password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "resources/resetPassword.fxml", "Reset Password", null  );
            }
        });*/

        showPasswordCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                togglePasswordVisibility();
            }
        });



    }
}