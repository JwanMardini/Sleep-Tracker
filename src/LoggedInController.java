import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    @FXML
    private AnchorPane home_form;
    @FXML
    private Button btn_home;
    @FXML
    private AnchorPane record_sleep_form;
    @FXML
    private AnchorPane resources_form;
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
    private AnchorPane history_form;
    @FXML
    private Button btn_history;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private AnchorPane profile_form;
    @FXML
    private Button btn_profile;
    @FXML
    private Button btn_resources;
    @FXML
    private Button save_profile;
    @FXML
    private TextField age_profile;
    @FXML
    private TextField email_profile;
    @FXML
    private PasswordField password_profile;
    @FXML
    private TextField username_profile;
    @FXML
    private AnchorPane recommendations_form;
    @FXML
    private Button btn_recommend;
    @FXML
    private Text tf_recommend;
    @FXML
    private Button btn_logout;
    @FXML
    private Label label_welcome;
    @FXML
    private Hyperlink sleepFoundationLink;
    @FXML
    private Hyperlink sleepEducationLink;
    @FXML
    private Hyperlink mentalHealthAmericaLink;
    @FXML
    private Hyperlink swedishPsychologicalAssociationLink;
    @FXML
    private Hyperlink swedishAssociationForCognitiveTherapiesLink;
    @FXML
    private Hyperlink headspaceLink;
    @FXML
    private Hyperlink whyWeSleepLink;
    @FXML
    private Hyperlink sleepIsYourSuperpowerLink;
    @FXML
    private Hyperlink sleepDisordersAndSleepDeprivationLink;
    @FXML
    private Hyperlink sleepRevolutionLink;
    @FXML
    private AnchorPane main_form;
    private String userInfo;
    private Alert alert;
    private int userID;
    private int age;
    private String password;

    DBUtils errorMessage = new DBUtils();

    public String getPassword(String username) {
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT Password FROM users WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                this.password = rs.getString("Password");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.password;
    }

    public int setUserID(String username) {
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
        return this.userID;
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
                                errorMessage.showMessageAlert("Error", null,"The sleep duration cannot be negative. Please enter valid date/time values.");

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
                            alert.setContentText("Your sleep record has been saved successfully. \nYour sleep duration is: " +
                                    duration.toHours() + " hours " + (duration.toMinutes() % 60) + " minutes");
                            alert.showAndWait();

                            // Handle invalid date/time format
                        }catch (DateTimeParseException e) {
                            errorMessage.showErrorAlert("Error", null,"Please enter valid date/time values.");
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
            PreparedStatement psChartSql = connection.prepareStatement("SELECT end_date, SUM(duration) FROM DateTime WHERE user_id = ? " +
                    "GROUP BY end_date ORDER BY TIMESTAMP(end_date) ASC LIMIT 8");
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

        // Remove "active-button" class from all buttons
        btn_home.getStyleClass().remove("active");
        btn_record_sleep.getStyleClass().remove("active");
        btn_history.getStyleClass().remove("active");
        btn_profile.getStyleClass().remove("active");
        btn_recommend.getStyleClass().remove("active");
        btn_resources.getStyleClass().remove("active");

        if (event.getSource() == btn_home) {
            home_form.setVisible(true);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            resources_form.setVisible(false);

            // Add active class to button
            btn_home.getStyleClass().add("active");

        } else if (event.getSource() == btn_record_sleep) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(true);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            resources_form.setVisible(false);

            // Add active class to button
            btn_record_sleep.getStyleClass().add("active");

            setDefaultDateTime();

        } else if (event.getSource() == btn_history) {
            home_form.setVisible(false);
            history_form.setVisible(true);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            resources_form.setVisible(false);

            // Add active class to button
            btn_history.getStyleClass().add("active");

            displayChart();

        } else if (event.getSource() == btn_profile) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(true);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            resources_form.setVisible(false);

            // Add active class to button
            btn_profile.getStyleClass().add("active");

            setProfileInfo();

        } else if (event.getSource() == btn_recommend) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(true);
            resources_form.setVisible(false);

            // Add active class to button
            btn_recommend.getStyleClass().add("active");

            checkSleepDuration();

        }else if (event.getSource() == btn_resources) {
            home_form.setVisible(false);
            history_form.setVisible(false);
            record_sleep_form.setVisible(false);
            profile_form.setVisible(false);
            main_form.setVisible(false);
            recommendations_form.setVisible(false);
            resources_form.setVisible(true);

            // Add active class to button
            btn_resources.getStyleClass().add("active");
        }
    }
    public  void setProfileInfo() {
        username_profile.setText(userInfo);
        email_profile.setText(DBUtils.getEmail(userInfo));
        String ageString = Integer.toString(age);
        age_profile.setText(ageString);
        password_profile.setText(password);

    }
    public void saveUserInfo(ActionEvent actionEvent) {
        // Get the updated user information from the form
        String email = email_profile.getText();
        String ageString = age_profile.getText();
        int ageInt = Integer.parseInt(ageString);
        String userNAME = username_profile.getText();
        String password = password_profile.getText();

        // Check if any of the fields are empty
        if (email.isEmpty() || ageString.isEmpty() || userNAME.isEmpty() || password.isEmpty()) {
            errorMessage.showErrorAlert("Error", null, "Please fill out all fields.");
            return;
        }
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET username=?, age=?, email=?, password=? WHERE username=?");
            pstmt.setString(1, userNAME);
            pstmt.setInt(2, ageInt);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, userInfo);
            pstmt.executeUpdate();
            conn.close();
            label_welcome.setText("Welcome " + userNAME + "!");
            this.userInfo = userNAME;
            this.age = ageInt;

            errorMessage.showMessageAlert("Success", null, "User information updated successfully.");

        } catch (SQLException e) {
            // Handle any errors that occur during the update process
            e.printStackTrace();
            errorMessage.showErrorAlert("Error", null, "An error occurred while updating user information.");
        }
    }


    public void logout() {
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
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

    public void checkSleepDuration() {
        Connection connection = DBUtils.getConnection();
        try {
            // Get the recommended sleep duration based on the user's age
            int[] recommendedSleepDuration = getRecommendedSleepDuration(age);
            int recommendedSleepDurationMin = recommendedSleepDuration[0];
            int recommendedSleepDurationMax = recommendedSleepDuration[1];

            // Get the total sleep duration for the user over the past week
            PreparedStatement psSleepSql = connection.prepareStatement("SELECT SUM(duration) FROM DateTime WHERE user_id = ? " +
                                                              "AND end_date BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW()");
            psSleepSql.setInt(1, userID);
            ResultSet sleepRs = psSleepSql.executeQuery();
            if (sleepRs.next()) {
                int totalSleepWeek = sleepRs.getInt(1); // Total sleep in hours over the past week

                // Calculate average sleep duration over the past week
                int avgSleep = (int) Math.round(totalSleepWeek / 7.0); // Average sleep in hours over the past week
                if (avgSleep < recommendedSleepDurationMin ) {
                    if (avgSleep == 0) {
                        tf_recommend.setText("It appears that your average sleep duration is 0. This could be because " +
                                "you may not have recorded your sleep hours accurately or consistently last week");

                    }else {
                        tf_recommend.setText("You are not getting enough sleep. Aim for at least " + recommendedSleepDurationMin +
                                " to " + recommendedSleepDurationMax + " hours of sleep per night for your age category. \n" +
                                 "\nTo get professional advice or counseling service, please visit the 'Resources' page. \n" +
                                "\nThis recommendation is based on your sleep records from the past week. The Following list displays these days: \n");
                    }
                    // Get the sleep records for the past week
                    PreparedStatement psSleepRecordsSql = connection.prepareStatement("SELECT start_time, end_time, duration FROM DateTime WHERE user_id = ? AND end_date BETWEEN DATE_SUB(NOW(), INTERVAL 7 DAY) AND NOW() ORDER BY end_date DESC");
                    psSleepRecordsSql.setInt(1, userID);
                    ResultSet sleepRecordsRs = psSleepRecordsSql.executeQuery();
                    String recommendation = "";
                    int day = 1;
                    while (sleepRecordsRs.next()) {
                        String startTime = sleepRecordsRs.getString("start_time");
                        String endTime = sleepRecordsRs.getString("end_time");
                        int duration = sleepRecordsRs.getInt("duration");
                        recommendation += day++ + ":   Start Time: " + startTime + ", End Time: " + endTime + ", Duration: " + duration + " hours \n";
                    }
                    tf_recommend.setText(tf_recommend.getText() + "\n" + recommendation);
                    tf_recommend.setWrappingWidth(475);
                } else {
                    tf_recommend.setText("Your sleep habits seem to be on track! Keep up the good work for your age category.");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int[] getRecommendedSleepDuration(int age) {
        int[] recommendedSleepDuration = new int[2];

        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age);
        } else if (age < 1) {
            recommendedSleepDuration[0] = 14;
            recommendedSleepDuration[1] = 17;
        } else if (age < 3) {
            recommendedSleepDuration[0] = 12;
            recommendedSleepDuration[1] = 14;
        } else if (age < 6) {
            recommendedSleepDuration[0] = 10;
            recommendedSleepDuration[1] = 13;
        } else if (age < 13) {
            recommendedSleepDuration[0] = 9;
            recommendedSleepDuration[1] = 11;
        } else if (age < 18) {
            recommendedSleepDuration[0] = 8;
            recommendedSleepDuration[1] = 10;
        } else if (age < 26) {
            recommendedSleepDuration[0] = 7;
            recommendedSleepDuration[1] = 9;
        } else if (age < 65) {
            recommendedSleepDuration[0] = 7;
            recommendedSleepDuration[1] = 8;
        } else {
            recommendedSleepDuration[0] = 7;
            recommendedSleepDuration[1] = 8;
        }

        return recommendedSleepDuration;
    }

    public void setUserAge(String username) {
        Connection connection = DBUtils.getConnection();
        try {
            PreparedStatement psAgeSql = connection.prepareStatement("SELECT age FROM Users WHERE username = ?");
            psAgeSql.setString(1, username);
            ResultSet ageRs = psAgeSql.executeQuery();
            if (ageRs.next()) {
                this.age = ageRs.getInt("age");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void sleepEducationLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(sleepEducationLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void sleepFoundationLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(sleepFoundationLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void mentalHealthAmericaLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(mentalHealthAmericaLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void swedishPsychologicalAssociationLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(swedishPsychologicalAssociationLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void SwedishAssociationForCognitiveTherapiesLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(swedishAssociationForCognitiveTherapiesLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace(); // Print the stack trace for debugging purposes
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }
    @FXML
    void headspaceLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(headspaceLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void whyWeSleepLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(whyWeSleepLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void sleepIsYourSuperpowerLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(sleepIsYourSuperpowerLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void SleepDisordersAndSleepDeprivationLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(sleepDisordersAndSleepDeprivationLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

    @FXML
    void SleepRevolutionLinkClicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(sleepRevolutionLink.getText()));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
            errorMessage.showErrorAlert("Error", "Failed to open link", "An error occurred while opening the link.");
        }
    }

}


