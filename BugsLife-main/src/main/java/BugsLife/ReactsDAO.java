package BugsLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReactsDAO {

    private String url, username, password;
    private Connection con;

    /**
     * Constructor of reactsDAO 
     * object Connects to database
     *
     * @throws SQLException
     */
    public ReactsDAO() throws SQLException {
//        url = "jdbc:mysql://usjhvwoirne9qesm:mX5sqtRsCidWZSWgmWv4@boekrueabx7qaseoc2ov-mysql.services.clever-cloud.com:3306/boekrueabx7qaseoc2ov";
//        username = "usjhvwoirne9qesm";
//        password = "mX5sqtRsCidWZSWgmWv4";
        url = "jdbc:mysql://localhost:3306/tester";
        username = "root";
        password = "password";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Get individual reaction of user to a unique comment
     * 
     * @param commentId unique id of the comment
     * @param username username of current user
     * @return reaction of user
     * @throws SQLException 
     */
    public String getIndividualReact(int commentId, String username) throws SQLException {
        //Statement myStmt = con.createStatement();
        //ResultSet rs = myStmt.executeQuery("select * from reacts where `comment id` = " + commentId + " and `username` = `" + username + "`");
        PreparedStatement myStmt = con.prepareStatement("select * from reacts where `comment id` = ? and username = ?");
        myStmt.setInt(1, commentId);
        myStmt.setString(2, username);
        ResultSet rs = myStmt.executeQuery();

        String reaction = "";

        while (rs.next()) {
            reaction = rs.getString("reaction");
        }

        return reaction;
    }

    /**
     * Get array list of reaction of a comment based on comment id
     * 
     * @param commentId unique id of comment
     * @return array list of reaction
     * @throws SQLException 
     */
    public ArrayList<React> getAllReact(int commentId) throws SQLException {
        ArrayList<React> react = new ArrayList<>();

        int angryCount = 0;
        int happyCount = 0;
        int thumbsUpCount = 0;

        Statement myStmt = con.createStatement();
        ResultSet rs = myStmt.executeQuery("select * from reacts where `comment id` = " + commentId);

        while (rs.next()) {
            String reaction = rs.getString("reaction");
            if (reaction.equals("happy")) {
                happyCount++;
            } else if (reaction.equals("angry")) {
                angryCount++;
            } else if (reaction.equals("thumbs up")) {
                thumbsUpCount++;
            }
        }

        react.add(new React("Angry", angryCount));
        react.add(new React("Happy", happyCount));
        react.add(new React("Thumbs Up", thumbsUpCount));
        return react;
    }

    /**
     * Add a reaction to the database
     * 
     * @param reaction reaction of user
     * @param commentId unique comment id of comment
     * @param username username of current logged in user
     * @throws SQLException 
     */
    public void addReact(String reaction, int commentId, String username) throws SQLException {
        boolean added = false;

        PreparedStatement myStmt = con.prepareStatement("insert into reacts"
                + " (reaction, `comment id`, username)"
                + " values (?, ?, ?)");

        myStmt.setString(1, reaction);
        myStmt.setInt(2, commentId);
        myStmt.setString(3, username);

        myStmt.executeUpdate();
    }

    /**
     * Remove reaction of user from a comment
     * 
     * @param commentId unique comment id of comment
     * @param username username of current logged in user
     * @throws SQLException 
     */
    public void removeReact(int commentId, String username) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("delete from reacts where (`comment id` = ? AND username = ?)");

        myStmt.setInt(1, commentId);
        myStmt.setString(2, username);

        myStmt.executeUpdate();
    }

    /**
     * Clears the database
     */
    public void clear() {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM reacts");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void closeConnection() throws SQLException {
        con.close();
    }
}
