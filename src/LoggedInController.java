import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class LoggedInController {

    // Main page
    @FXML
    private MenuButton btn_home;

    @FXML
    private MenuButton btn_record_sleep;

    @FXML
    private Button btn_history;

    @FXML
    private MenuButton btn_profile;

    @FXML
    private Label label_welcome;


    //Home -> About controller
    @FXML
    private Button btn_back_to_home1;


    //Profile controller
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
    @FXML
    private TextField username;

    @FXML
    private DatePicker start_date;

    @FXML
    private TextField start_time;

    @FXML
    private DatePicker end_date;

    @FXML
    private TextField end_time;

    @FXML
    private Label sleepDurationLabel;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_back_to_home;

    private String userInfo;

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
        DBUtils.changeScene(event, "resources/history.fxml", "History", userInfo);
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

    @FXML
    public void handleSaveButton(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sleeptrackerlogin", "root", "toor");
             PreparedStatement psGetUserId = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
             PreparedStatement psInsertDateTime = connection.prepareStatement("INSERT INTO DateTime(start_date, start_time, end_date, end_time, duration, user_id) VALUES (?, ?, ?, ?, ?, ?)")) {

            // Get the user ID for the logged-in user from the Users table
            psGetUserId.setString(1, userInfo);
            try (ResultSet rs = psGetUserId.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");

                    // Calculate duration
                    LocalDateTime startTime = LocalDateTime.of(start_date.getValue(), LocalTime.parse(start_time.getText()));
                    LocalDateTime endTime = LocalDateTime.of(end_date.getValue(), LocalTime.parse(end_time.getText()));
                    Duration duration = Duration.between(startTime, endTime);

                    // Insert a new row into the DateTime table with the date, time, duration, and user ID
                    psInsertDateTime.setDate(1, java.sql.Date.valueOf(start_date.getValue()));
                    psInsertDateTime.setTime(2, java.sql.Time.valueOf(start_time.getText()));
                    psInsertDateTime.setDate(3, java.sql.Date.valueOf(end_date.getValue()));
                    psInsertDateTime.setTime(4, java.sql.Time.valueOf(end_time.getText()));
                    psInsertDateTime.setLong(5, duration.toMinutes());
                    psInsertDateTime.setInt(6, userId);
                    psInsertDateTime.executeUpdate();

                    // Display duration in label
                    sleepDurationLabel.setText("Duration: " + duration.toHours() + " hours " + (duration.toMinutes() % 60) + " minutes");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


