package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.List;

public class UnitsFrame extends JFrame implements ActionListener {

    private JLabel lblTitle, lblSelectUnit, lblSelectSemester, copyrightMsg;
    private JPanel mainPanel, headerPanel, footerPanel;
    private JButton btnAddUnits, btnAdd, btnBack;
    private JDialog addUnitDialog;
    private JComboBox selectUnit, selectSemester;

    String[] units, semesters;

    DBConnection dbConnection = new DBConnection();

    public UnitsFrame() {

        super("My Units");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        lblTitle = new JLabel("MY UNITS");
        headerPanel.add(lblTitle, BorderLayout.WEST);

        btnAddUnits = new JButton("Add Units");
        btnAddUnits.setFocusable(false);
        btnAddUnits.setBackground(Color.BLUE);
        btnAddUnits.setForeground(Color.WHITE);
        btnAddUnits.addActionListener(this);
        headerPanel.add(btnAddUnits, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);


        // Table
        String[] columnNames = {"Unit Code", "Unit Name", "Semester", "Action"};
        DefaultTableModel topicsModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable topicsTable = new JTable(topicsModel);

        List<Map> allStudentUnits = dbConnection.getStudentUnits();

        for (Map stdUnit: allStudentUnits) {

            topicsModel.addRow(new Object[]{
                    stdUnit.get("UnitCode"),
                    stdUnit.get("UnitName"),
                    stdUnit.get("Semester"),
                    "DELETE"
            });

        }

        topicsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int row = topicsTable.rowAtPoint(e.getPoint());
                int column = topicsTable.columnAtPoint(e.getPoint());

                if (column == 3) {

                    int confirm = JOptionPane.showConfirmDialog(
                            UnitsFrame.this,
                            "Are you sure?",
                            "Confirm delete",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {

                        // Retrieve unit code
                        int unitCode = Integer.parseInt(topicsTable.getValueAt(row, 0).toString());

                        // Delete unit from database
                        boolean res = dbConnection.deleteUnit(unitCode);

                        if (res) {

                            JOptionPane.showMessageDialog(
                                    UnitsFrame.this,
                                    "Successfully deleted!"
                            );

                            topicsModel.removeRow(row);

                        } else {

                            JOptionPane.showMessageDialog(
                                    UnitsFrame.this,
                                    "Error deleting unit!"
                            );

                        }

                    }

                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(topicsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);

        btnBack = new JButton("BACK");
        btnBack.setPreferredSize(new Dimension(50, 50));
        btnBack.setBackground(new Color(70, 130, 180));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusable(false);
        btnBack.addActionListener(this);
        this.add(btnBack, BorderLayout.NORTH);

        footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(Color.WHITE);

        copyrightMsg = new JLabel("Copyright, 2024 Wendy Ochieng. All rights reserved.");
        copyrightMsg.setFont(new Font("Arial", Font.ITALIC, 12));
        copyrightMsg.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(copyrightMsg);

        this.add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAddUnits) {

            addUnitDialog = new JDialog();
            addUnitDialog.setTitle("Add Unit");
            addUnitDialog.setSize(300, 300);
            addUnitDialog.setLocationRelativeTo(this);
            addUnitDialog.setLayout(null);

            lblSelectUnit = new JLabel("Select unit: ");
            lblSelectUnit.setBounds(20, 20, 250, 20);
            addUnitDialog.add(lblSelectUnit);

            units = dbConnection.getAllUnits().toArray(new String[0]);
            selectUnit = new JComboBox(units);
            selectUnit.setBounds(20, 50, 250, 20);
            addUnitDialog.add(selectUnit);

            lblSelectSemester = new JLabel("Select semester: ");
            lblSelectSemester.setBounds(20, 80, 250, 20);
            addUnitDialog.add(lblSelectSemester);

            semesters = new String[]{"1", "2", "3"};
            selectSemester = new JComboBox(semesters);
            selectSemester.setBounds(20, 110, 250, 20);
            addUnitDialog.add(selectSemester);

            btnAdd = new JButton("ADD");
            btnAdd.setBounds(20, 160, 250, 40);
            btnAdd.addActionListener(this);
            addUnitDialog.add(btnAdd);

            addUnitDialog.setVisible(true);

        }

        if (e.getSource() == btnAdd) {

            int unitCode = dbConnection.getUnitCode(units[selectUnit.getSelectedIndex()]);
            int semester = Integer.parseInt(semesters[selectSemester.getSelectedIndex()]);

            boolean res = dbConnection.addUnit(unitCode, semester);

            if (res) {
                JOptionPane.showMessageDialog(this, "Successfully created Unit!");

                addUnitDialog.setVisible(false);

                // Refresh frame
                new UnitsFrame();

                dispose();
            } else {

                JOptionPane.showMessageDialog(this, "Unit may already exist!");

            }

        }


        if (e.getSource() == btnBack) {

            new FeedsFrame();

            dispose();

        }

    }
}
