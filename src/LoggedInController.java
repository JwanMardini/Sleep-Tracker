import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import java.io.IOException;


public class LoggedInController {

     @FXML
    private MenuButton btn_history;
    @FXML
    private MenuButton btn_home;
    @FXML
    private MenuButton btn_profile;
    @FXML
    private MenuButton btn_record_sleep;

    @FXML
    private Label label_welcome;

    private String userInfo;


    @FXML
    void btn1MonthClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/month1.fxml");
    }
    @FXML
    void btn1WeekClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/week1.fxml");
    }
    @FXML
    void btn2WeeksClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/weeks2.fxml");
    }
    @FXML
    void btnAboutClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/about.fxml");
    }
    @FXML
    void btnEditProfileClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/editProfile.fxml");
    }
    @FXML
    void btnGeneralRecommendationsClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/generalRecommendations.fxml");
    }
    @FXML
    void btnHistoryClicked(ActionEvent event) {
        btn_history.show();
    }
    @FXML
    void btnHomeClicked(ActionEvent event) {
        btn_home.show();
    }
    @FXML
    void btnLogOutClicked(ActionEvent event) throws IOException {
        Main.changeScene("resources/login.fxml");
    }
    @FXML
    void btnProfileClicked(ActionEvent event) {
        btn_profile.show();
    }
    @FXML
    void btnRecordSleepClicked(ActionEvent event) {
        btn_record_sleep.show();
    }
    @FXML
    void btnRecordSleepTimeClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/recordSleepTime.fxml");
    }
    @FXML
    void btnResourceClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/resources.fxml");

    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
        label_welcome.setText(label_welcome.getText() +" " + userInfo);
    }
}


