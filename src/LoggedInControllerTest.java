import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggedInControllerTest {

    private Connection connection;

    @BeforeEach
    public void setup() throws SQLException {
        // Establish a connection to the test database
        connection = DriverManager.getConnection("jdbc:mysql://localhost/testdb", "username", "password");

        // Create the necessary tables and insert sample data
        createTables();
        insertSampleData();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        // Drop the test tables
        dropTables();

        // Close the connection
        connection.close();
    }

    @Test
    public void testSetUserAge() throws SQLException {
        // Create an instance of LoggedInController
        LoggedInController loggedInController = new LoggedInController();

        // Call the method to be tested
        loggedInController.setUserAge("testUser");

        // Assert the expected result
        assertEquals(25, loggedInController.getAge());
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        // Create the Users table
        String createUserTableQuery = "CREATE TABLE Users (" +
                "username VARCHAR(50) PRIMARY KEY," +
                "age INT" +
                ")";
        statement.executeUpdate(createUserTableQuery);
    }

    private void insertSampleData() throws SQLException {
        // Insert a sample user with age 25
        String insertUserQuery = "INSERT INTO Users (username, age) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery);
        preparedStatement.setString(1, "testUser");
        preparedStatement.setInt(2, 25);
        preparedStatement.executeUpdate();
    }

    private void dropTables() throws SQLException {
        Statement statement = connection.createStatement();

        // Drop the Users table
        String dropUserTableQuery = "DROP TABLE Users";
        statement.executeUpdate(dropUserTableQuery);
    }
    @Test
    public void testGetRecommendedSleepDuration() {
       LoggedInController controller = new LoggedInController();

        // Test for different age ranges
        int[] result1 = controller.getRecommendedSleepDuration(25);
        Assertions.assertArrayEquals(new int[]{7, 9}, result1);

        int[] result2 = controller.getRecommendedSleepDuration(10);
        Assertions.assertArrayEquals(new int[]{9, 11}, result2);

        int[] result3 = controller.getRecommendedSleepDuration(2);
        Assertions.assertArrayEquals(new int[]{12, 14}, result3);

        // Test for edge cases
        int[] result4 = controller.getRecommendedSleepDuration(0);
        Assertions.assertArrayEquals(new int[]{14, 17}, result4);

        int[] result5 = controller.getRecommendedSleepDuration(120);
        Assertions.assertArrayEquals(new int[]{7, 8}, result5);
    }

}
