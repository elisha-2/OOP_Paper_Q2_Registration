package oop.paper.q2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;

public class StudentRegistrationForm extends JFrame {

    private JTextField txtFirstName, txtLastName;
    private JTextField txtEmail, txtConfirmEmail;
    private JPasswordField txtPassword, txtConfirmPassword;

    private JComboBox<Integer> cmbYear;
    private JComboBox<String> cmbMonth;
    private JComboBox<Integer> cmbDay;

    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;

    private JComboBox<String> cmbDepartment;

    private JButton btnSubmit, btnCancel;

    private JTextArea txtOutput;

    private JLabel lblErrorFirst, lblErrorLast, lblErrorEmail, lblErrorPass, lblErrorDob, lblErrorGender, lblErrorDept;

    private final StudentCsvService csvService = new StudentCsvService("students.csv");

    public StudentRegistrationForm() {
        setTitle("New Student Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 520);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(1, 2, 10, 10));

        add(buildLeftPanel());
        add(buildRightPanel());

        updateDays();
    }

    private JPanel buildLeftPanel() {

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("New Student Registration Form"));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        txtFirstName = new JTextField();
        lblErrorFirst = errorLabel();
        addRow(panel, c, 0, "First Name:", txtFirstName, lblErrorFirst);

        txtLastName = new JTextField();
        lblErrorLast = errorLabel();
        addRow(panel, c, 1, "Last Name:", txtLastName, lblErrorLast);

        txtEmail = new JTextField();
        lblErrorEmail = errorLabel();
        addRow(panel, c, 2, "Email:", txtEmail, lblErrorEmail);

        txtConfirmEmail = new JTextField();
        addRow(panel, c, 3, "Confirm Email:", txtConfirmEmail, new JLabel(""));

        txtPassword = new JPasswordField();
        lblErrorPass = errorLabel();
        addRow(panel, c, 4, "Password:", txtPassword, lblErrorPass);

        txtConfirmPassword = new JPasswordField();
        addRow(panel, c, 5, "Confirm Password:", txtConfirmPassword, new JLabel(""));

        JPanel dobPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        cmbYear = new JComboBox<>();
        cmbMonth = new JComboBox<>();
        cmbDay = new JComboBox<>();

        for (int y = LocalDate.now().getYear(); y >= 1950; y--) cmbYear.addItem(y);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        for (String m : months) cmbMonth.addItem(m);

        dobPanel.add(cmbYear);
        dobPanel.add(cmbMonth);
        dobPanel.add(cmbDay);

        lblErrorDob = errorLabel();

        cmbYear.addActionListener(e -> updateDays());
        cmbMonth.addActionListener(e -> updateDays());

        addRow(panel, c, 6, "DOB (Y/M/D):", dobPanel, lblErrorDob);

        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");

        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);

        lblErrorGender = errorLabel();
        addRow(panel, c, 7, "Gender:", genderPanel, lblErrorGender);

        cmbDepartment = new JComboBox<>(new String[]{
                "-- Select Department --",
                "Civil", "CSE", "Electrical", "E&C", "Mechanical"
        });

        lblErrorDept = errorLabel();
        addRow(panel, c, 8, "Department:", cmbDepartment, lblErrorDept);

        btnSubmit = new JButton("Submit");
        btnCancel = new JButton("Cancel");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.add(btnSubmit);
        btnPanel.add(btnCancel);

        c.gridx = 1;
        c.gridy = 9;
        panel.add(btnPanel, c);

