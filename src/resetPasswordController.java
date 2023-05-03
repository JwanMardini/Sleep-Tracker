import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

public class resetPasswordController implements Initializable{
    @FXML
    private TextField tf_email;

    @FXML
    public Label l_email;
    @FXML
    public Label l_label;

    @FXML
    private TextField tf_token;


    @FXML
    private Label l_token;

    @FXML
    private Label l_token2;

    @FXML
    private Button btn_submit;

    @FXML
    private Button btn_send;
    @FXML
    public Button back_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!DBUtils.checkEmail(tf_email.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The provided credentials are incorrect!");
                    alert.show();
                }else{
                    tf_email.setVisible(false);
                    l_label.setVisible(false);
                    btn_send.setVisible(false);
                    l_email.setVisible(false);

                    l_token.setVisible(true);
                    tf_token.setVisible(true);
                    btn_submit.setVisible(true);
                    l_token2.setVisible(true);
                }
            }
        });

        btn_submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String token = null;
                try {
                    token = DBUtils.sendToken(actionEvent, tf_email.getText());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                if (!tf_token.getText().trim().equals(token)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Wrong token");
                    alert.show();
                }else{
                    DBUtils.changeScene(actionEvent, "resources/newPassword.fxml", null, tf_email.getText());
                }

            }
        });

        back_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "resources/login.fxml", null, null);
            }
        });

    }
}
