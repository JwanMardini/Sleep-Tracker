import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

public class LoggedInTest {
    private static final String user = "HKR3";
    private static final String password = "1237";
    private static final int age = 22;
    private static final String email = "example@alloush.com";
    private static final String secQue = "hkr";


    @BeforeAll
    public static void setUp(){
        try(Connection conn = DBUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM Users WHERE username = ?")){
            preparedStatement.setString(1, user);
            int rowDel = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Users(username, Password, email, secQue, age) VALUES(?,?,?,?,?)");){
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, secQue);
            preparedStatement.setInt(5, age);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetPassword(){
        try(Connection conn = DBUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?")){
            preparedStatement.setString(1, user);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String passwordRes = resultSet.getString("Password");
                    assertEquals(password, passwordRes);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
