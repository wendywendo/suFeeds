package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    private JLabel lblUsername, lblPassword, lblTitle, copyrightMsg;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel formPanel, btnPanel, footerPanel;
    private JButton btnLogin, btnSignup;

    DBConnection dbConnection = new DBConnection();

    public LoginFrame() {

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
        lblTitle = new JLabel("WELCOME BACK", SwingConstants.CENTER);
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

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        lblPassword = new JLabel("Password: ");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        // Password field
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        txtPassword = new JPasswordField(20);
        txtPassword.setPreferredSize(new Dimension(200, 30));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        // Login button
        btnLogin = new JButton("LOGIN");
        btnLogin.setPreferredSize(new Dimension(100,35));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusable(false);
        btnLogin.addActionListener(this);
        btnPanel.add(btnLogin);

        // Signup button
        btnSignup = new JButton("Go to SIGNUP");
        btnSignup.setPreferredSize(new Dimension(150,35));
        btnSignup.setBackground(new Color(70, 130, 180));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setFocusable(false);
        btnSignup.addActionListener(this);
        btnPanel.add(btnSignup);

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

        if (e.getSource() == btnLogin) {

            String username = txtUsername.getText();

            char[] passwordChars = txtPassword.getPassword();
            String password = new String(passwordChars);

            if ((!username.isEmpty()) && (!password.isEmpty())) {

                String res = dbConnection.loginStudent(username, password);
                JOptionPane.showMessageDialog(LoginFrame.this, res);

                if (res.equals("Login Successful")) {

                    Main.usernameLogged = username;

                    new FeedsFrame();

                    dispose();

                }

            } else {

                JOptionPane.showMessageDialog(this, "Fields must not be empty!");

            }

        }

        if (e.getSource() == btnSignup) {

            new SignupFrame();

            dispose();

        }

    }
}
