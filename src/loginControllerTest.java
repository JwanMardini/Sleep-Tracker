import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class loginControllerTest {

    private LoginController loginController;
    private Button mockButton;

    @Before
    public void setUp() {
        loginController = new LoginController();
        mockButton = new Button(); // Create a mock Button
    }

    @Test
    public void testSwitchForm_forgotPassword() {
        loginController.switchForm(createMockActionEvent(mockButton));
        assertFalse(loginController.login_form.isVisible());
        assertFalse(loginController.login_left_form.isVisible());
        assertTrue(loginController.forgotPass_form.isVisible());
        assertTrue(loginController.pass_left_form.isVisible());
        assertFalse(loginController.resetPass_form.isVisible());
    }

    @Test
    public void testSwitchForm_forgotPassSend() {
        loginController.switchForm(createMockActionEvent(loginController.forgotPass_send_btn));
        assertFalse(loginController.login_form.isVisible());
        assertFalse(loginController.login_left_form.isVisible());
        assertFalse(loginController.forgotPass_form.isVisible());
        assertTrue(loginController.pass_left_form.isVisible());
        assertTrue(loginController.resetPass_form.isVisible());
    }

    @Test
    public void testSwitchForm_resetPass() {
        loginController.switchForm(createMockActionEvent(loginController.resetPass_btn));
        assertTrue(loginController.login_form.isVisible());
        assertTrue(loginController.login_left_form.isVisible());
        assertFalse(loginController.forgotPass_form.isVisible());
        assertFalse(loginController.pass_left_form.isVisible());
        assertFalse(loginController.resetPass_form.isVisible());
    }

    @Test
    public void testSwitchForm_back() {
        loginController.switchForm(createMockActionEvent(loginController.back_btn));
        assertTrue(loginController.login_form.isVisible());
        assertTrue(loginController.login_left_form.isVisible());
        assertFalse(loginController.forgotPass_form.isVisible());
        assertFalse(loginController.pass_left_form.isVisible());
        assertFalse(loginController.resetPass_form.isVisible());
    }

    private ActionEvent createMockActionEvent(Object source) {
        return new ActionEvent(source, null);
    }

    // Rest of the tests and methods...
}
