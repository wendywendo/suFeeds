package com.ochieng.sufeeds;

// NAME: Wendy Wendo Ochieng
// Admission number: 190431
// Group: ICS Group C
// Date: 23rd November 2024


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    public static Properties properties = new Properties();


    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load config.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = properties.getProperty("DATABASE_URL");
    static final String USER = properties.getProperty("USER");
    static final String PASSWORD = properties.getProperty("PASSWORD");

    Connection conn = null;
    Statement statement = null;
    ResultSet result = null;

    public DBConnection() {
        try {
            // Load the driver class
            Class.forName(DRIVER);

            // Establish connection
            conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    // Sign Up Student
    public String createStudent(String username, String fName, String lName, String password) {

        try {

            // Check if student already exists
            String sql = "SELECT * FROM tbl_students WHERE username = ?;";
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setString(1, username);
                try (ResultSet result = psmt.executeQuery()) {
                    if (result.isBeforeFirst()) {
                        return "User already exists!";
                    }
                }
            }

            // Create student
            sql = "INSERT INTO tbl_students (username, fName, lName, password) VALUES (?, ?, ?, ?);";
            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setString(1, username);
                psmt.setString(2, fName);
                psmt.setString(3, lName);
                psmt.setString(4, password);

                psmt.executeUpdate();
            }

            return "Successfully created student!";

        }  catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

            return "Error!";

        }

    }


    // Login User
    public String loginStudent(String username, String password) {

        try {

            String sql = "SELECT * FROM tbl_students WHERE username = ?";

            PreparedStatement psmt = conn.prepareStatement(sql);

            psmt.setString(1, username);
            result = psmt.executeQuery();

            if (!result.isBeforeFirst()) {
                return "No user found!";
            } else {

                while (result.next()) {

                    if (username.equals(result.getObject(1))) {
                        if (password.equals(result.getObject(4))) {
                            return "Login Successful";

                        } else {
                            return "Invalid credentials";
                        }
                    }


                }
            }

        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return "Error!";

    }

    // Get userDetails
    public Map<String, String> getUserDetails(String username) {

        try {

            Map<String, String> userDetails = new HashMap<>();

            statement = conn.createStatement();

            String sql = "SELECT * FROM tbl_students WHERE username = '" + username + "';";
            result = statement.executeQuery(sql);

            while (result.next()) {

                userDetails.put("FName", (String) result.getObject(2));
                userDetails.put("LName", (String) result.getObject(3));

                return userDetails;

            }

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return new HashMap<>();

    }

    // Get All Feeds
    public List<Map> getFeeds() {
        try {

            List<Map> feeds = new ArrayList<>();

            String sql = "SELECT feedId, topic, comment, username, unitCode " +
                    "FROM tbl_feeds JOIN tbl_students USING(username) JOIN tbl_units USING(unitCode);";

            statement = conn.createStatement();

            result = statement.executeQuery(sql);

            while (result.next()) {

                Map<Object, Object> feed = new HashMap<>();
                feed.put("FeedId", result.getObject(1));
                feed.put("Topic", result.getObject(2));
                feed.put("Comment", result.getObject(3));
                feed.put("Username", result.getObject(4));
                feed.put("UnitCode", result.getObject(5));

                feeds.add(feed);
            }

            return feeds;

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return new ArrayList<>();
    }

    // Get UnitName
    public String getUnitName(int unitCode) {

        try {

            statement = conn.createStatement();

            String sql = "SELECT * FROM tbl_units WHERE unitCode = " + unitCode;
            result = statement.executeQuery(sql);

            while (result.next()) {

                return (String) result.getObject(2);

            }


        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return null;

    }

    // Add feed
    public void addFeed(String topic, String comment, String username, int unitCode) {

        try {

            statement = conn.createStatement();

            String sql = "INSERT INTO tbl_feeds(topic, comment, username, unitCode) VALUES(?, ?, ?, ?)";

            try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                psmt.setString(1, topic);
                psmt.setString(2, comment);
                psmt.setString(3, username);
                psmt.setInt(4, unitCode);

                psmt.executeUpdate();
            }


        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    // Get all units
    public List<String> getAllUnits() {

        try {

            statement = conn.createStatement();

            List<String> allUnits = new ArrayList<>();

            String sql = "SELECT * FROM tbl_units;";
            result = statement.executeQuery(sql);

            while (result.next()) {

                allUnits.add((String) result.getObject(2));

            }

            return allUnits;


        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

            return null;
        }

    }

    // Get unit code
    public int getUnitCode(String unitName) {

        try {

            String sql = "SELECT * FROM tbl_units where unitName = '" + unitName  + "';";
            result = statement.executeQuery(sql);

            while (result.next()) {

                return (int) result.getObject(1);

            }

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return -1;

    }

    // Get all student units
    public List<Map> getStudentUnits() {

        try {

            statement = conn.createStatement();

            List<Map> allStudentUnits = new ArrayList<>();

            String sql = "SELECT u.unitCode, u.unitName, su.semester FROM tbl_units u " +
                    "JOIN tbl_stdunits su ON u.unitCode = su.unitCode " +
                    "JOIN tbl_students us ON su.username = us.username " +
                    "WHERE us.username = " + Main.usernameLogged;

            result = statement.executeQuery(sql);

            while (result.next()) {

                Map<String, String> studentUnit = new HashMap<>();

                studentUnit.put("UnitCode", result.getObject(1).toString());
                studentUnit.put("UnitName", (String) result.getObject(2));
                studentUnit.put("Semester", result.getObject(3).toString());

                allStudentUnits.add(studentUnit);

            }

            return allStudentUnits;


        } catch (SQLException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return new ArrayList<>();
    }


    // Add a unit
    public boolean addUnit(int unitCode, int semester) {

        try {

            statement = conn.createStatement();

            // Check if unit already exists for student
            String sql = "SELECT * FROM tbl_stdunits WHERE username = '" + Main.usernameLogged + "' && " +
                    "unitCode = " + unitCode;
            result = statement.executeQuery(sql);

            if (!result.isBeforeFirst()) {
                sql = "INSERT INTO tbl_stdunits (unitCode, username, semester) VALUES(?, ?, ?)";

                try (PreparedStatement psmt = conn.prepareStatement(sql)) {
                    psmt.setInt(1, unitCode);
                    psmt.setString(2, Main.usernameLogged);
                    psmt.setInt(3, semester);

                    psmt.executeUpdate();

                    return true;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return false;

    }

    // Delete feed
    public boolean deleteFeed(int feedId) {

        try {

            statement = conn.createStatement();

            String sql = "DELETE FROM tbl_feeds where feedId = " + feedId;
            statement.executeUpdate(sql);

            return true;

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return false;

    }

    // Edit feed
    public boolean editFeed(int feedId, String topic, String comment) {

        try {

            statement = conn.createStatement();

            String sql = "UPDATE tbl_feeds SET comment = '" + comment + "', topic = '" + topic + "' " +
                    "WHERE feedId = " + feedId;

            statement.executeUpdate(sql);

            return true;

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return false;

    }

    // Get feed
    public Map<String, String> getFeed(int feedId) {

        try {

            statement = conn.createStatement();

            Map<String, String> feed = new HashMap<>();

            String sql = "SELECT topic, comment FROM tbl_feeds WHERE feedId = " + feedId;
            result = statement.executeQuery(sql);

            while (result.next()) {

                feed.put("Topic", (String) result.getObject(1));
                feed.put("Comment", (String) result.getObject(2));

                return feed;

            }

        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return new HashMap<>();

    }

    // Delete unit
    public boolean deleteUnit(int unitCode) {

        try {

            statement = conn.createStatement();

            String sql = "DELETE FROM tbl_stdunits WHERE username = '" +
                    Main.usernameLogged + "' AND unitCode = " + unitCode;

            statement.executeUpdate(sql);

            return true;


        } catch (SQLException e) {

            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);

        }

        return false;

    }

}
