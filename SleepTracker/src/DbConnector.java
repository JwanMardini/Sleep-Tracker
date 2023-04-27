import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnector {
    private static final String DB_URL = "";
    private static final String USER = "";
    private static final String PASS = "";


    public void addUser(User user) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             /*PreparedStatement stmt = conn.prepareStatement("INSERT INTO employee (name, age) VALUES (?, ?)")*/) {
            /*stmt.setString(1, employee.name());
            stmt.setInt(2, employee.age());
            stmt.executeUpdate();*/
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }



}
