package javaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_class extends JFrame {

    Main_class(){

        // --- CHANGE: Set the background color to a light, neutral tone ---
        getContentPane().setBackground(new Color(229, 211, 200));
        // ----------------------------------------------------------------

        // creating Label
        JLabel heading = new JLabel("Employee Management System");
        heading.setBounds(150, 50, 450, 40);
        heading.setFont(new Font("Railway", Font.BOLD, 25));
        add(heading);

        // Position buttons perfectly and add color
        JButton add = new JButton("Add Employee");
        add.setBounds(200, 150, 300, 40);
        add.setFont(new Font("Railway", Font.BOLD, 20));
        add.setBackground(new Color(30, 144, 254)); // Dodger Blue
        add.setForeground(Color.WHITE);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddEmployee();
                setVisible(false);
            }
        });
        add(add);

        JButton view = new JButton("View Employees");
        view.setBounds(200, 210, 300, 40);
        view.setFont(new Font("Railway", Font.BOLD, 20));
        view.setBackground(new Color(30, 144, 254));
        view.setForeground(Color.WHITE);
        view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewEmployee();
                setVisible(false);
            }
        });
        add(view);

        JButton remove = new JButton("Remove Employee");
        remove.setBounds(200, 270, 300, 40);
        remove.setFont(new Font("Railway", Font.BOLD, 20));
        remove.setBackground(new Color(30, 144, 254));
        remove.setForeground(Color.WHITE);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoveEmployee();
                setVisible(false);
            }
        });
        add(remove);

        JButton update = new JButton("Update Employee");
        update.setBounds(200, 330, 300, 40);
        update.setFont(new Font("Railway", Font.BOLD, 20));
        update.setBackground(new Color(30, 144, 254));
        update.setForeground(Color.WHITE);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateEmployee();
                setVisible(false);
            }
        });
        add(update);

        setSize(700,500);
        setLocation(400,200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new Main_class();
    }
}