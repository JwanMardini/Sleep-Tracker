import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;


public class ResourcesController {

    @FXML
    private Button btn_back_to_home;

    @FXML
    void btnBackToHome(ActionEvent event) throws IOException {
        Main.changeScene("resources/logged-in.fxml");

    }

}
