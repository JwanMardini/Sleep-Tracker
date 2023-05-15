import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    //Login
    @FXML
    AnchorPane login_form;
    @FXML
    AnchorPane login_left_form;
    @FXML
    Button btn_login;
    @FXML
    Button btn_sign_up;
    @FXML
    TextField tf_username;
    @FXML
    TextField tf_password;
    @FXML
    private Hyperlink forgot_password;
    @FXML
    CheckBox showPasswordCheckBox;


    //Forgot Password
    @FXML
    AnchorPane forgotPass_form;
    @FXML
    AnchorPane pass_left_form;
    @FXML
    Button back_btn;
    @FXML
    Button forgotPass_send_btn;
    @FXML
    private TextField secQue;
    @FXML
    private TextField tf_username_forgot;


    //Reset Password
    @FXML
    AnchorPane resetPass_form;
    @FXML
    Button resetPass_btn;
    @FXML
    private PasswordField tf_confResetPass;
    @FXML
    private PasswordField tf_resetPass;

    public void switchForm(ActionEvent event) {

        if (event.getSource() == forgot_password) {
            login_form.setVisible(false);
            login_left_form.setVisible(false);
            forgotPass_form.setVisible(true);
            pass_left_form.setVisible(true);
            resetPass_form.setVisible(false);

        } else if (event.getSource() == forgotPass_send_btn) {
            login_form.setVisible(false);
            login_left_form.setVisible(false);
            forgotPass_form.setVisible(false);
            pass_left_form.setVisible(true);
            resetPass_form.setVisible(true);

        } else if (event.getSource() == resetPass_btn) {
            login_form.setVisible(true);
            login_left_form.setVisible(true);
            forgotPass_form.setVisible(false);
            pass_left_form.setVisible(false);
            resetPass_form.setVisible(false);

        } else if (event.getSource() == back_btn) {
            login_form.setVisible(true);
            login_left_form.setVisible(true);
            forgotPass_form.setVisible(false);
            pass_left_form.setVisible(false);
            resetPass_form.setVisible(false);
        }
    }

    @FXML
    void togglePasswordVisibility() {
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

    public void handleLoginButtonAction() {
    }
}