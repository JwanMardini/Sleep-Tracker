import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public Hyperlink forgotPassword;

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

        forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "resources/resetPassword.fxml", "Reset Password", null);
            }
        });



    }
}