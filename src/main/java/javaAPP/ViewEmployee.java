package javaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ViewEmployee extends JFrame implements ActionListener {
    JTable table;
    JButton search, showAll, back, print;
    JTextField searchField;

    // Global variable to hold the Name to highlight
    private String highlightedEmpName = ""; // Changed from highlightedEmpId

    public ViewEmployee() {
        getContentPane().setBackground(new Color(229, 211, 200));

        JLabel heading = new JLabel("View Employee Details");
        heading.setBounds(350, 30, 400, 50);
        heading.setFont(new Font("serif", Font.BOLD, 25));
        add(heading);

        // Search components
        // --- CHANGE 1: Search label now says "Search by Employee Name" ---
        JLabel searchLabel = new JLabel("Search by Employee Name");
        searchLabel.setBounds(50, 80, 200, 30);
        searchLabel.setFont(new Font("serif", Font.BOLD, 15));
        add(searchLabel);
        // ------------------------------------------------------------------

        searchField = new JTextField();
        searchField.setBounds(250, 80, 150, 30);
        add(searchField);

        search = new JButton("Search");
        search.setBounds(420, 80, 80, 30);
        search.addActionListener(this);
        search.setBackground(new Color(30, 144, 254));
        search.setForeground(Color.WHITE);
        add(search);

        // Button to clear highlight and show all
        showAll = new JButton("Show All");
        showAll.setBounds(510, 80, 90, 30);
        showAll.addActionListener(this);
        showAll.setBackground(new Color(50, 50, 50));
        showAll.setForeground(Color.WHITE);
        add(showAll);

        print = new JButton("Print");
        print.setBounds(610, 80, 80, 30);
        print.addActionListener(this);
        print.setBackground(new Color(102, 178, 255));
        print.setForeground(Color.BLACK);
        add(print);

        back = new JButton("Back");
        back.setBounds(700, 80, 80, 30);
        back.addActionListener(this);
        back.setBackground(new Color(255, 99, 71));
        back.setForeground(Color.WHITE);
        add(back);

        // Table setup
        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 150, 810, 350);
        add(sp);

        // Load all data into the table model
        loadAllEmployeeData();

        // Apply custom cell renderer for highlighting
        applyHighlighter();

        setSize(850, 550);
        setLocation(350, 100);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to Load all data
    private void loadAllEmployeeData() {
        conn c = null;
        ResultSet rs = null;
        try {
            c = new conn();
            String query = "SELECT * FROM employee";
            rs = c.statement.executeQuery(query);

            DefaultTableModel tableModel = createTableModel(rs);
            table.setModel(tableModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, c);
        }
    }

    // Helper method to create TableModel from ResultSet
    private DefaultTableModel createTableModel(ResultSet rs) throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();
        // Column Headers (Name is at index 0, Employee ID is at 10)
        String[] columns = {"Name", "Father's Name", "Date of Birth", "Salary", "Address", "Phone", "Email", "Aadhar", "Education", "Designation", "Employee ID"};
        tableModel.setColumnIdentifiers(columns);

        while (rs.next()) {
            Object[] row = new Object[11];
            row[0] = rs.getString("name"); // Name is at index 0
            row[1] = rs.getString("fname");
            row[2] = rs.getString("dob");
            row[3] = rs.getString("salary");
            row[4] = rs.getString("address");
            row[5] = rs.getString("phone");
            row[6] = rs.getString("email");
            row[7] = rs.getString("aadhar");
            row[8] = rs.getString("education");
            row[9] = rs.getString("designation");
            row[10] = rs.getString("empid");
            tableModel.addRow(row);
        }
        return tableModel;
    }

    // Helper method to close database resources
    private void closeResources(ResultSet rs, conn c) {
        try {
            if (rs != null) rs.close();
            if (c != null && c.connection != null) c.connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // --- CHANGE 2: Highlighting Logic now checks the Name column (index 0) ---
    private void applyHighlighter() {
        // Apply custom renderer to all columns
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Get the Employee Name from the Name column (index 0)
                Object empNameValue = table.getModel().getValueAt(row, 0);
                String currentRowEmpName = (empNameValue != null) ? empNameValue.toString() : "";

                // Check if the current row's Name matches the Name to be highlighted
                if (!highlightedEmpName.isEmpty() && currentRowEmpName.toLowerCase().contains(highlightedEmpName.toLowerCase())) {
                    // Highlight color (Yellow)
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.BLACK);
                } else if (isSelected) {
                    // Default selection color
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    // Default colors for non-selected rows
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            String searchName = searchField.getText().trim();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Employee Name to search.");
                highlightedEmpName = "";
            } else {
                // Check if the Name exists in the currently loaded data
                boolean found = false;
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // --- CHANGE 3: Loop checks the Name column (index 0) for a match ---
                for (int i = 0; i < model.getRowCount(); i++) {
                    String empName = model.getValueAt(i, 0).toString();
                    if (empName.toLowerCase().contains(searchName.toLowerCase())) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Store the search term to highlight all matching rows
                    highlightedEmpName = searchName;
                } else {
                    highlightedEmpName = "";
                    JOptionPane.showMessageDialog(this, "Employee Name '" + searchName + "' not found in the list.");
                }
            }
            // Repaint the table to apply the highlight/clear the highlight
            table.repaint();

        } else if (e.getSource() == showAll) {
            // Clear the highlight variable and repaint
            highlightedEmpName = "";
            table.repaint();
            searchField.setText("");

        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception E) {
                E.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}