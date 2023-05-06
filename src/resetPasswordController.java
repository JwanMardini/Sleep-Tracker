import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class resetPasswordController implements Initializable{
    @FXML
    private TextField tf_email;

    @FXML
    private Label l_email;

    @FXML
    private Button btn_send;
    @FXML
    private Button back_btn;

    @FXML
    private Label secQueLabel;

    @FXML
    private TextField secQue;

    @FXML
    private Button checkButton;

    @FXML
    private Button updButton;

    @FXML
    private TextField newPass;

    @FXML
    private TextField confNewPass;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!DBUtils.checkEmail(tf_email.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The provided email are incorrect!");
                    alert.show();
                }else{
                    tf_email.setVisible(false);
                    l_email.setVisible(false);
                    btn_send.setVisible(false);

                    secQueLabel.setVisible(true);
                    secQue.setVisible(true);
                    checkButton.setVisible(true);
                }
            }
        });

        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!DBUtils.checkSecQue(secQue.getText().trim(), tf_email.getText().trim())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The provided information are incorrect!");
                    alert.show();
                }else{
                    DBUtils.changeScene(actionEvent, "resources/newPassword.fxml", null, tf_email.getText().trim());
                }
            }
        });

        back_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "resources/login.fxml", null, null);
            }
        });

        updButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!newPass.getText().trim().isEmpty() && confNewPass.getText().trim().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("All fields should be filled.");
                    alert.show();
                } else if (!newPass.getText().trim().equals(confNewPass.getText().trim())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The confirmed password does not match the new password");
                    alert.show();
                }else {
                    DBUtils.updatePassword(tf_email.getText().trim(), newPass.getText().trim());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Password updated");
                    alert.show();
                    DBUtils.changeScene(actionEvent, "resources/login.fxml", "Log in", null);
                }
            }
        });

    }
}
