import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginFrame extends JFrame implements ActionListener {
    JButton loginButton;
    JButton registerButton2;
    JLabel label;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JTextField userNameField;
    JTextField passwordField;
    JCheckBox showPassBox;

    Container contentPane;
    loginFrame(){
        this.setTitle("Welcome!");
        this.setSize(600, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = this.getContentPane();
        contentPane.setBackground(Color.DARK_GRAY);

        label = new JLabel();
        label.setText("Log in, or register");
        label.setBounds(200, 20, 250, 100);
        label.setBackground(Color.DARK_GRAY);
        label.setFont(new Font("MV Bold", Font.BOLD, 18));
        label.setForeground(Color.cyan);
        label.setOpaque(true);

        usernameLabel = new JLabel("Email");
        usernameLabel.setBounds(180, 125, 250, 50);
        usernameLabel.setBackground(Color.white);
        usernameLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        usernameLabel.setForeground(Color.cyan);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(180, 165, 250, 50);
        passwordLabel.setBackground(Color.white);
        passwordLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        passwordLabel.setForeground(Color.cyan);

        loginButton = new JButton();
        loginButton.setText("Login");
        loginButton.setBounds(250, 220, 100, 30);
        loginButton.setFocusable(false);

        registerButton2 = new JButton();
        registerButton2.setText("Register");
        registerButton2.setBounds(250, 260, 100, 30);
        registerButton2.setFocusable(false);
        registerButton2.addActionListener(this);

        userNameField = new JTextField();
        userNameField.setBounds(250, 140, 150, 20);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 180, 150, 20);

        showPassBox = new JCheckBox();
        showPassBox.setText("Show password");
        showPassBox.setForeground(Color.cyan);
        showPassBox.setBounds(245, 205, 15, 10);
        showPassBox.setForeground(Color.cyan);







        this.add(label);
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(loginButton);
        this.add(registerButton2);
        this.add(userNameField);
        this.add(passwordField);
        this.add(showPassBox);
        //this.add(comboBox);
        this.setLayout(null);

        this.setVisible(true);




    }







    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton2){
            registerFrame registerPage = new registerFrame();
            this.setVisible(false);
        }

    }
}
