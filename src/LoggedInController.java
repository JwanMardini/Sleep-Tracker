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
import java.util.Optional;
import java.util.ResourceBundle;

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


    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
        if (label_welcome != null) {
            label_welcome.setText("Welcome " + userInfo);
        }
    }


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
                    psInsertDateTime.setLong(5, duration.toHours());
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


    public void displayChart() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sleeptrackerlogin", "root", "toor");
             PreparedStatement psChartSql = connection.prepareStatement("SELECT end_date, SUM(duration) FROM DateTime GROUP BY end_date ORDER BY TIMESTAMP(end_date) ASC LIMIT 8");
             ResultSet rs = psChartSql.executeQuery()) {

            XYChart.Series chartData = new XYChart.Series();

            while (rs.next()) {
                chartData.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2))); // 1 is date, 2 is duration
            }

            barChart.getData().add(chartData);

        } catch (SQLException e) {
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



        } else if (event.getSource() == btn_record_sleep) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(true);
            profile_form.setVisible(false);
            main_form.setVisible(false);

            setDefaultDateTime();

        } else if (event.getSource() == btn_history) {
            home_form.setVisible(false);
            history_form.setVisible(true);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);

            displayChart();

        } else if (event.getSource() == btn_profile) {
            home_form.setVisible(false);
            history_form.setVisible(true);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(true);
            main_form.setVisible(false);


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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


