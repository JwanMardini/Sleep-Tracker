import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBUtilsTest {
    private static final String user = "HKR3457dsaasda73wrewr253443";
    private static final String password = "1234567";
    private static final int age = 22;
    private static final String email = "example@alloush.com";
    private static final String secQue = "hkr";

    //Tests sign up and log in

    @BeforeAll
    public static void setUp(){

    }
    @Test
    public void testSignUpUserWithValidData() {
        String usernameRes = null;
        String passwordRes = null;
        int ageRes = 0;
        String emailRes = null;
        String secQueRes = null;
        // Check that the user was inserted into the database
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            preparedStatement.setString(1, user);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    usernameRes = resultSet.getString("username");
                    passwordRes = resultSet.getString("Password");
                    ageRes = resultSet.getInt("age");
                    emailRes = resultSet.getString("email");
                    secQueRes = resultSet.getString("secQue");

                    assertEquals(user, usernameRes);
                    assertEquals(password, passwordRes);
                    assertEquals(age, ageRes);
                    assertEquals(email, emailRes);
                    assertEquals(secQue, secQueRes);
                }else {
                    try (PreparedStatement insertStatement = conn.prepareStatement("INSERT INTO users (username, password, age, email, secQue) VALUES (?, ?, ?, ?, ?)")) {
                        insertStatement.setString(1, user);
                        insertStatement.setString(2, password);
                        insertStatement.setInt(3, age);
                        insertStatement.setString(4, email);
                        insertStatement.setString(5, secQue);
                        insertStatement.executeUpdate();

                        // Retrieve the added user's values
                        usernameRes = user;
                        passwordRes = password;
                        ageRes = age;
                        emailRes = email;
                        secQueRes = secQue;

                        // Check if the values are the same
                        assertEquals(user, usernameRes);
                        assertEquals(password, passwordRes);
                        assertEquals(age, ageRes);
                        assertEquals(email, emailRes);
                        assertEquals(secQue, secQueRes);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Tests getEmail method
    @Test
    public void testGetEmail() {
        String emailRes = null;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT email FROM users WHERE username = ?")) {
            preparedStatement.setString(1, user);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    emailRes = resultSet.getString("email");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(email, emailRes);
    }
}


