package javaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class Login extends JFrame implements ActionListener {
    // These are now properly declared instance variables
    JTextField userField;
    JPasswordField passField;
    JButton loginButton;
    JButton backButton;

    Login() {
        // background color
        getContentPane().setBackground(new Color(229, 211, 200));

        JLabel username = new JLabel("Username");
        username.setBounds(170, 95, 100, 30);
        add(username);

        userField = new JTextField();
        userField.setBounds(280, 95, 150, 30);
        add(userField);

        JLabel password = new JLabel("Password");
        password.setBounds(170, 135, 100, 30);
        add(password);

        passField = new JPasswordField();
        passField.setBounds(280, 135, 150, 30);
        add(passField);

        backButton = new JButton("Back");
        backButton.setBounds(170, 175, 100, 30);
        backButton.addActionListener(this);
        add(backButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(280, 175, 150, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        // Frame settings
        setSize(600, 300); // Fix: Frame size matches image size
        setLocation(450, 200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == loginButton){
            try{
                String user = userField.getText().trim();
                String pass = new String(passField.getPassword()).trim();

                conn c = new conn();

                // Fix: Use PreparedStatement to prevent SQL Injection
                String query = "select * from login where UserName = ? and password = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setString(1, user);
                ps.setString(2, pass);

                ResultSet resultSet = ps.executeQuery();

                if(resultSet.next()){
                    setVisible(false);
                    new Main_class();
                }else {
                    JOptionPane.showMessageDialog(null,"Invalid Username or Password");
                }

                // Close resources
                resultSet.close();
                ps.close();
                c.connection.close();

            }catch(Exception E){
                E.printStackTrace();
            }
        } else if(e.getSource() == backButton){
            System.exit(0);         }
    }

    public static void main(String[] args){
        new Login();
    }
}