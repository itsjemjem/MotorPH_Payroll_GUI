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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.IOException;

// Apache POI imports for Excel handling
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
    private String lastName;
    private String firstName;
    private int birthday;
    private String address;
    private String phoneNumber;
    private String sssNum;
    private String philhealthNum;
    private String tinNum;
    private String pagibigNum;
    private String status;
    private String position;
    private String immediateSupervisor;
    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double grossSemiMonthlyRate;
    private double hourlyRate;
    
    public Employee(int id, String lastName, String firstName, int birthday, String address,
                   String phoneNumber, String sssNum, String philhealthNum, String tinNum,
                   String pagibigNum, String status, String position, String immediateSupervisor,
                   double basicSalary, double riceSubsidy, double phoneAllowance,
                   double clothingAllowance, double grossSemiMonthlyRate, double hourlyRate, String password) {
        super(id, password);
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNum = sssNum;
        this.philhealthNum = philhealthNum;
        this.tinNum = tinNum;
        this.pagibigNum = pagibigNum;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
    }
    
    // Simple constructor for backward compatibility
    public Employee(int id, String name, int birthday, String password) {
        super(id, password);
        String[] nameParts = name.split(" ", 2);
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
        this.birthday = birthday;
        this.address = "";
        this.phoneNumber = "";
        this.sssNum = "";
        this.philhealthNum = "";
        this.tinNum = "";
        this.pagibigNum = "";
        this.status = "Active";
        this.position = "";
        this.immediateSupervisor = "";
        this.basicSalary = 0.0;
        this.riceSubsidy = 0.0;
        this.phoneAllowance = 0.0;
        this.clothingAllowance = 0.0;
        this.grossSemiMonthlyRate = 0.0;
        this.hourlyRate = 0.0;
    }
    
    public void view() {
        System.out.println("Employee ID: " + empId);
        System.out.println("Name: " + getFullName());
        System.out.println("Birthday: " + birthday);
        System.out.println("Position: " + position);
        System.out.println("Status: " + status);
        System.out.println("Login Status: " + loginStatus);
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Getters and setters
    public String getName() { return getFullName(); }
    public void setName(String name) { 
        String[] nameParts = name.split(" ", 2);
        this.firstName = nameParts[0];
        this.lastName = nameParts.length > 1 ? nameParts[1] : "";
    }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public int getBirthday() { return birthday; }
    public void setBirthday(int birthday) { this.birthday = birthday; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getSssNum() { return sssNum; }
    public void setSssNum(String sssNum) { this.sssNum = sssNum; }
    public String getPhilhealthNum() { return philhealthNum; }
    public void setPhilhealthNum(String philhealthNum) { this.philhealthNum = philhealthNum; }
    public String getTinNum() { return tinNum; }
    public void setTinNum(String tinNum) { this.tinNum = tinNum; }
    public String getPagibigNum() { return pagibigNum; }
    public void setPagibigNum(String pagibigNum) { this.pagibigNum = pagibigNum; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getImmediateSupervisor() { return immediateSupervisor; }
    public void setImmediateSupervisor(String immediateSupervisor) { this.immediateSupervisor = immediateSupervisor; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public double getRiceSubsidy() { return riceSubsidy; }
    public void setRiceSubsidy(double riceSubsidy) { this.riceSubsidy = riceSubsidy; }
    public double getPhoneAllowance() { return phoneAllowance; }
    public void setPhoneAllowance(double phoneAllowance) { this.phoneAllowance = phoneAllowance; }
    public double getClothingAllowance() { return clothingAllowance; }
    public void setClothingAllowance(double clothingAllowance) { this.clothingAllowance = clothingAllowance; }
    public double getGrossSemiMonthlyRate() { return grossSemiMonthlyRate; }
    public void setGrossSemiMonthlyRate(double grossSemiMonthlyRate) { this.grossSemiMonthlyRate = grossSemiMonthlyRate; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
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
    
    public void loadEmployeesFromCSV(String filePath) {
        Path path = Paths.get(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            // Skip header row
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 19) { // Updated to match all CSV columns
                    try {
                        int id = Integer.parseInt(values[0].trim());
                        String lastName = values[1].trim();
                        String firstName = values[2].trim();
                        int birthday = Integer.parseInt(values[3].trim());
                        String address = values[4].trim();
                        String phoneNumber = values[5].trim();
                        String sssNum = values[6].trim();
                        String philhealthNum = values[7].trim();
                        String tinNum = values[8].trim();
                        String pagibigNum = values[9].trim();
                        String status = values[10].trim();
                        String position = values[11].trim();
                        String immediateSupervisor = values[12].trim();
                        double basicSalary = Double.parseDouble(values[13].trim());
                        double riceSubsidy = Double.parseDouble(values[14].trim());
                        double phoneAllowance = Double.parseDouble(values[15].trim());
                        double clothingAllowance = Double.parseDouble(values[16].trim());
                        double grossSemiMonthlyRate = Double.parseDouble(values[17].trim());
                        double hourlyRate = Double.parseDouble(values[18].trim());
                        
                        // Use a default password (you might want to add this as a column in CSV)
                        String password = "pass" + id;
                        
                        Employee emp = new Employee(id, lastName, firstName, birthday, address,
                                                  phoneNumber, sssNum, philhealthNum, tinNum,
                                                  pagibigNum, status, position, immediateSupervisor,
                                                  basicSalary, riceSubsidy, phoneAllowance,
                                                  clothingAllowance, grossSemiMonthlyRate, hourlyRate, password);
                        this.add(emp);
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error loading employee data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadEmployeesFromExcel(String filePath, String sheetName) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Workbook workbook;
            
            // Determine file type and create appropriate workbook
            if (filePath.toLowerCase().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            } else if (filePath.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(fileInputStream);
            } else {
                throw new IllegalArgumentException("Unsupported file format. Please use .xlsx or .xls files.");
            }
            
            // Get the specified sheet
            Sheet sheet;
            if (sheetName != null && !sheetName.isEmpty()) {
                sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    // If sheet name not found, show available sheets
                    StringBuilder availableSheets = new StringBuilder("Available sheets:\n");
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        availableSheets.append("- ").append(workbook.getSheetName(i)).append("\n");
                    }
                    throw new IllegalArgumentException("Sheet '" + sheetName + "' not found.\n" + availableSheets.toString());
                }
            } else {
                // Use first sheet if no sheet name specified
                sheet = workbook.getSheetAt(0);
            }
            
            // Read data from sheet
            boolean firstRow = true;
            for (Row row : sheet) {
                // Skip header row
                if (firstRow) {
                    firstRow = false;
                    continue;
                }
                
                // Check if row has enough cells
                if (row.getLastCellNum() >= 19) {
                    try {
                        int id = (int) getNumericCellValue(row.getCell(0));
                        String lastName = getStringCellValue(row.getCell(1));
                        String firstName = getStringCellValue(row.getCell(2));
                        int birthday = (int) getNumericCellValue(row.getCell(3));
                        String address = getStringCellValue(row.getCell(4));
                        String phoneNumber = getStringCellValue(row.getCell(5));
                        String sssNum = getStringCellValue(row.getCell(6));
                        String philhealthNum = getStringCellValue(row.getCell(7));
                        String tinNum = getStringCellValue(row.getCell(8));
                        String pagibigNum = getStringCellValue(row.getCell(9));
                        String status = getStringCellValue(row.getCell(10));
                        String position = getStringCellValue(row.getCell(11));
                        String immediateSupervisor = getStringCellValue(row.getCell(12));
                        double basicSalary = getNumericCellValue(row.getCell(13));
                        double riceSubsidy = getNumericCellValue(row.getCell(14));
                        double phoneAllowance = getNumericCellValue(row.getCell(15));
                        double clothingAllowance = getNumericCellValue(row.getCell(16));
                        double grossSemiMonthlyRate = getNumericCellValue(row.getCell(17));
                        double hourlyRate = getNumericCellValue(row.getCell(18));
                        
                        // Skip empty rows
                        if (id == 0 || firstName.isEmpty()) {
                            continue;
                        }
                        
                        // Use a default password
                        String password = "pass" + id;
                        
                        // Create and add employee
                        Employee emp = new Employee(id, lastName, firstName, birthday, address,
                                                  phoneNumber, sssNum, philhealthNum, tinNum,
                                                  pagibigNum, status, position, immediateSupervisor,
                                                  basicSalary, riceSubsidy, phoneAllowance,
                                                  clothingAllowance, grossSemiMonthlyRate, hourlyRate, password);
                        this.add(emp);
                        
                    } catch (Exception e) {
                        System.err.println("Error reading row " + row.getRowNum() + ": " + e.getMessage());
                        // Continue with next row instead of stopping
                    }
                }
            }
            
            workbook.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error loading Excel file: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, 
                e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method to get string value from cell regardless of cell type
    private String getStringCellValue(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    // Helper method to get numeric value from cell
    private double getNumericCellValue(Cell cell) {
        if (cell == null) return 0;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    return 0;
                }
            default:
                return 0;
        }
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
    
    public void loadEmployeesFromCSV(String filePath) {
        admin.loadEmployeesFromCSV(filePath);
    }
    
    public void loadEmployeesFromExcel(String filePath, String sheetName) {
        admin.loadEmployeesFromExcel(filePath, sheetName);
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
    
    public MotorPH_Payroll_GUI() {
        payrollSystem = new PayrollSystem();
        
        // Load data from excel file
         String excelPath = "/Users/jemwagas/NetBeansProjects/Practice/src/main/java/com/mycompany/practice/MotorPH Employee Data.xlsx";
         String sheetName = "Employee Details";
         payrollSystem.loadEmployeesFromExcel(excelPath, sheetName);
        
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
        panel.setBackground(new java.awt.Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title
        JLabel titleLabel = new JLabel("Payroll Management System", JLabel.CENTER);
        titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        titleLabel.setForeground(new java.awt.Color(25, 25, 112));
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
        loginButton.setBackground(new java.awt.Color(70, 130, 180));
        loginButton.setForeground(java.awt.Color.WHITE);
        loginButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
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
        headerPanel.setBackground(new java.awt.Color(70, 130, 180));
        JLabel headerLabel = new JLabel("Motor PH's Payroll Management Dashboard");
        headerLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        headerLabel.setForeground(java.awt.Color.WHITE);
        headerPanel.add(headerLabel);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new java.awt.Color(220, 20, 60));
        logoutButton.setForeground(java.awt.Color.WHITE);
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
        
        // Employee table with all CSV headers
        String[] columnNames = {
            "Employee ID", "Last Name", "First Name", "Birthday", "Address", 
            "Phone Number", "SSS Number", "Philhealth Number", "TIN Number", 
            "Pag-ibig Number", "Status", "Position", "Immediate Supervisor", 
            "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance", 
            "Gross Semi-Monthly Rate", "Hourly Rate"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(tableModel);
        
        // Set column widths for better display
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Employee ID
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Last Name
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // First Name
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Birthday
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Address
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(120); // Phone
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(100); // SSS
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(120); // Philhealth
        employeeTable.getColumnModel().getColumn(8).setPreferredWidth(100); // TIN
        employeeTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Pag-ibig
        employeeTable.getColumnModel().getColumn(10).setPreferredWidth(80); // Status
        employeeTable.getColumnModel().getColumn(11).setPreferredWidth(120); // Position
        employeeTable.getColumnModel().getColumn(12).setPreferredWidth(150); // Supervisor
        employeeTable.getColumnModel().getColumn(13).setPreferredWidth(100); // Basic Salary
        employeeTable.getColumnModel().getColumn(14).setPreferredWidth(100); // Rice Subsidy
        employeeTable.getColumnModel().getColumn(15).setPreferredWidth(120); // Phone Allowance
        employeeTable.getColumnModel().getColumn(16).setPreferredWidth(130); // Clothing Allowance
        employeeTable.getColumnModel().getColumn(17).setPreferredWidth(150); // Gross Semi-Monthly
        employeeTable.getColumnModel().getColumn(18).setPreferredWidth(100); // Hourly Rate
        
        refreshEmployeeTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
                emp.getLastName(),
                emp.getFirstName(),
                emp.getBirthday(),
                emp.getAddress(),
                emp.getPhoneNumber(),
                emp.getSssNum(),
                emp.getPhilhealthNum(),
                emp.getTinNum(),
                emp.getPagibigNum(),
                emp.getStatus(),
                emp.getPosition(),
                emp.getImmediateSupervisor(),
                String.format("₱%.2f", emp.getBasicSalary()),
                String.format("₱%.2f", emp.getRiceSubsidy()),
                String.format("₱%.2f", emp.getPhoneAllowance()),
                String.format("₱%.2f", emp.getClothingAllowance()),
                String.format("₱%.2f", emp.getGrossSemiMonthlyRate()),
                String.format("₱%.2f", emp.getHourlyRate())
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showAddEmployeeDialog(DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(this, "Add Employee", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create tabbed pane for better organization
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Basic Info Tab
        JPanel basicPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        basicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField idField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField birthdayField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        
        basicPanel.add(new JLabel("Employee ID:"));
        basicPanel.add(idField);
        basicPanel.add(new JLabel("Last Name:"));
        basicPanel.add(lastNameField);
        basicPanel.add(new JLabel("First Name:"));
        basicPanel.add(firstNameField);
        basicPanel.add(new JLabel("Birthday (YYYYMMDD):"));
        basicPanel.add(birthdayField);
        basicPanel.add(new JLabel("Address:"));
        basicPanel.add(addressField);
        basicPanel.add(new JLabel("Phone Number:"));
        basicPanel.add(phoneField);
        
        // Government Numbers Tab
        JPanel govPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        govPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField sssField = new JTextField();
        JTextField philhealthField = new JTextField();
        JTextField tinField = new JTextField();
        JTextField pagibigField = new JTextField();
        
        govPanel.add(new JLabel("SSS Number:"));
        govPanel.add(sssField);
        govPanel.add(new JLabel("Philhealth Number:"));
        govPanel.add(philhealthField);
        govPanel.add(new JLabel("TIN Number:"));
        govPanel.add(tinField);
        govPanel.add(new JLabel("Pag-ibig Number:"));
        govPanel.add(pagibigField);
        
        // Employment Info Tab
        JPanel empPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        empPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField statusField = new JTextField("Active");
        JTextField positionField = new JTextField();
        JTextField supervisorField = new JTextField();
        
        empPanel.add(new JLabel("Status:"));
        empPanel.add(statusField);
        empPanel.add(new JLabel("Position:"));
        empPanel.add(positionField);
        empPanel.add(new JLabel("Immediate Supervisor:"));
        empPanel.add(supervisorField);
        
        // Salary Info Tab
        JPanel salaryPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        salaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField basicSalaryField = new JTextField("0.00");
        JTextField riceSubsidyField = new JTextField("0.00");
        JTextField phoneAllowanceField = new JTextField("0.00");
        JTextField clothingAllowanceField = new JTextField("0.00");
        JTextField grossSemiMonthlyField = new JTextField("0.00");
        JTextField hourlyRateField = new JTextField("0.00");
        
        salaryPanel.add(new JLabel("Basic Salary:"));
        salaryPanel.add(basicSalaryField);
        salaryPanel.add(new JLabel("Rice Subsidy:"));
        salaryPanel.add(riceSubsidyField);
        salaryPanel.add(new JLabel("Phone Allowance:"));
        salaryPanel.add(phoneAllowanceField);
        salaryPanel.add(new JLabel("Clothing Allowance:"));
        salaryPanel.add(clothingAllowanceField);
        salaryPanel.add(new JLabel("Gross Semi-Monthly Rate:"));
        salaryPanel.add(grossSemiMonthlyField);
        salaryPanel.add(new JLabel("Hourly Rate:"));
        salaryPanel.add(hourlyRateField);
        
        tabbedPane.addTab("Basic Info", basicPanel);
        tabbedPane.addTab("Government IDs", govPanel);
        tabbedPane.addTab("Employment", empPanel);
        tabbedPane.addTab("Salary", salaryPanel);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String lastName = lastNameField.getText().trim();
                String firstName = firstNameField.getText().trim();
                int birthday = Integer.parseInt(birthdayField.getText().trim());
                String address = addressField.getText().trim();
                String phoneNumber = phoneField.getText().trim();
                String sssNum = sssField.getText().trim();
                String philhealthNum = philhealthField.getText().trim();
                String tinNum = tinField.getText().trim();
                String pagibigNum = pagibigField.getText().trim();
                String status = statusField.getText().trim();
                String position = positionField.getText().trim();
                String supervisor = supervisorField.getText().trim();
                double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
                double riceSubsidy = Double.parseDouble(riceSubsidyField.getText().trim());
                double phoneAllowance = Double.parseDouble(phoneAllowanceField.getText().trim());
                double clothingAllowance = Double.parseDouble(clothingAllowanceField.getText().trim());
                double grossSemiMonthly = Double.parseDouble(grossSemiMonthlyField.getText().trim());
                double hourlyRate = Double.parseDouble(hourlyRateField.getText().trim());
                
                String password = "pass" + id; // Default password
                
                Employee newEmp = new Employee(id, lastName, firstName, birthday, address,
                                             phoneNumber, sssNum, philhealthNum, tinNum,
                                             pagibigNum, status, position, supervisor,
                                             basicSalary, riceSubsidy, phoneAllowance,
                                             clothingAllowance, grossSemiMonthly, hourlyRate, password);
                
                payrollSystem.getAdmin().add(newEmp);
                refreshEmployeeTable(tableModel);
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for numeric fields!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);
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
            dialog.setSize(500, 400);
            dialog.setLocationRelativeTo(this);
            
            JPanel mainPanel = new JPanel(new BorderLayout());
            
            // Create tabbed pane for better organization
            JTabbedPane tabbedPane = new JTabbedPane();
            
            // Basic Info Tab
            JPanel basicPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            basicPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField lastNameField = new JTextField(emp.getLastName());
            JTextField firstNameField = new JTextField(emp.getFirstName());
            JTextField birthdayField = new JTextField(String.valueOf(emp.getBirthday()));
            JTextField addressField = new JTextField(emp.getAddress());
            JTextField phoneField = new JTextField(emp.getPhoneNumber());
            
            basicPanel.add(new JLabel("Last Name:"));
            basicPanel.add(lastNameField);
            basicPanel.add(new JLabel("First Name:"));
            basicPanel.add(firstNameField);
            basicPanel.add(new JLabel("Birthday (YYYYMMDD):"));
            basicPanel.add(birthdayField);
            basicPanel.add(new JLabel("Address:"));
            basicPanel.add(addressField);
            basicPanel.add(new JLabel("Phone Number:"));
            basicPanel.add(phoneField);
            
            // Government Numbers Tab
            JPanel govPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            govPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField sssField = new JTextField(emp.getSssNum());
            JTextField philhealthField = new JTextField(emp.getPhilhealthNum());
            JTextField tinField = new JTextField(emp.getTinNum());
            JTextField pagibigField = new JTextField(emp.getPagibigNum());
            
            govPanel.add(new JLabel("SSS Number:"));
            govPanel.add(sssField);
            govPanel.add(new JLabel("Philhealth Number:"));
            govPanel.add(philhealthField);
            govPanel.add(new JLabel("TIN Number:"));
            govPanel.add(tinField);
            govPanel.add(new JLabel("Pag-ibig Number:"));
            govPanel.add(pagibigField);
            
            // Employment Info Tab
            JPanel empPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            empPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField statusField = new JTextField(emp.getStatus());
            JTextField positionField = new JTextField(emp.getPosition());
            JTextField supervisorField = new JTextField(emp.getImmediateSupervisor());
            
            empPanel.add(new JLabel("Status:"));
            empPanel.add(statusField);
            empPanel.add(new JLabel("Position:"));
            empPanel.add(positionField);
            empPanel.add(new JLabel("Immediate Supervisor:"));
            empPanel.add(supervisorField);
            
            // Salary Info Tab
            JPanel salaryPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            salaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextField basicSalaryField = new JTextField(String.valueOf(emp.getBasicSalary()));
            JTextField riceSubsidyField = new JTextField(String.valueOf(emp.getRiceSubsidy()));
            JTextField phoneAllowanceField = new JTextField(String.valueOf(emp.getPhoneAllowance()));
            JTextField clothingAllowanceField = new JTextField(String.valueOf(emp.getClothingAllowance()));
            JTextField grossSemiMonthlyField = new JTextField(String.valueOf(emp.getGrossSemiMonthlyRate()));
            JTextField hourlyRateField = new JTextField(String.valueOf(emp.getHourlyRate()));
            
            salaryPanel.add(new JLabel("Basic Salary:"));
            salaryPanel.add(basicSalaryField);
            salaryPanel.add(new JLabel("Rice Subsidy:"));
            salaryPanel.add(riceSubsidyField);
            salaryPanel.add(new JLabel("Phone Allowance:"));
            salaryPanel.add(phoneAllowanceField);
            salaryPanel.add(new JLabel("Clothing Allowance:"));
            salaryPanel.add(clothingAllowanceField);
            salaryPanel.add(new JLabel("Gross Semi-Monthly Rate:"));
            salaryPanel.add(grossSemiMonthlyField);
            salaryPanel.add(new JLabel("Hourly Rate:"));
            salaryPanel.add(hourlyRateField);
            
            tabbedPane.addTab("Basic Info", basicPanel);
            tabbedPane.addTab("Government IDs", govPanel);
            tabbedPane.addTab("Employment", empPanel);
            tabbedPane.addTab("Salary", salaryPanel);
            
            mainPanel.add(tabbedPane, BorderLayout.CENTER);
            
            JButton updateButton = new JButton("Update");
            updateButton.addActionListener(e -> {
                try {
                    emp.setLastName(lastNameField.getText().trim());
                    emp.setFirstName(firstNameField.getText().trim());
                    emp.setBirthday(Integer.parseInt(birthdayField.getText().trim()));
                    emp.setAddress(addressField.getText().trim());
                    emp.setPhoneNumber(phoneField.getText().trim());
                    emp.setSssNum(sssField.getText().trim());
                    emp.setPhilhealthNum(philhealthField.getText().trim());
                    emp.setTinNum(tinField.getText().trim());
                    emp.setPagibigNum(pagibigField.getText().trim());
                    emp.setStatus(statusField.getText().trim());
                    emp.setPosition(positionField.getText().trim());
                    emp.setImmediateSupervisor(supervisorField.getText().trim());
                    emp.setBasicSalary(Double.parseDouble(basicSalaryField.getText().trim()));
                    emp.setRiceSubsidy(Double.parseDouble(riceSubsidyField.getText().trim()));
                    emp.setPhoneAllowance(Double.parseDouble(phoneAllowanceField.getText().trim()));
                    emp.setClothingAllowance(Double.parseDouble(clothingAllowanceField.getText().trim()));
                    emp.setGrossSemiMonthlyRate(Double.parseDouble(grossSemiMonthlyField.getText().trim()));
                    emp.setHourlyRate(Double.parseDouble(hourlyRateField.getText().trim()));
                    
                    refreshEmployeeTable(tableModel);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for numeric fields!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dialog.dispose());
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(updateButton);
            buttonPanel.add(cancelButton);
            
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(mainPanel);
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
                "PHP" + payslip.getSalary(),
                "PHP" + payslip.getDeductions(),
                "PHP" + payslip.getNetSalary()
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void showGeneratePayslipDialog(DefaultTableModel tableModel) {
        JDialog dialog = new JDialog(this, "Generate Payslip", true);
        dialog.setSize(300, 180);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
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
                attendance.getLoginDateTime().format(formatter) : "No log in";
            String logoutTime = attendance.getLogoutDateTime() != null ? 
                attendance.getLogoutDateTime().format(formatter) : "No log out";
            
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