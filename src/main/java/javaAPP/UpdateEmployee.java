package javaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import com.toedter.calendar.JDateChooser;

public class UpdateEmployee extends JFrame implements ActionListener {
    JButton checkButton, updateButton, back;
    JComboBox<String> cEmpId;
    JTextField tname, tfname, taddress, tphone, temail, tsalary, tdesignation;
    JLabel tEmpId, heading;
    JDateChooser dcdob;

    public UpdateEmployee() {
        getContentPane().setBackground(new Color(229, 211, 200));

        heading = new JLabel("Update Employee Details");
        heading.setBounds(300, 30, 400, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // --- CHANGE HERE: Updated Search Label Text ---
        JLabel searchLabel = new JLabel("Enter Employee ID");
        searchLabel.setBounds(50, 80, 200, 30);
        searchLabel.setFont(new Font("serif", Font.BOLD, 15));
        add(searchLabel);
        // ---------------------------------------------

        cEmpId = new JComboBox<>();
        cEmpId.setBounds(250, 80, 150, 30);
        cEmpId.setBackground(Color.WHITE);
        add(cEmpId);

        checkButton = new JButton("Check");
        checkButton.setBounds(420, 80, 100, 30);
        checkButton.addActionListener(this);
        checkButton.setBackground(new Color(30, 144, 254));
        checkButton.setForeground(Color.WHITE);
        add(checkButton);

        // Name
        JLabel name = new JLabel("Name");
        name.setBounds(50, 150, 150, 30);
        name.setFont(new Font("serif", Font.BOLD, 20));
        add(name);

        tname = new JTextField();
        tname.setBounds(200, 150, 150, 30);
        add(tname);

        // Father's Name
        JLabel fname = new JLabel("Father's Name");
        fname.setBounds(400, 150, 150, 30);
        fname.setFont(new Font("serif", Font.BOLD, 20));
        add(fname);

        tfname = new JTextField();
        tfname.setBounds(600, 150, 150, 30);
        add(tfname);

        // Date of Birth
        JLabel dob = new JLabel("Date of Birth");
        dob.setBounds(50, 200, 150, 30);
        dob.setFont(new Font("serif", Font.BOLD, 20));
        add(dob);

        dcdob = new JDateChooser();
        dcdob.setBounds(200, 200, 150, 30);
        dcdob.setBackground(Color.WHITE);
        add(dcdob);

        // Phone
        JLabel phone = new JLabel("Phone");
        phone.setBounds(400, 200, 150, 30);
        phone.setFont(new Font("serif", Font.BOLD, 20));
        add(phone);

        tphone = new JTextField();
        tphone.setBounds(600, 200, 150, 30);
        add(tphone);

        // Address
        JLabel address = new JLabel("Address");
        address.setBounds(50, 250, 150, 30);
        address.setFont(new Font("serif", Font.BOLD, 20));
        add(address);

        taddress = new JTextField();
        taddress.setBounds(200, 250, 150, 30);
        add(taddress);

        // Email
        JLabel email = new JLabel("Email");
        email.setBounds(400, 250, 150, 30);
        email.setFont(new Font("serif", Font.BOLD, 20));
        add(email);

        temail = new JTextField();
        temail.setBounds(600, 250, 150, 30);
        add(temail);

        // Salary
        JLabel salary = new JLabel("Salary");
        salary.setBounds(50, 300, 150, 30);
        salary.setFont(new Font("serif", Font.BOLD, 20));
        add(salary);

        tsalary = new JTextField();
        tsalary.setBounds(200, 300, 150, 30);
        add(tsalary);

        // Designation
        JLabel designation = new JLabel("Designation");
        designation.setBounds(400, 300, 150, 30);
        designation.setFont(new Font("serif", Font.BOLD, 20));
        add(designation);

        tdesignation = new JTextField();
        tdesignation.setBounds(600, 300, 150, 30);
        add(tdesignation);

        // Buttons
        updateButton = new JButton("Update");
        updateButton.setBounds(250, 400, 150, 40);
        updateButton.addActionListener(this);
        updateButton.setBackground(new Color(30, 144, 254));
        updateButton.setForeground(Color.WHITE);
        add(updateButton);

        back = new JButton("Back");
        back.setBounds(450, 400, 150, 40);
        back.addActionListener(this);
        back.setBackground(new Color(50, 50, 50));
        back.setForeground(Color.WHITE);
        add(back);

        setSize(850, 500);
        setLocation(350, 150);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        populateEmpIds();
    }

    private void populateEmpIds() {
        conn c = null;
        ResultSet rs = null;
        try {
            c = new conn();
            String query = "SELECT empid FROM employee";
            rs = c.statement.executeQuery(query);
            while (rs.next()) {
                cEmpId.addItem(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (c != null && c.connection != null) c.connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        tname.setText("");
        tfname.setText("");
        taddress.setText("");
        tphone.setText("");
        temail.setText("");
        tsalary.setText("");
        tdesignation.setText("");
        dcdob.setDate(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            String empId = (String) cEmpId.getSelectedItem();
            if (empId == null) {
                JOptionPane.showMessageDialog(null, "No Employee ID selected.");
                return;
            }
            conn c = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                c = new conn();
                String query = "SELECT * FROM employee WHERE empid = ?";
                ps = c.connection.prepareStatement(query);
                ps.setString(1, empId);
                rs = ps.executeQuery();

                // --- FIX: Clear fields if employee is not found ---
                if (rs.next()) {
                    tname.setText(rs.getString("name"));
                    tfname.setText(rs.getString("fname"));
                    taddress.setText(rs.getString("address"));
                    tphone.setText(rs.getString("phone"));
                    temail.setText(rs.getString("email"));
                    tsalary.setText(rs.getString("salary"));
                    tdesignation.setText(rs.getString("designation"));

                    // Set Date of Birth
                    java.sql.Date dob = rs.getDate("dob");
                    if (dob != null) {
                        dcdob.setDate(new java.util.Date(dob.getTime()));
                    }
                } else {
                    clearFields(); // Clear old data
                    JOptionPane.showMessageDialog(null, "Employee with ID " + empId + " not found.");
                }
                // --------------------------------------------------

            } catch (Exception E) {
                E.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == updateButton) {
            String empId = (String) cEmpId.getSelectedItem();
            String name = tname.getText();
            String fname = tfname.getText();
            String address = taddress.getText();
            String phone = tphone.getText();
            String email = temail.getText();
            String salary = tsalary.getText();
            String designation = tdesignation.getText();

            String dob = "";
            java.util.Date selectedDate = dcdob.getDate();
            if (selectedDate != null) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                dob = sdf.format(selectedDate);
            }

            conn c = null;
            PreparedStatement ps = null;
            try {
                c = new conn();
                String query = "UPDATE employee SET name = ?, fname = ?, dob = ?, salary = ?, address = ?, phone = ?, email = ?, designation = ? WHERE empid = ?";
                ps = c.connection.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, fname);
                ps.setString(3, dob);
                ps.setString(4, salary);
                ps.setString(5, address);
                ps.setString(6, phone);
                ps.setString(7, email);
                ps.setString(8, designation);
                ps.setString(9, empId);

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
                    setVisible(false);
                    new Main_class();
                } else {
                    JOptionPane.showMessageDialog(null, "Employee Not Found or No changes Made");
                }
            } catch (Exception E) {
                E.printStackTrace();
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (c != null && c.connection != null) c.connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new UpdateEmployee();
    }
}