/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.motorph_payroll_gui;

/**
 *
 * @author jemwagas
 */
// Import classes from Apache POI needed to work with Excel files.
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

// User class - base class for authentication
class User {
    protected int empId;
    protected String password;
    protected String loginStatus;
    
    public User(int empId, String password) {
        this.empId = empId;
        this.password = password;
        this.loginStatus = "Logged Out";
    }
    
    public boolean verifyLogin(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            this.loginStatus = "Logged In";
            return true;
        }
        return false;
    }
    
    public void logout() {
        this.loginStatus = "Logged Out";
    }
    
    // Getters and setters
    public int getEmpId() { return empId; }
    public String getLoginStatus() { return loginStatus; }
}

// Employee class - extends User
class Employee extends User {
    private String name;
    private int birthday;
    
    public Employee(int id, String name, int birthday, String password) {
        super(id, password);
        this.name = name;
        this.birthday = birthday;
    }
    
    public void view() {
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + name);
        System.out.println("Birthday: " + birthday);
        System.out.println("Login Status: " + loginStatus);
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBirthday() { return birthday; }
    public void setBirthday(int birthday) { this.birthday = birthday; }
}

// Admin class - extends User
class Admin extends User {
    private String adminName;
    private String email;
    private List<Employee> employees;
    
    public Admin(int empId, String adminName, String email, String password) {
        super(empId, password);
        this.adminName = adminName;
        this.email = email;
        this.employees = new ArrayList<>();
    }
    
    public void add(Employee employee) {
        employees.add(employee);
    }
    
    public Employee search(int empId) {
        for (Employee emp : employees) {
            if (emp.getEmpId() == empId) {
                return emp;
            }
        }
        return null;
    }
    
    public void edit(int empId, String newName, int newBirthday) {
        Employee emp = search(empId);
        if (emp != null) {
            emp.setName(newName);
            emp.setBirthday(newBirthday);
        }
    }
    
    public boolean delete(int empId) {
        Employee emp = search(empId);
        if (emp != null) {
            employees.remove(emp);
            return true;
        }
        return false;
    }
    
    // Getters and setters
    public String getAdminName() { return adminName; }
    public String getEmail() { return email; }
    public List<Employee> getEmployees() { return employees; }
}

// Payslip class - composition with Employee
class Payslip {
    private int id;
    private String name;
    private int birthday;
    private int salary;
    private int deductions;
    private Employee employee;
    
    public Payslip(Employee employee, int salary, int deductions) {
        this.employee = employee;
        this.id = employee.getEmpId();
        this.name = employee.getName();
        this.birthday = employee.getBirthday();
        this.salary = salary;
        this.deductions = deductions;
    }
    
    public String getPayslipDetails() {
        return String.format("Employee ID: %d\nName: %s\nBirthday: %d\nGross Salary: $%d\nDeductions: $%d\nNet Salary: $%d", 
                           id, name, birthday, salary, deductions, (salary - deductions));
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getSalary() { return salary; }
    public int getDeductions() { return deductions; }
    public int getNetSalary() { return salary - deductions; }
}

// Attendance class - composition with Employee
class Attendance {
    private int id;
    private String name;
    private int birthday;
    private LocalDateTime loginDateTime;
    private LocalDateTime logoutDateTime;
    private Employee employee;
    
    public Attendance(Employee employee) {
        this.employee = employee;
        this.id = employee.getEmpId();
        this.name = employee.getName();
        this.birthday = employee.getBirthday();
    }
    
    public void update(LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.loginDateTime = loginTime;
        this.logoutDateTime = logoutTime;
    }
    
    public String getAttendanceDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String loginStr = loginDateTime != null ? loginDateTime.format(formatter) : "Not logged in";
        String logoutStr = logoutDateTime != null ? logoutDateTime.format(formatter) : "Not logged out";
        
        StringBuilder details = new StringBuilder();
        details.append("Employee ID: ").append(id).append("\n");
        details.append("Name: ").append(name).append("\n");
        details.append("Login Time: ").append(loginStr).append("\n");
        details.append("Logout Time: ").append(logoutStr).append("\n");
        
        if (loginDateTime != null && logoutDateTime != null) {
            long hoursWorked = java.time.Duration.between(loginDateTime, logoutDateTime).toHours();
            details.append("Hours Worked: ").append(hoursWorked);
        }
        
        return details.toString();
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDateTime getLoginDateTime() { return loginDateTime; }
    public LocalDateTime getLogoutDateTime() { return logoutDateTime; }
}

// PayrollSystem class - main system logic
class PayrollSystem {
    private Admin admin;
    private List<Payslip> payslips;
    private List<Attendance> attendanceRecords;
    
    public PayrollSystem() {
        this.admin = new Admin(1, "System Admin", "admin@company.com", "admin123");
        this.payslips = new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
        
        // Add sample employees
        Employee emp1 = new Employee(101, "John Doe", 19900515, "pass123");
        Employee emp2 = new Employee(102, "Jane Smith", 19851220, "pass456");
        admin.add(emp1);
        admin.add(emp2);
    }
    
