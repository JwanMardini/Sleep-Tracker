import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

public class LoggedInTest {
    private static final String user = "HKR3";
    private static final String password = "1237";
    private static final int age = 22;
    private static final String email = "example@alloush.com";
    private static final String secQue = "hkr";


    @BeforeAll
    public static void setUp() {
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM Users WHERE username = ?");
             PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO users (username, password, age, email, secQue) VALUES (?, ?, ?, ?, ?)")) {

            deleteStatement.setString(1, user);
            int rowsDeleted = deleteStatement.executeUpdate();

            insertStatement.setString(1, user);
            insertStatement.setString(2, password);
            insertStatement.setInt(3, age);
            insertStatement.setString(4, email);
            insertStatement.setString(5, secQue);
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetPassword(){
        LoggedInController loggedInController = new LoggedInController();
        String passRes = loggedInController.getPassword(user);
        assertEquals(password, passRes);
    }
}
