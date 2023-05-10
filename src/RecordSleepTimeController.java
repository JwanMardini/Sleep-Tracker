import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class RecordSleepTimeController {
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

    private LoginController loginController;


    @FXML
    void btnBackToHome(ActionEvent event) throws IOException {
        //Main.changeScene("resources/logged-in.fxml");
    }

    @FXML
    public void initialize() {
        // Set the default value for the date picker
        start_date.setValue(LocalDateTime.now().toLocalDate());

        // Set the default value for the time field
        start_time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    }


    @FXML
    public void handleSaveButton(ActionEvent actionEvent) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sleeptrackerlogin", "root", "toor");
             PreparedStatement psGetUserId = connection.prepareStatement("SELECT id FROM users WHERE username = ?");
             PreparedStatement psInsertDateTime = connection.prepareStatement("INSERT INTO DateTime(start_date, start_time, end_date, end_time, duration, user_id) VALUES (?, ?, ?, ?, ?, ?)")) {

            // Get the user ID for the logged-in user from the Users table
            psGetUserId.setString(1, username.getText());
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

