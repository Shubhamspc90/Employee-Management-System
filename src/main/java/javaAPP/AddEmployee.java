package javaAPP;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.Random;

public class AddEmployee extends JFrame implements ActionListener {
    Random ran = new Random();
    int number = ran.nextInt(999999);
    JButton addButton, back;
    JTextField tname, tfname, taddress, tphone, taadhar, temail, tsalary, tdesignation;
    JLabel tempid;
    JComboBox boxEducation;
    JDateChooser dcdate;

    public AddEmployee() {
        getContentPane().setBackground(new Color(229, 211, 200));

        JLabel heading = new JLabel("Add Employee Details");
        heading.setBounds(350, 30, 400, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // Name
        JLabel name = new JLabel("Name");
        name.setBounds(50, 100, 150, 30);
        name.setFont(new Font("serif", Font.BOLD, 20));
        add(name);

        tname = new JTextField();
        tname.setBounds(200, 100, 150, 30);
        add(tname);

        // Father's Name
        JLabel fname = new JLabel("Father's Name");
        fname.setBounds(400, 100, 150, 30);
        fname.setFont(new Font("serif", Font.BOLD, 20));
        add(fname);

        tfname = new JTextField();
        tfname.setBounds(600, 100, 150, 30);
        add(tfname);

        // Date of Birth
        JLabel dob = new JLabel("Date of Birth");
        dob.setBounds(50, 150, 150, 30);
        dob.setFont(new Font("serif", Font.BOLD, 20));
        add(dob);

        dcdate = new JDateChooser();
        dcdate.setBounds(200, 150, 150, 30);
        dcdate.setBackground(Color.WHITE);
        add(dcdate);

        // Phone
        JLabel phone = new JLabel("Phone");
        phone.setBounds(400, 150, 150, 30);
        phone.setFont(new Font("serif", Font.BOLD, 20));
        add(phone);

        tphone = new JTextField();
        tphone.setBounds(600, 150, 150, 30);
        add(tphone);

        // Address
        JLabel address = new JLabel("Address");
        address.setBounds(50, 200, 150, 30);
        address.setFont(new Font("serif", Font.BOLD, 20));
        add(address);

        taddress = new JTextField();
        taddress.setBounds(200, 200, 150, 30);
        add(taddress);

        // Email
        JLabel email = new JLabel("Email");
        email.setBounds(400, 200, 150, 30);
        email.setFont(new Font("serif", Font.BOLD, 20));
        add(email);

        temail = new JTextField();
        temail.setBounds(600, 200, 150, 30);
        add(temail);

        // Aadhar Number
        JLabel aadhar = new JLabel("Aadhar Number");
        aadhar.setBounds(50, 250, 150, 30);
        aadhar.setFont(new Font("serif", Font.BOLD, 20));
        add(aadhar);

        taadhar = new JTextField();
        taadhar.setBounds(200, 250, 150, 30);
        add(taadhar);

        // Salary
        JLabel salary = new JLabel("Salary");
        salary.setBounds(400, 250, 150, 30);
        salary.setFont(new Font("serif", Font.BOLD, 20));
        add(salary);

        tsalary = new JTextField();
        tsalary.setBounds(600, 250, 150, 30);
        add(tsalary);

        // Education
        JLabel education = new JLabel("Education");
        education.setBounds(50, 300, 150, 30);
        education.setFont(new Font("serif", Font.BOLD, 20));
        add(education);

        String items[] = {"BA", "BSC", "BCA", "MCA", "BBA", "MBA", "BTECH", "MTECH"};
        boxEducation = new JComboBox(items);
        boxEducation.setBounds(200, 300, 150, 30);
        boxEducation.setBackground(Color.WHITE);
        add(boxEducation);

        // Designation
        JLabel designation = new JLabel("Designation");
        designation.setBounds(400, 300, 150, 30);
        designation.setFont(new Font("serif", Font.BOLD, 20));
        add(designation);

        tdesignation = new JTextField();
        tdesignation.setBounds(600, 300, 150, 30);
        add(tdesignation);

        // Employee ID
        JLabel empid = new JLabel("Employee ID");
        empid.setBounds(50, 350, 150, 30);
        empid.setFont(new Font("serif", Font.BOLD, 20));
        add(empid);

        tempid = new JLabel("" + number);
        tempid.setBounds(200, 350, 150, 30);
        tempid.setFont(new Font("serif", Font.BOLD, 20));
        tempid.setForeground(Color.RED);
        add(tempid);


        // Add and Back buttons
        addButton = new JButton("ADD");
        addButton.setBounds(250, 450, 150, 40);
        addButton.addActionListener(this);
        add(addButton);

        back = new JButton("Back");
        back.setBounds(450, 450, 150, 40);
        back.addActionListener(this);
        add(back);

        setSize(850, 550);
        setLocation(350, 100);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = tname.getText();
            String fname = tfname.getText();
            String dob = ((JTextField) dcdate.getDateEditor().getUiComponent()).getText();
            String salary = tsalary.getText();
            String address = taddress.getText();
            String phone = tphone.getText();
            String email = temail.getText();
            String aadhar = taadhar.getText();
            String education = (String) boxEducation.getSelectedItem();
            String designation = tdesignation.getText();
            String empid = tempid.getText();

            try {
                conn c = new conn();
                String query = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.connection.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, fname);
                ps.setString(3, dob);
                ps.setString(4, salary);
                ps.setString(5, address);
                ps.setString(6, phone);
                ps.setString(7, email);
                ps.setString(8, aadhar);
                ps.setString(9, education);
                ps.setString(10, designation);
                ps.setString(11, empid);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee Added Successfully");
                setVisible(false);
                new Main_class();

                ps.close();
                c.connection.close();
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}