        btnSubmit.addActionListener(e -> onSubmit());
        btnCancel.addActionListener(e -> onCancel());

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Your Data is Below:"));

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 13));

        panel.add(new JScrollPane(txtOutput), BorderLayout.CENTER);
        return panel;
    }

    private JLabel errorLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Arial", Font.PLAIN, 12));
        return lbl;
    }

    private void addRow(JPanel panel, GridBagConstraints c, int row, String label, Component field, Component error) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0.3;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.weightx = 0.7;
        panel.add(field, c);

        c.gridx = 2;
        c.weightx = 0.7;
        panel.add(error, c);
    }

    private void updateDays() {
        cmbDay.removeAllItems();

        int year = (int) cmbYear.getSelectedItem();
        int monthIndex = cmbMonth.getSelectedIndex() + 1;

        int daysInMonth = switch (monthIndex) {
            case 1,3,5,7,8,10,12 -> 31;
            case 4,6,9,11 -> 30;
            case 2 -> isLeapYear(year) ? 29 : 28;
            default -> 30;
        };

        for (int d = 1; d <= daysInMonth; d++) {
            cmbDay.addItem(d);
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private void clearErrors() {
        lblErrorFirst.setText(" ");
        lblErrorLast.setText(" ");
        lblErrorEmail.setText(" ");
        lblErrorPass.setText(" ");
        lblErrorDob.setText(" ");
        lblErrorGender.setText(" ");
        lblErrorDept.setText(" ");
    }

    private void onCancel() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtConfirmEmail.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        cmbDepartment.setSelectedIndex(0);
        genderGroup.clearSelection();
        txtOutput.setText("");
        clearErrors();
    }

    private void onSubmit() {

        clearErrors();
        StringBuilder summaryErrors = new StringBuilder();

        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String confirmEmail = txtConfirmEmail.getText().trim();

        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        int year = (int) cmbYear.getSelectedItem();
        int month = cmbMonth.getSelectedIndex() + 1;
        int day = (int) cmbDay.getSelectedItem();

        LocalDate dob = LocalDate.of(year, month, day);

        String gender = rbMale.isSelected() ? "M" : (rbFemale.isSelected() ? "F" : "");
        String dept = (String) cmbDepartment.getSelectedItem();

        boolean valid = true;

        if (first.isEmpty()) {
            lblErrorFirst.setText("Required");
            summaryErrors.append("- First Name is required\n");
            valid = false;
        }

        if (last.isEmpty()) {
            lblErrorLast.setText("Required");
            summaryErrors.append("- Last Name is required\n");
            valid = false;
        }

        if (email.isEmpty() || confirmEmail.isEmpty()) {
            lblErrorEmail.setText("Required");
            summaryErrors.append("- Email and Confirm Email are required\n");
            valid = false;
        } else if (!isValidEmail(email)) {
            lblErrorEmail.setText("Invalid email");
            summaryErrors.append("- Email format is invalid\n");
            valid = false;
        } else if (!email.equals(confirmEmail)) {
            lblErrorEmail.setText("Emails do not match");
            summaryErrors.append("- Email and Confirm Email must match\n");
            valid = false;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            lblErrorPass.setText("Required");
            summaryErrors.append("- Password and Confirm Password are required\n");
            valid = false;
        } else if (!isValidPassword(password)) {
            lblErrorPass.setText("8-20 chars, letter+digit");
            summaryErrors.append("- Password must be 8-20 chars and contain at least 1 letter and 1 digit\n");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            lblErrorPass.setText("Passwords do not match");
            summaryErrors.append("- Password and Confirm Password must match\n");
            valid = false;
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 16 || age > 60) {
            lblErrorDob.setText("Age must be 16-60");
            summaryErrors.append("- Age must be between 16 and 60\n");
            valid = false;
        }

        if (gender.isEmpty()) {
            lblErrorGender.setText("Select one");
            summaryErrors.append("- Gender must be selected\n");
            valid = false;
        }

        if (dept == null || dept.startsWith("--")) {
            lblErrorDept.setText("Select one");
            summaryErrors.append("- Department must be selected\n");
            valid = false;
        }

        if (!valid) {
            JOptionPane.showMessageDialog(
                    this,
                    summaryErrors.toString(),
                    "Validation Errors",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int currentYear = LocalDate.now().getYear();
        String id = csvService.generateId(currentYear);

        String dobString = dob.toString();
        String record = "ID: " + id + " | " + first + " " + last + " | " + gender
                + " | " + dept + " | " + dobString + " | " + email;

        txtOutput.append(record + "\n");

        try {
            csvService.appendRecord(record);
            JOptionPane.showMessageDialog(this, "Student saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to save: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 20) return false;
        boolean hasLetter = password.matches(".*[A-Za-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        return hasLetter && hasDigit;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRegistrationForm().setVisible(true));
    }
}
