import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogIn {
    public LogIn() {

    }

    @FXML
    private Button loginButton;

    @FXML
    private Label wrongLogIn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;



    public void userLogIn(ActionEvent event) throws IOException{
        checkLogin();

    }

    private void checkLogin() throws IOException{
        //Main main = new Main();
        if(username.getText().toString().equals("Jwan") && password.getText().toString().equals("123")){
            wrongLogIn.setText("Success!");
            //main.changeScene("Homepage.fxml");
        } else if (username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter data");

        }else {
            wrongLogIn.setText("Wrong username or password");
        }

    }
}