    public Payslip generatePayslip(int empId, int salary, int deductions) {
        Employee emp = admin.search(empId);
        if (emp != null) {
            Payslip payslip = new Payslip(emp, salary, deductions);
            payslips.add(payslip);
            return payslip;
        }
        return null;
    }
    
    public Attendance recordAttendance(int empId, LocalDateTime loginTime, LocalDateTime logoutTime) {
        Employee emp = admin.search(empId);
        if (emp != null) {
            Attendance attendance = new Attendance(emp);
            attendance.update(loginTime, logoutTime);
            attendanceRecords.add(attendance);
            return attendance;
        }
        return null;
    }
    
    public Admin getAdmin() { return admin; }
    public List<Payslip> getPayslips() { return payslips; }
    public List<Attendance> getAttendanceRecords() { return attendanceRecords; }
}

// Main GUI Application
public class MotorPH_Payroll_GUI extends JFrame {
    private PayrollSystem payrollSystem;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private boolean isLoggedIn = false;
    
    public MotorPH_Payroll_GUI () {
        payrollSystem = new PayrollSystem();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Motor PH's Employee Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create card layout for different panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create panels
        JPanel loginPanel = createLoginPanel();
        JPanel dashboardPanel = createDashboardPanel();
        
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(dashboardPanel, "DASHBOARD");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Payroll Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 30, 0);
        panel.add(titleLabel, gbc);
        
        // Username field
        gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Admin ID:"), gbc);
        
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(usernameField, gbc);
        
        // Password field
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(passwordField, gbc);
        
        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Nunito", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(loginButton, gbc);
        
