import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class registerFrame extends JFrame implements ActionListener {
    Container contentPane;
    JButton registerButton;
    JTextField emilField;
    JPasswordField passwordField;
    JPasswordField confPassword;
    JTextField phoneNumField;
    JLabel label;
    JLabel emilLabel;
    JLabel passwordLabel;
    JLabel confPasswordLabel;
    JLabel phoneNumLabel;
    JComboBox genderComboBox;









    registerFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setResizable(false);

        contentPane = this.getContentPane();
        contentPane.setBackground(Color.DARK_GRAY);




        String[] gender = {" ", "Male", "Female", "Other"};
        genderComboBox = new JComboBox(gender);
        genderComboBox.setBounds(100, 420, 150, 20);

        this.setLayout(null);
        this.setVisible(true);



    }








    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
