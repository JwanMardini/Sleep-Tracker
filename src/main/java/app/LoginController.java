package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //Login
    @FXML
    private AnchorPane login_form;
    @FXML
    private AnchorPane login_left_form;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_sign_up;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_passwordShow;
    @FXML
    private Hyperlink forgot_password;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private AnchorPane forgotPass_form;
    @FXML
    private AnchorPane pass_left_form;
    @FXML
    private Button back_btn;
    @FXML
    private TextField secQue;
    @FXML
    private TextField tf_username_forgot;
    @FXML
    private AnchorPane resetPass_form;
    @FXML
    private PasswordField tf_confResetPass;
    @FXML
    private PasswordField tf_resetPass;

    private Alert alert;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    DBUtils errorMessage = new DBUtils();

    public void switchForm(ActionEvent event) {

        if (event.getSource() == forgot_password) {
            login_form.setVisible(false);
            login_left_form.setVisible(false);
            forgotPass_form.setVisible(true);
            pass_left_form.setVisible(true);
            resetPass_form.setVisible(false);

        }   else if (event.getSource() == back_btn) {
            login_form.setVisible(true);
            login_left_form.setVisible(true);
            forgotPass_form.setVisible(false);
            pass_left_form.setVisible(false);
            resetPass_form.setVisible(false);
        }
    }

    public void forgotPassword(ActionEvent actionEvent) {

        if (tf_username_forgot.getText().isEmpty() || secQue.getText().isEmpty()) {
            errorMessage.showMessageAlert("Blank fields",null, "Please fill all blank fields");

        } else {

            String checkData = "SELECT username, secQue FROM users "
                    + "WHERE username = ? AND secQue = ?";
            connect = DBUtils.getConnection();

            try {

                prepare = connect.prepareStatement(checkData);
                prepare.setString(1, tf_username_forgot.getText());
                prepare.setString(2, secQue.getText());
                result = prepare.executeQuery();
                // IF CORRECT
                if (result.next()) {
                    // PROCEED TO CHANGE PASSWORD
                    login_form.setVisible(false);
                    login_left_form.setVisible(false);
                    forgotPass_form.setVisible(false);
                    pass_left_form.setVisible(true);
                    resetPass_form.setVisible(true);

                } else {
                    errorMessage.showErrorAlert(null,null,"Incorrect information");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            tf_passwordShow.setText(tf_password.getText());
            tf_passwordShow.setVisible(true);
            tf_password.setVisible(false);
        } else {
            tf_password.setText(tf_passwordShow.getText());
            tf_passwordShow.setVisible(false);
            tf_password.setVisible(true);
        }
        tf_password.setDisable(showPasswordCheckBox.isSelected());
    }

    public void resetPassword(ActionEvent actionEvent){

        // CHECK ALL FIELDS IF EMPTY OR NOT
        if(tf_resetPass.getText().isEmpty() || tf_confResetPass.getText().isEmpty()){

            errorMessage.showErrorAlert("Blank fields",null, "Please fill all blank fields");

        }else if(!tf_resetPass.getText().equals(tf_confResetPass.getText())){
            // CHECK IF THE PASSWORD AND CONFIRMATION ARE NOT MATCH
            errorMessage.showErrorAlert("Error", null, "Password does not match");

        }else{
            // USERNAME IS OUR REFERENCE TO UPDATE THE DATA OF THE USER
            String updateData = "UPDATE users SET password = ? WHERE username = ?";
            connect = DBUtils.getConnection();
            try{
                PreparedStatement prepare = connect.prepareStatement(updateData);
                prepare.setString(1, tf_resetPass.getText());
                prepare.setString(2, tf_username_forgot.getText());
                int rowsUpdated = prepare.executeUpdate();
                if (rowsUpdated > 0) {
                    errorMessage.showMessageAlert("Done!",null, "Successfully changed Password");

                    // LOGIN FORM WILL APPEAR
                    login_form.setVisible(true);
                    login_left_form.setVisible(true);
                    forgotPass_form.setVisible(false);
                    pass_left_form.setVisible(false);
                    resetPass_form.setVisible(false);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
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
                DBUtils.changeScene(actionEvent, "/signup.fxml", "Sign up!", null);
            }
        }
        );

        showPasswordCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                togglePasswordVisibility();
            }
        });
    }
}