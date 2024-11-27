package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class Feed extends JPanel implements ActionListener {

    private JLabel lblTopic, lblAuthor, lblUnitName;
    private JTextArea txtComment;
    private JPanel headerPanel, bottomPanel, updatePanel;
    private JButton btnDelete, btnEdit;

    JFrame parent;
    int feedId;

    // Dialog components
    JDialog dialog;
    JTextArea txtEditComment;
    JTextField txtEditTopic;
    JButton btnEditFeed;

    DBConnection dbConnection = new DBConnection();

    public Feed(JFrame parent, String fId, String topic, String comment, String author, int unitCode) {

        this.parent = parent;

        feedId = Integer.parseInt(fId);

        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(230, 230, 230), 5),
                BorderFactory.createEmptyBorder(10, 15, 15, 15)
        ));

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        lblTopic = new JLabel(topic);
        lblTopic.setFont(new Font("Arial", Font.BOLD, 13));
        lblTopic.setForeground(new Color(70, 130, 180));
        headerPanel.add(lblTopic, BorderLayout.WEST);

        lblUnitName = new JLabel(dbConnection.getUnitName(unitCode));
        lblUnitName.setFont(new Font("Arial", Font.BOLD, 13));
        lblUnitName.setForeground(new Color(70, 130, 180));
        headerPanel.add(lblUnitName, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);

        txtComment = new JTextArea(comment);
        txtComment.setLineWrap(true);
        txtComment.setWrapStyleWord(true);
        txtComment.setEditable(false);
        txtComment.setBackground(Color.WHITE);
        txtComment.setFont(new Font("Arial", Font.PLAIN, 13));
        this.add(txtComment, BorderLayout.CENTER);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        Map<String, String> userDetails = dbConnection.getUserDetails(author);

        lblAuthor = new JLabel("Posted by " + userDetails.get("FName") + " " + userDetails.get("LName"));
        bottomPanel.add(lblAuthor, BorderLayout.WEST);

        if (author.equals(Main.usernameLogged)) {
            updatePanel = new JPanel(new FlowLayout());
            updatePanel.setBackground(Color.WHITE);

            ImageIcon iconEdit = new ImageIcon("pencil.png");

            btnEdit = new JButton(iconEdit);
            btnEdit.setPreferredSize(new Dimension(50, 50));
            btnEdit.addActionListener(this);
            btnEdit.setContentAreaFilled(false);
            btnEdit.setBorderPainted(false);
            btnEdit.setFocusable(false);
            updatePanel.add(btnEdit);

            ImageIcon icon = new ImageIcon("bin.png");

            btnDelete = new JButton(icon);
            btnDelete.setPreferredSize(new Dimension(50, 50));
            btnDelete.addActionListener(this);
            btnDelete.setContentAreaFilled(false);
            btnDelete.setBorderPainted(false);
            btnDelete.setFocusable(false);
            updatePanel.add(btnDelete);

            bottomPanel.add(updatePanel, BorderLayout.EAST);
        }

        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnDelete) {

            boolean res = dbConnection.deleteFeed(feedId);

            if (res) {

                JOptionPane.showMessageDialog(this,"Successfully deleted feed");

                new FeedsFrame();

                parent.dispose();


            } else {

                JOptionPane.showMessageDialog(this, "Error deleting feed!");

            }

        }

        if (e.getSource() == btnEdit) {

            Map<String, String> feedDetails = dbConnection.getFeed(feedId);

            String topic = feedDetails.get("Topic");
            String comment = feedDetails.get("Comment");

            dialog = new JDialog();
            dialog.setLocationRelativeTo(this);
            dialog.setSize(300, 300);
            dialog.setTitle("Edit Feed");
            dialog.setLayout(null);

            txtEditTopic = new JTextField();
            txtEditTopic.setText(topic);
            txtEditTopic.setBorder(new TitledBorder("Topic"));
            txtEditTopic.setBounds(20, 20, 250, 40);
            dialog.add(txtEditTopic);

            txtEditComment = new JTextArea();
            txtEditComment.setText(comment);
            txtEditComment.setBorder(new TitledBorder("Comment"));
            txtEditComment.setBounds(20, 70, 250, 100);
            txtEditComment.setWrapStyleWord(true);
            txtEditComment.setLineWrap(true);
            dialog.add(txtEditComment);

            btnEditFeed = new JButton("Save");
            btnEditFeed.setBackground(Color.BLUE);
            btnEditFeed.setForeground(Color.WHITE);
            btnEditFeed.setBounds(100, 210, 100, 35);
            btnEditFeed.addActionListener(this);
            dialog.add(btnEditFeed);

            dialog.setVisible(true);

        }

        if (e.getSource() == btnEditFeed) {

            boolean res = dbConnection.editFeed(
                    feedId,
                    txtEditTopic.getText(),
                    txtEditComment.getText()
            );

            if (res) {

                JOptionPane.showMessageDialog(this, "Successfully edited feed!");

                new FeedsFrame();

                parent.dispose();

            } else {

                JOptionPane.showMessageDialog(this, "Error editing feed");

            }

            dialog.setVisible(false);

        }

    }
}