        // Login action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.equals("1") && payrollSystem.getAdmin().verifyLogin(password)) {
                isLoggedIn = true;
                cardLayout.show(mainPanel, "DASHBOARD");
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("Motor PH's Payroll Management Dashboard");
        headerLabel.setFont(new Font("Nunito", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 20, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> {
            payrollSystem.getAdmin().logout();
            isLoggedIn = false;
            cardLayout.show(mainPanel, "LOGIN");
        });
        headerPanel.add(logoutButton);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Employees", createEmployeePanel());
        tabbedPane.addTab("Payslips", createPayslipPanel());
        tabbedPane.addTab("Attendance", createAttendancePanel());
        
        panel.add(tabbedPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Employee table
        String[] columnNames = {"ID", "Name", "Birthday", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(tableModel);
        refreshEmployeeTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddEmployeeDialog(tableModel));
        editButton.addActionListener(e -> showEditEmployeeDialog(employeeTable, tableModel));
        deleteButton.addActionListener(e -> deleteEmployee(employeeTable, tableModel));
        refreshButton.addActionListener(e -> refreshEmployeeTable(tableModel));
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createPayslipPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Payslip table
        String[] columnNames = {"Employee ID", "Name", "Gross Salary", "Deductions", "Net Salary"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable payslipTable = new JTable(tableModel);
        refreshPayslipTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(payslipTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton generateButton = new JButton("Generate Payslip");
        JButton viewButton = new JButton("View Details");
        JButton refreshButton = new JButton("Refresh");
        
        generateButton.addActionListener(e -> showGeneratePayslipDialog(tableModel));
        viewButton.addActionListener(e -> showPayslipDetails(payslipTable));
        refreshButton.addActionListener(e -> refreshPayslipTable(tableModel));
        
        buttonPanel.add(generateButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Attendance table
        String[] columnNames = {"Employee ID", "Name", "Login Time", "Logout Time", "Hours Worked"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable attendanceTable = new JTable(tableModel);
        refreshAttendanceTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton recordButton = new JButton("Record Attendance");
        JButton viewButton = new JButton("View Details");
        JButton refreshButton = new JButton("Refresh");
        
        recordButton.addActionListener(e -> showRecordAttendanceDialog(tableModel));
        viewButton.addActionListener(e -> showAttendanceDetails(attendanceTable));
        refreshButton.addActionListener(e -> refreshAttendanceTable(tableModel));
        
        buttonPanel.add(recordButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Helper methods for employee management
    private void refreshEmployeeTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Employee emp : payrollSystem.getAdmin().getEmployees()) {
            Object[] rowData = {
                emp.getEmpId(),
                emp.getName(),
                emp.getBirthday(),
                emp.getLoginStatus()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showAddEmployeeDialog(DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(this, "Add Employee", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField birthdayField = new JTextField();
        JTextField passwordField = new JTextField();
        
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Birthday (YYYYMMDD):"));
        panel.add(birthdayField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int birthday = Integer.parseInt(birthdayField.getText());
                String password = passwordField.getText();
                
                Employee newEmp = new Employee(id, name, birthday, password);
                payrollSystem.getAdmin().add(newEmp);
                refreshEmployeeTable(tableModel);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for ID and Birthday!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showEditEmployeeDialog(JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int empId = (Integer) table.getValueAt(selectedRow, 0);
        Employee emp = payrollSystem.getAdmin().search(empId);
        
        if (emp != null) {
            JDialog dialog = new JDialog(this, "Edit Employee", true);
            dialog.setSize(300, 150);
            dialog.setLocationRelativeTo(this);
            
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField nameField = new JTextField(emp.getName());
            JTextField birthdayField = new JTextField(String.valueOf(emp.getBirthday()));
            
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Birthday (YYYYMMDD):"));
            panel.add(birthdayField);
            
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(e -> {
                try {
                    String name = nameField.getText();
                    int birthday = Integer.parseInt(birthdayField.getText());
                    
                    payrollSystem.getAdmin().edit(empId, name, birthday);
                    refreshEmployeeTable(tableModel);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a valid number for Birthday!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dialog.dispose());
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(updateButton);
            buttonPanel.add(cancelButton);
            
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);
        }
    }
    
    private void deleteEmployee(JTable table, DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int empId = (Integer) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (payrollSystem.getAdmin().delete(empId)) {
                refreshEmployeeTable(tableModel);
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employee!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Helper methods for payslip management
    private void refreshPayslipTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        for (Payslip payslip : payrollSystem.getPayslips()) {
            Object[] rowData = {
                payslip.getId(),
                payslip.getName(),
                "$" + payslip.getSalary(),
                "$" + payslip.getDeductions(),
                "$" + payslip.getNetSalary()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showGeneratePayslipDialog(DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(this, "Generate Payslip", true);
        dialog.setSize(300, 180);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField idField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField deductionsField = new JTextField();
        
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Gross Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Deductions:"));
        panel.add(deductionsField);
        
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int salary = Integer.parseInt(salaryField.getText());
                int deductions = Integer.parseInt(deductionsField.getText());
                
                Payslip payslip = payrollSystem.generatePayslip(id, salary, deductions);
                if (payslip != null) {
                    refreshPayslipTable(tableModel);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Payslip generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(generateButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showPayslipDetails(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a payslip to view!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int empId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        Payslip payslip = payrollSystem.getPayslips().stream()
            .filter(p -> p.getId() == empId)
            .findFirst()
            .orElse(null);
        
        if (payslip != null) {
            JOptionPane.showMessageDialog(this, payslip.getPayslipDetails(), "Payslip Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Helper methods for attendance management
    private void refreshAttendanceTable(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        for (Attendance attendance : payrollSystem.getAttendanceRecords()) {
            String loginTime = attendance.getLoginDateTime() != null ? 
                attendance.getLoginDateTime().format(formatter) : "Not logged in";
            String logoutTime = attendance.getLogoutDateTime() != null ? 
                attendance.getLogoutDateTime().format(formatter) : "Not logged out";
            
            long hoursWorked = 0;
            if (attendance.getLoginDateTime() != null && attendance.getLogoutDateTime() != null) {
                hoursWorked = java.time.Duration.between(attendance.getLoginDateTime(), attendance.getLogoutDateTime()).toHours();
            }
            
            Object[] rowData = {
                attendance.getId(),
                attendance.getName(),
                loginTime,
                logoutTime,
                hoursWorked + " hours"
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showRecordAttendanceDialog(DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(this, "Record Attendance", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField idField = new JTextField();
        JTextField loginHoursField = new JTextField("8");
        JTextField logoutHoursField = new JTextField("0");
        
        panel.add(new JLabel("Employee ID:"));
        panel.add(idField);
        panel.add(new JLabel("Login Hours Ago:"));
        panel.add(loginHoursField);
        panel.add(new JLabel("Logout Hours Ago:"));
        panel.add(logoutHoursField);
        panel.add(new JLabel("(0 = now)"));
        panel.add(new JLabel(""));
        
        JButton recordButton = new JButton("Record");
        recordButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int loginHours = Integer.parseInt(loginHoursField.getText());
                int logoutHours = Integer.parseInt(logoutHoursField.getText());
                
                LocalDateTime loginTime = LocalDateTime.now().minusHours(loginHours);
                LocalDateTime logoutTime = LocalDateTime.now().minusHours(logoutHours);
                
                Attendance attendance = payrollSystem.recordAttendance(id, loginTime, logoutTime);
                if (attendance != null) {
                    refreshAttendanceTable(tableModel);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Attendance recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(recordButton);
        buttonPanel.add(cancelButton);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void showAttendanceDetails(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an attendance record to view!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int empId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        Attendance attendance = payrollSystem.getAttendanceRecords().stream()
            .filter(a -> a.getId() == empId)
            .findFirst()
            .orElse(null);
        
        if (attendance != null) {
            JOptionPane.showMessageDialog(this, attendance.getAttendanceDetails(), "Attendance Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel with better error handling
        try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                // Continue with default look and feel
            }
        
        
        // Create and show GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MotorPH_Payroll_GUI gui = new MotorPH_Payroll_GUI();
                    gui.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, 
                        "Error starting application: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}