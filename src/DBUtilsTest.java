

/*

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import java.awt.event.ActionEvent;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.control.Alert;


public class DBUtilsTest {
    private static final String DbUrl = "jdbc:mysql://localhost:3306/sleeptracker";
    private static final String DbUsername = "root";
    private static final String DbPassword = "sql@2023";

    @Test
    public void testSignUpUserWithValidData() {
        // Given
        String username = "user123";
        String password = "password";
        String email = "user123@example.com";
        String seQue = "HKR";

        // When
        //DBUtils.signUpUser(null, username, password, email);



        // Check that the user was inserted into the database
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                assertTrue(resultSet.isBeforeFirst());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Check that the scene was changed to the logged-in view
        // TODO: implement a way to check the scene
    }

    @Test
    public void testSignUpUserWithExistingUsername() {
        // Given
        String username = "user456";
        String password = "password";
        String email = "user456@example.com";
        String seQue = "HKR";
        // Insert a user with the same username into the database
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Users(username, Password, email, secQue) VALUES(?,?,?,?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, seQue);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // When
        //DBUtils.signUpUser(null, username, password, email);

        // Then
        // Check that an error message was shown
        // TODO: implement a way to check the alert message
    }

    @Test
    public void testLogInUserWithValidCredentials() {
        // Given
        String username = "user789";
        String password = "password";
        // Insert a user into the database
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Users(username, Password, email, secQue) VALUES(?,?,?,?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "user789@example.com");
            preparedStatement.setString(4, "HKR");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // When
        DBUtils.logInUser(null, username, password);

        // Check that the scene was changed to the logged-in view
        // TODO: implement a way to check the scene
    }

    @Test
    public void testLogInUser_WrongCredentials() {

        javafx.event.ActionEvent actionEvent = new javafx.event.ActionEvent();

        String username = "john";
        String password = "test123";
        //DBUtils.signUpUser(actionEvent, username, password, "john@example.com");

        // Act
        DBUtils.logInUser(actionEvent, username, "wrongpassword");

        // Assert
        // Check if an error message is shown
        Alert alert = getTopMostAlert();
        assertTrue(alert.getTitle().equals("Login failed"));
        assertTrue(alert.getContentText().equals("Please check your username and password and try again."));
    }

// Helper methods

    private Scene getCurrentScene() {
        return new Scene(new Parent() {
            @Override
            protected ObservableList<Node> getChildren() {
                return FXCollections.emptyObservableList();
            }
        });
    }

    private Alert getTopMostAlert() {
        // TODO: implement this method
        return null;
    }

    public static void signUpUser(ActionEvent event, String username, String password, String email) {
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Users(username, Password, email) VALUES(?,?,?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            // TODO: change scene to logged-in view
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) {
                // User already exists
                // TODO: show an error message
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    // User is logged in
                    // TODO: change scene to logged-in view
                } else {
                    // Login failed
                    // TODO: show an error message
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUserExists(String username) {
        try (Connection conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String loggedInUser() {
        // TODO: implement this method
        return null;
    }


}

*/
