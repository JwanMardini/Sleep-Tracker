import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class registerFrame extends JFrame implements ActionListener {
    Container contentPane;
    JButton registerButton;
    JButton goBackButton;
    JTextField usernameField;
    JPasswordField passwordField;
    JPasswordField confPasswordField;
    JTextField phoneNumField;
    JLabel label;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel confPasswordLabel;
    JLabel phoneNumLabel;
    JComboBox genderComboBox;









    registerFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);

        label = new JLabel();
        label.setText("Registrations page");
        label.setBounds(200, 20, 250, 100);
        label.setBackground(Color.DARK_GRAY);
        label.setFont(new Font("MV Bold", Font.BOLD, 18));
        label.setForeground(Color.cyan);
        label.setOpaque(true);

        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 120, 250, 50);
        usernameLabel.setBackground(Color.white);
        usernameLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        usernameLabel.setForeground(Color.cyan);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 160, 250, 50);
        passwordLabel.setBackground(Color.white);
        passwordLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        passwordLabel.setForeground(Color.cyan);

        confPasswordLabel = new JLabel("Confirm password");
        confPasswordLabel.setBounds(100, 200, 250, 50);
        confPasswordLabel.setBackground(Color.white);
        confPasswordLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        confPasswordLabel.setForeground(Color.cyan);

        phoneNumLabel = new JLabel("Phone Number");
        phoneNumLabel.setBounds(100, 240, 250, 50);
        phoneNumLabel.setBackground(Color.white);
        phoneNumLabel.setFont(new Font("MV Bold", Font.BOLD, 12));
        phoneNumLabel.setForeground(Color.cyan);

        registerButton = new JButton();
        registerButton.setText("Register");
        registerButton.setBounds(250, 260, 100, 30);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);

        usernameField = new JTextField();
        usernameField.setBounds(210, 140, 150, 20);

        passwordField = new JPasswordField();
        passwordField.setBounds(210, 180, 150, 20);

        confPasswordField = new JPasswordField();
        confPasswordField.setBounds(210, 220, 150, 20);

        phoneNumField = new JPasswordField();
        phoneNumField.setBounds(210, 260, 150, 20);

        registerButton = new JButton("register");
        registerButton.setBounds(230, 300, 100, 30);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);

        goBackButton = new JButton("Go back");
        goBackButton.setBounds(230, 340, 100, 30);
        goBackButton.setFocusable(false);
        goBackButton.addActionListener(this);
        goBackButton.setBorderPainted(false);
        goBackButton.setContentAreaFilled(false);
        goBackButton.setFocusPainted(false);
        goBackButton.setForeground(Color.cyan);
        goBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));



        contentPane = this.getContentPane();
        contentPane.setBackground(Color.DARK_GRAY);


        String[] gender = {" ", "Male", "Female", "Other"};
        genderComboBox = new JComboBox(gender);
        genderComboBox.setBounds(100, 420, 150, 20);


        this.add(label);
        this.add(usernameField);
        this.add(passwordField);
        this.add(confPasswordField);
        this.add(phoneNumField);
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(confPasswordLabel);
        this.add(phoneNumLabel);
        this.add(registerButton);
        this.add(goBackButton);

        this.setLayout(null);
        this.setVisible(true);



    }








    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == goBackButton){
            this.setVisible(false);
            loginFrame loginFrame = new loginFrame();
        }

    }
}
