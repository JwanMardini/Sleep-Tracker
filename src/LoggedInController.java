import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.text.Text;
public class LoggedInController implements Initializable {

    //Home
    @FXML
    private AnchorPane home_form;
    @FXML
    private Button btn_home;


    //Record Sleep
    @FXML
    private AnchorPane record_sleep_form;
    @FXML
    private Button btn_record_sleep;
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


    //History
    @FXML
    private AnchorPane history_form;
    @FXML
    private Button btn_history;
    @FXML
    private BarChart<?, ?> barChart;


    //Profile
    @FXML
    private AnchorPane profile_form;
    @FXML
    private Button btn_profile;
    @FXML
    private Button saveButton;
    @FXML
    private TextField nameID;
    @FXML
    private TextField usernameID;
    @FXML
    private TextField emailID;


    //Recommendation
    @FXML
    private AnchorPane recommendations_form;
    @FXML
    private Button btn_recommend;
    @FXML
    private Text total_text;
    @FXML
    private Text average_text;


    //Logout
    @FXML
    private Button btn_logout;


    //Welcoming
    @FXML
    private Label label_welcome;


    //Other
    @FXML
    private AnchorPane main_form;
    private String userInfo;
    private Alert alert;
    private int userID;




    public void setUserID(String username) {

        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.userID = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
        if (label_welcome != null) {
            label_welcome.setText("Welcome " + userInfo + "!");
        }
    }


    public void handleSaveButton(ActionEvent actionEvent) {
        try {
            try (Connection connection = DBUtils.getConnection();
                 PreparedStatement psGetUserId = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
                 PreparedStatement psInsertDateTime = connection.prepareStatement("INSERT INTO DateTime(start_date, start_time, end_date, end_time, duration, user_id) VALUES (?, ?, ?, ?, ?, ?)")) {

                // Get the user ID for the logged-in user from the Users table
                psGetUserId.setString(1, userInfo);
                try (ResultSet rs = psGetUserId.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        try {
                            // Calculate duration
                            LocalDateTime startTime = LocalDateTime.of(start_date.getValue(), LocalTime.parse(start_time.getText()));
                            LocalDateTime endTime = LocalDateTime.of(end_date.getValue(), LocalTime.parse(end_time.getText()));
                            Duration duration = Duration.between(startTime, endTime);

                            // Check if duration is negative
                            if (duration.isNegative()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("The sleep duration cannot be negative. Please enter valid date/time values.");
                                alert.showAndWait();
                                return; // Exit the method without inserting the new row
                            }

                            // Insert a new row into the DateTime table with the date, time, duration, and user ID
                            psInsertDateTime.setDate(1, java.sql.Date.valueOf(start_date.getValue()));
                            psInsertDateTime.setTime(2, java.sql.Time.valueOf(start_time.getText()));
                            psInsertDateTime.setDate(3, java.sql.Date.valueOf(end_date.getValue()));
                            psInsertDateTime.setTime(4, java.sql.Time.valueOf(end_time.getText()));
                            psInsertDateTime.setLong(5, duration.toHours());
                            psInsertDateTime.setInt(6, userId);
                            psInsertDateTime.executeUpdate();

                            // Display duration in label
                            sleepDurationLabel.setText("Duration: " + duration.toHours() + " hours " + (duration.toMinutes() % 60) + " minutes");
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Saved!");

                            alert.setHeaderText(null);
                            alert.setContentText("Your sleep record has been saved successfully. \nYour sleep duration is: " + duration.toHours() + " hours " + (duration.toMinutes() % 60) + " minutes");
                            alert.showAndWait();

                            // Handle invalid date/time format
                        }catch (DateTimeParseException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter valid date/time values.");
                            alert.showAndWait();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void displayChart() {
        XYChart.Series chartData = new XYChart.Series();
        Connection connection = DBUtils.getConnection();
        try {
            PreparedStatement psChartSql = connection.prepareStatement("SELECT end_date, SUM(duration) FROM DateTime WHERE user_id = ? GROUP BY end_date ORDER BY TIMESTAMP(end_date) ASC LIMIT 8");
            psChartSql.setInt(1, userID);
            ResultSet rs = psChartSql.executeQuery();


            while (rs.next()) {
                chartData.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2))); // 1 is date, 2 is duration

            }
            // TO GET THE DATA FROM THE DATABASE VIA XYCHART
            barChart.getData().add(chartData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void switchForm(ActionEvent event) {
        if (event.getSource() == btn_home) {
            home_form.setVisible(true);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);


        } else if (event.getSource() == btn_record_sleep) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(true);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            setDefaultDateTime();

        } else if (event.getSource() == btn_history) {
            home_form.setVisible(false);
            history_form.setVisible(true);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            displayChart();


        } else if (event.getSource() == btn_profile) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(true);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            usernameID.setText(userInfo);
            emailID.setText(DBUtils.getEmail(userInfo));
        } else if (event.getSource() == btn_recommend) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(true);

            displayRecommendations();

        }
    }


    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM
                btn_logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("resources/login.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Sleep Tracker");

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefaultDateTime () {
        // Set the default value for the date picker
        start_date.setValue(LocalDateTime.now().toLocalDate());

        // Set the default value for the time field
        start_time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // Set the default value for the date picker
        end_date.setValue(LocalDateTime.now().toLocalDate());

        // Set the default value for the time field
        end_time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    public void displayRecommendations() {
        Connection connection = DBUtils.getConnection();
        try {
            PreparedStatement psSleepSql = connection.prepareStatement("SELECT SUM(duration) FROM DateTime WHERE user_id = ?");
            psSleepSql.setInt(1, userID);
            ResultSet rs = psSleepSql.executeQuery();
            if (rs.next()) {
                int totalSleep = rs.getInt(1); // Total sleep in hours

                // Calculate average sleep duration over the past week
                PreparedStatement psAvgSleepSql = connection.prepareStatement("SELECT AVG(duration) FROM DateTime WHERE user_id = ? AND end_date BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW()");
                psAvgSleepSql.setInt(1, userID);
                ResultSet avgRs = psAvgSleepSql.executeQuery();
                if (avgRs.next()) {
                    int avgSleep = (int) Math.round(avgRs.getDouble(1)); // Average sleep in hours
                    if (avgSleep < 7) {
                        average_text.setText("(You are not getting enough sleep. Aim for at least 7 hours of sleep per night");

                    } else if (avgSleep > 9) {
                        average_text.setText("You are getting more than enough sleep. Consider adjusting your bedtime or waking up earlier.");

                    } else {
                        average_text.setText("Your sleep habits seem to be on track! Keep up the good work.");
                    }
                }

                // Calculate total sleep debt
                int sleepDebt = totalSleep - (8 * 7); // Assumes 8 hours of sleep per night
                if (sleepDebt > 0) {
                    total_text.setText("You have a sleep debt of " + sleepDebt + " hours. Consider going to bed earlier or taking a nap to catch up.");
                } else {
                    total_text.setText("You do not have a sleep debt. Keep up the good work!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


