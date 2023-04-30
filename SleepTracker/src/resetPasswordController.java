import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class resetPasswordController implements Initializable{
    @FXML
    private TextField tf_email;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String token = DBUtils.sendToken(event, tf_email.getText());
                if(DBUtils.checkToken(token)){
                    DBUtils.changeScene(event, "newPassword.fxml", "new password", null);
                }
            }
        });

    }
}
