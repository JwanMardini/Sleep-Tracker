import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

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

    //About controller
    @FXML
    private Button btn_back_to_home1;



    //Edit profile controller
    @FXML
    private Button btn_back_to_home2;
    @FXML
    private Button saveButton;
    @FXML
    private TextField nameID;
    @FXML
    private TextField usernameID;
    @FXML
    private TextField emailID;

    // General recommendation controller
    @FXML
    private Button btn_back_to_home3;

    // month1 controller
    @FXML
    private Button btn_back_to_home4;

    // Resources controller
    @FXML
    private Button btn_back_to_home5;

    // week 1 Controller
    @FXML
    private Button btn_back_to_home6;

    // week 2 controller
    @FXML
    private Button btn_back_to_home7;


    //Record sleep timer


    private String userInfo;


    @FXML
    void btn1MonthClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/month1.fxml", userInfo);
    }
    @FXML
    void btn1WeekClicked(ActionEvent event) throws IOException{
        Main.changeScene( "resources/week1.fxml", userInfo);
    }
    @FXML
    void btn2WeeksClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/weeks2.fxml", userInfo);
    }
    @FXML
    void btnAboutClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/about.fxml", userInfo);
    }
    @FXML
    void btnEditProfileClicked(ActionEvent event) throws IOException{
        Main.changeScene( "resources/editProfile.fxml",  userInfo);
    }
    @FXML
    void btnGeneralRecommendationsClicked(ActionEvent event) throws IOException{
        Main.changeScene( "resources/generalRecommendations.fxml", userInfo);
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
        DBUtils.changeScene(event, "resources/login.fxml", "log in", null);
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
        Main.changeScene("resources/recordSleepTime.fxml", userInfo);
    }
    @FXML
    void btnResourceClicked(ActionEvent event) throws IOException{
        Main.changeScene("resources/resources.fxml", userInfo);

    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
        if (label_welcome != null) {
            label_welcome.setText("Welcome " + userInfo);
        }
    }

    @FXML
    void btnBackToHome(ActionEvent event) throws IOException {
        DBUtils.changeScene(event, "resources/logged-in.fxml", "Home", userInfo);

    }
}


