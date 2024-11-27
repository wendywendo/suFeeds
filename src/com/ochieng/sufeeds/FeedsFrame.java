package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedsFrame extends JFrame implements ActionListener {

    private JPanel feedsPanel, headerPanel, footerPanel;
    private JScrollPane scrollPane;
    private JButton btnAdd, btnAddFeed, btnGoToUnits, btnLogout;

    // Dialog components
    private JDialog dialog;
    private JLabel copyrightMsg;
    private JComboBox selectUnit;

    private JTextField txtTopic;
    private JTextArea txtComment;
    String[] units;

    DBConnection dbConnection = new DBConnection();

    public FeedsFrame() {

        setTitle("All Feeds");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 25));
        headerPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("ADD FEED");
        btnAdd.setBackground(new Color(70, 130, 180));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusable(false);
        btnAdd.addActionListener(this);
        headerPanel.add(btnAdd);

        btnGoToUnits = new JButton("SET UNITS");
        btnGoToUnits.setBackground(new Color(70, 130, 180));
        btnGoToUnits.setForeground(Color.WHITE);
        btnGoToUnits.setFocusable(false);
        btnGoToUnits.addActionListener(this);
        headerPanel.add(btnGoToUnits);

        btnLogout = new JButton("LOGOUT");
        btnLogout.setBackground(new Color(70, 130, 180));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusable(false);
        btnLogout.addActionListener(this);
        headerPanel.add(btnLogout);

        this.add(headerPanel, BorderLayout.NORTH);

        // Get all feeds and display

        feedsPanel = new JPanel();
        feedsPanel.setLayout(new BoxLayout(feedsPanel, BoxLayout.Y_AXIS));
        feedsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        Map<String, String> userDetails = dbConnection.getUserDetails(Main.usernameLogged);
        String fName = userDetails.get("FName");
        String lName = userDetails.get("LName");
        JLabel welcomeMsg = new JLabel("Welcome " + fName + " " + lName + "!");
        feedsPanel.add(welcomeMsg);

        List<Map> feeds = dbConnection.getFeeds();

        if (feeds.isEmpty()) {

            JLabel lblEmpty = new JLabel("No feeds available");
            feedsPanel.add(lblEmpty);
        }

        for (Map myFeed: feeds) {

            Feed feed = new Feed(
                    this,
                    myFeed.get("FeedId").toString(),
                    (String) myFeed.get("Topic"),
                    (String) myFeed.get("Comment"),
                    (String) myFeed.get("Username"),
                    Integer.parseInt(myFeed.get("UnitCode").toString())
            );
            feedsPanel.add(feed);

        }

        this.add(feedsPanel, BorderLayout.CENTER);

        scrollPane = new JScrollPane(feedsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        this.add(scrollPane, BorderLayout.CENTER);

        footerPanel = new JPanel(new FlowLayout());

        copyrightMsg = new JLabel("Copyright, 2024 Wendy Ochieng. All rights reserved.");
        copyrightMsg.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightMsg.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(copyrightMsg);

        this.add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {

            dialog = new JDialog();
            dialog.setLocationRelativeTo(this);
            dialog.setSize(300, 300);
            dialog.setTitle("Add Feed");
            dialog.setLayout(null);

            txtTopic = new JTextField();
            txtTopic.setBorder(new TitledBorder("Topic"));
            txtTopic.setBounds(20, 20, 250, 40);
            dialog.add(txtTopic);

            txtComment = new JTextArea();
            txtComment.setBorder(new TitledBorder("Comment"));
            txtComment.setBounds(20, 70, 250, 100);
            txtComment.setWrapStyleWord(true);
            txtComment.setLineWrap(true);
            dialog.add(txtComment);

            List<Map> allStudentUnits = dbConnection.getStudentUnits();

            // Convert List<Map> to String[]
            List<String> unitNames = new ArrayList<>();
            allStudentUnits.forEach(unit -> unitNames.add((String) unit.get("UnitName")));

            units = unitNames.toArray(new String[0]);

            selectUnit = new JComboBox(units);
            selectUnit.setBounds(20, 180, 250, 20);
            dialog.add(selectUnit);

            btnAddFeed = new JButton("Post");
            btnAddFeed.setBackground(Color.BLUE);
            btnAddFeed.setForeground(Color.WHITE);
            btnAddFeed.setBounds(100, 210, 100, 35);
            btnAddFeed.addActionListener(this);
            dialog.add(btnAddFeed);

            dialog.setVisible(true);

        }

        if (e.getSource() == btnAddFeed) {

            String topic = txtTopic.getText();
            String comment = txtComment.getText();
            int unitCode = dbConnection.getUnitCode(units[selectUnit.getSelectedIndex()]);

            if ((!topic.isEmpty()) && (!comment.isEmpty())) {

                dbConnection.addFeed(
                        topic,
                        comment,
                        Main.usernameLogged,
                        unitCode
                );

                JOptionPane.showMessageDialog(this, "Successfully created Feed!");

                dialog.setVisible(false);

                // Refresh frame
                dispose();
                new FeedsFrame();


            } else {

                JOptionPane.showMessageDialog(this, "Fields must not be empty!");

            }

        }

        if (e.getSource() == btnGoToUnits) {

            new UnitsFrame();

            dispose();

        }

        if (e.getSource() == btnLogout) {

            new LoginFrame();

            dispose();

            Main.usernameLogged = "";

        }

    }
}
