package BugsLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersDAO {

    private String url, username, password;
    private Connection con;

    /**
     * Constructor of userDAO
     * object Connects to database
     *
     * @throws SQLException
     */
    public UsersDAO() throws SQLException {
//        url = "jdbc:mysql://usjhvwoirne9qesm:mX5sqtRsCidWZSWgmWv4@boekrueabx7qaseoc2ov-mysql.services.clever-cloud.com:3306/boekrueabx7qaseoc2ov";
//        username = "usjhvwoirne9qesm";
//        password = "mX5sqtRsCidWZSWgmWv4";
        url = "jdbc:mysql://localhost:3306/tester";
        username = "root";
        password = "password";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Gets all users from the database
     * 
     * @return array list of users
     * @throws SQLException 
     */
    public ArrayList<User> getAllUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Statement myStmt = con.createStatement();
        ResultSet rs = myStmt.executeQuery("select * from users");

        while (rs.next()) {
            User tempUser = convertRowToUser(rs);
            users.add(tempUser);
        }

        return users;
    }

    public void addUser(User newUser) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into users"
                + " values (?, ?, ?, ?)");

        myStmt.setInt(1, newUser.getUserid());
        myStmt.setString(2, newUser.getUsername());
        myStmt.setString(3, newUser.getPassword());
        myStmt.setInt(4, newUser.getPoints());

        myStmt.executeUpdate();
    }

    /**
     * Updates points of the user
     * 
     * @param pointsAdded number of points to add
     * @param userId unique id of the user
     * @throws SQLException 
     */
    public void updatePoints(int pointsAdded, int userId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("update users set `points` = ? where `user id` = ? ");

        User user = getUser(userId);
        int points = user.getPoints() + pointsAdded;

        myStmt.setInt(1, points);
        myStmt.setInt(2, userId);

        myStmt.executeUpdate();
    }

    /**
     * clears the database
     */
    public void clear() {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private User getUser(int userId) throws SQLException {
        Statement myStmt = con.createStatement();
        ResultSet rs = myStmt.executeQuery("select * from users where `user id` = " + userId);

        while (rs.next()) {
            return convertRowToUser(rs);
        }

        return null;
    }

    private User convertRowToUser(ResultSet myRs) throws SQLException {
        int id = myRs.getInt("user id");
        String username = myRs.getString("username");
        String password = myRs.getString("password");
        int points = myRs.getInt("points");

        User tempUser = new User(id, username, password, points);

        return tempUser;
    }
    
    public void closeConnection() throws SQLException {
        con.close();
    }
}
