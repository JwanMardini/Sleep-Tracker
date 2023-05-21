
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class DBUtils {
    private static final String DbUrl = "jdbc:mysql://localhost:3306/sleeptrackerlogin";
    private static final String DbUsername = "root";
    private static final String DbPassword = "sql@2023";

    // This method changes the scene to the specified FXML file with a given title and username.
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) { // If a username is provided, load the FXML file and set user information
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInfo(username);
                loggedInController.setUserID(username);
                loggedInController.setUserAge(username);
                loggedInController.getPassword(username);

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
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    // This method signs up a user with the specified username and password.
    public static void signUpUser(ActionEvent actionEvent, String username, String password, String email, String secQue, int age) {
        // to close the ResultSet, PreparedStatements, and Connection once the execution is done
        try (Connection connection = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? ")) {
            psCheckUserExists.setString(1, username);
            try (ResultSet resultSet = psCheckUserExists.executeQuery()) {
                if (resultSet.isBeforeFirst()) {  // If the user already exists, show an error message.
                    showErrorAlert("User already exists", null, "You cannot use this username.");

                } else { // If the user doesn't exist, insert the user into the database and change the scene to the logged-in view.


                    try (PreparedStatement psInsert = connection.prepareStatement("INSERT INTO Users(username, Password, email, secQue, age) VALUES(?,?,?,?,?)")) {
                        psInsert.setString(1, username);
                        psInsert.setString(2, password);
                        psInsert.setString(3, email);
                        psInsert.setString(4, secQue);
                        psInsert.setInt(5,age);
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
                    showErrorAlert("Incorrect Credentials Message", null, "Provided Credentials are incorrect!");

                } else {
                    while (resultSet.next()) {


                       String retrievePassword = resultSet.getString("Password");
                        if (retrievePassword.equals(password)) {

                            changeScene(actionEvent, "resources/logged-in.fxml", "Welcome!", username);
                        } else {
                            showErrorAlert("Incorrect Credentials Message", null,"The provided credentials are incorrect!" );

                        }
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DbUrl, DbUsername, DbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String getEmail(String username){
        String email = null;
        try(Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT email FROM users WHERE username = ?")){
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    email = resultSet.getString("email");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return email;
    }

    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showMessageAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
