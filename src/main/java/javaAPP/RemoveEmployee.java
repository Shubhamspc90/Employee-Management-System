package javaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RemoveEmployee extends JFrame implements ActionListener {
    JComboBox<String> cEmpId;
    JButton delete, back;

    public RemoveEmployee() {
        getContentPane().setBackground(new Color(229, 211, 200));

        JLabel heading = new JLabel("Remove Employee");
        heading.setBounds(200, 30, 400, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // Employee ID Label
        JLabel labelEmpId = new JLabel("Employee ID");
        labelEmpId.setBounds(50, 100, 150, 30);
        labelEmpId.setFont(new Font("serif", Font.BOLD, 20));
        add(labelEmpId);

        // Employee ID Dropdown
        cEmpId = new JComboBox<>();
        cEmpId.setBounds(200, 100, 150, 30);
        cEmpId.setBackground(Color.WHITE);
        add(cEmpId);

        // Populate the dropdown with employee IDs from the database
        try {
            conn c = new conn();
            String query = "SELECT empid FROM employee";
            ResultSet rs = c.statement.executeQuery(query);
            while (rs.next()) {
                cEmpId.addItem(rs.getString("empid"));
            }
            rs.close();
            c.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Delete button
        delete = new JButton("Delete");
        delete.setBounds(200, 180, 100, 30);
        delete.addActionListener(this);
        delete.setBackground(new Color(200, 0, 0));
        delete.setForeground(Color.WHITE);
        add(delete);

        // Back button
        back = new JButton("Back");
        back.setBounds(320, 180, 100, 30);
        back.addActionListener(this);
        back.setBackground(new Color(50, 50, 50));
        back.setForeground(Color.WHITE);
        add(back);

        setSize(600, 300);
        setLocation(450, 200);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            try {
                String empId = (String) cEmpId.getSelectedItem();
                conn c = new conn();
                String query = "DELETE FROM employee WHERE empid = ?";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setString(1, empId);
                int result = ps.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Removed Successfully");
                    // Refresh the dropdown after deletion
                    cEmpId.removeItem(empId);
                } else {
                    JOptionPane.showMessageDialog(null, "Employee Not Found");
                }
                ps.close();
                c.connection.close();
            } catch (Exception E) {
                E.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred during deletion.");
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}