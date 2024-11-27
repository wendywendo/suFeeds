package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupFrame extends JFrame implements ActionListener {

    private JPanel formPanel, btnPanel, footerPanel;
    private JLabel lblTitle, lblUsername, lblPassword, lblConfirmPassword, lblFName, lblLName, copyrightMsg;
    private JTextField txtUsername, txtFName, txtLName;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnLogin, btnSignup;

    DBConnection dbConnection = new DBConnection();

    public SignupFrame() {

        setTitle("SU FEEDS");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null); // Center on screen

        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        lblTitle = new JLabel("SIGN UP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        formPanel.add(lblTitle, gbc);

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblUsername = new JLabel("Username: ");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblUsername, gbc);

        // Username text field
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        txtUsername = new JTextField(20);
        txtUsername.setPreferredSize(new Dimension(200, 30));
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtUsername, gbc);

        // FName Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblFName = new JLabel("First Name: ");
        lblFName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblFName, gbc);

        // FName text field
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        txtFName = new JTextField(20);
        txtFName.setPreferredSize(new Dimension(200, 30));
        txtFName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtFName, gbc);

        // LName Label
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        lblLName = new JLabel("Last Name: ");
        lblLName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblLName, gbc);

        // LName text field
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        txtLName = new JTextField(20);
        txtLName.setPreferredSize(new Dimension(200, 30));
        txtLName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtLName, gbc);


        // Password label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        lblPassword = new JLabel("Password: ");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        // Password field
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        txtPassword = new JPasswordField(20);
        txtPassword.setPreferredSize(new Dimension(200, 30));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPassword, gbc);

        // Confirm Password label
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        lblConfirmPassword = new JLabel("Confirm Password: ");
        lblConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblConfirmPassword, gbc);

        // Confirm Password field
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        txtConfirmPassword = new JPasswordField(20);
        txtConfirmPassword.setPreferredSize(new Dimension(200, 30));
        txtConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtConfirmPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Signup button
        btnSignup = new JButton("SIGNUP");
        btnSignup.setPreferredSize(new Dimension(100,35));
        btnSignup.setBackground(new Color(70, 130, 180));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFocusable(false);
        btnSignup.addActionListener(this);
        btnPanel.add(btnSignup);

        // Login button
        btnLogin = new JButton("Have an account?");
        btnLogin.setPreferredSize(new Dimension(150,35));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusable(false);
        btnLogin.addActionListener(this);
        btnPanel.add(btnLogin);

        formPanel.add(btnPanel, gbc);

        this.add(formPanel, BorderLayout.CENTER);

        footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(240, 240, 250));

        copyrightMsg = new JLabel("Copyright, 2024 Wendy Ochieng. All rights reserved.");
        copyrightMsg.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightMsg.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(copyrightMsg);

        this.add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Create student

        if (e.getSource() == btnSignup) {

            // Get user Input
            String username = txtUsername.getText();
            String fNname = txtFName.getText();
            String lName = txtLName.getText();

            char[] passwordChars = txtPassword.getPassword();
            String password = new String(passwordChars);

            char[] confirmPasswordChars = txtConfirmPassword.getPassword();
            String confirmPassword = new String(confirmPasswordChars);


            // Check if password and confirm password match

            if ((!username.isEmpty()) && (!fNname.isEmpty()) && (!lName.isEmpty()) && (!password.isEmpty())) {

                if (password.equals(confirmPassword)) {

                    String res = dbConnection.createStudent(username, fNname, lName, password);

                    JOptionPane.showMessageDialog(SignupFrame.this, res);

                    new LoginFrame();

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(this, "Passwords do not match!");

                }

            } else {

                JOptionPane.showMessageDialog(this, "Fields must not be empty!");

            }

        }


        // Go to Login Page

        if (e.getSource() == btnLogin) {

            new LoginFrame();

            dispose();

        }

    }
}
