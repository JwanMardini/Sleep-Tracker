//Class to communicate with our DB
//Sign in + sign up and changing view or scene

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.sql.*;
import java.io.IOException;
import java.util.Random;



public class DBUtils {
    private static final String DbUrl = "jdbc:mysql://localhost:3306/sleeptrackerlogin";
    private static final String DbUsername = "root";
    private static final String DbPassword = "Jwan.joan12";


    // This method changes the scene to the specified FXML file with a given title and username.
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) { // If a username is provided, load the FXML file and set user information
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else { // If no username is provided, simply load the FXML file.
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Set the stage title, scene, and show the stage.
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    // This method signs up a user with the specified username and password.
    public static void signUpUser(ActionEvent actionEvent, String username, String password, String email) {
        // to close the ResultSet, PreparedStatements, and Connection once the execution is done
        try (Connection connection = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? ")) {
            psCheckUserExists.setString(1, username);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.isBeforeFirst()) {  // If the user already exists, show an error message.
                    System.out.println("User already exists! ");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("User already exists");
                    alert.setHeaderText(null);
                    alert.setContentText("You cannot use this username.");
                    alert.show();
                } else { // If the user doesn't exist, insert the user into the database and change the scene to the logged-in view.
                    try (PreparedStatement psInsert = connection.prepareStatement("INSERT INTO Users(username, Password, email) VALUES(?,?,?)")) {
                        psInsert.setString(1, username);
                        psInsert.setString(2, password);
                        psInsert.setString(3, email);
                        psInsert.executeUpdate();
                        changeScene(actionEvent, "resources/logged-in.fxml", "Welcome!", username);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void logInUser(ActionEvent actionEvent, String username, String password) {
        // to close the ResultSet, PreparedStatements, and Connection once the execution is done
        try (Connection connection = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT Password FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    System.out.println("User not found in the database!");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Incorrect Credentials Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Provided Credentials are incorrect!");
                    alert.show();
                } else {
                    while (resultSet.next()) {
                        String retrievePassword = resultSet.getString("Password");
                        if (retrievePassword.equals(password)) {
                            changeScene(actionEvent, "resources/logged-in.fxml", "Welcome!", username);
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Incorrect Credentials Message");
                            alert.setHeaderText(null);
                            alert.setContentText("The provided credentials are incorrect!");
                            alert.show();
                        }
                    }
                }
            }
            }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkEmail(String email) {
        String retrieveEmail = null;
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    retrieveEmail = resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(retrieveEmail == null)
            return false;
        else {
            return true;
        }
    }


    public static String sendToken(ActionEvent actionEvent , String email) throws EmailException {
        // Generate a random token
        String token = String.format("%04d", new Random().nextInt(10000));

        // Set up email properties
        String host = "smtp.mail.gmail.com";
        String emailUsername = "sleep.tracker12@gmail.com";
        String emailPassword = "Sleep.123";

        // Create the email message
        HtmlEmail emailMessage = new HtmlEmail();
        emailMessage.setHostName(host);
        emailMessage.setSmtpPort(587);
        emailMessage.setAuthentication(emailUsername, emailPassword);
        emailMessage.setStartTLSEnabled(true);
        emailMessage.setFrom(emailUsername);
        emailMessage.addTo(email);
        emailMessage.setSubject("Your token for verification");
        emailMessage.setHtmlMsg("Your verification token is: " + token);
        // Send the email
        emailMessage.send();
        // Return the token sent to the email
        return token;
    }


    public static void updatePassword(String email, String newPassword){

    }


}

