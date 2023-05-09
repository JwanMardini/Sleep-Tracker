import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;


public class EditProfileController {

    @FXML
    private Button btn_back_to_home;

    @FXML
    private Button saveButton;

    @FXML
    private TextField nameID;

    @FXML
    private TextField usernameID;

    @FXML
    private TextField emailID;



    @FXML
    void btnBackToHome(ActionEvent event) throws IOException {
        Main.changeScene("resources/logged-in.fxml");

    }



}
