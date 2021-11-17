package BugsLife;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class CommentsDAO {

    private String url, username, password;
    private Connection con;

    /**
     * Constructor of issueDAO object
     * Connects to database
     * 
     * @throws SQLException
     */
    public CommentsDAO() throws SQLException {
//        url = "jdbc:mysql://usjhvwoirne9qesm:mX5sqtRsCidWZSWgmWv4@boekrueabx7qaseoc2ov-mysql.services.clever-cloud.com:3306/boekrueabx7qaseoc2ov";;
//        username = "usjhvwoirne9qesm";
//        password = "mX5sqtRsCidWZSWgmWv4";
        url = "jdbc:mysql://localhost:3306/tester";
        username = "root";
        password = "password";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Get all comments of an issue without the image
     * Only used to export JSON file
     *
     * @param issueId the unique id of the issue to get comments from
     * @return all comments of the issue without images
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<CommentWithoutImage> getAllCommentsNoIMG(int issueId) throws SQLException, IOException {
        ArrayList<CommentWithoutImage> comments = new ArrayList<>();

        PreparedStatement myStmt = con.prepareStatement("select * from comments where `issue id` = ?");
        myStmt.setInt(1, issueId);

        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            CommentWithoutImage tempComment = convertRowToCommentNoIMG(rs);
            comments.add(tempComment);
        }

        return comments;
    }

    /**
     * Converts the resultSet to commentWithoutImage
     *
     * @param myRs object which is a table of data representing a database
     * result set
     * @return commentWithoutImage retrieved from database
     * @throws SQLException
     * @throws IOException
     */
    private CommentWithoutImage convertRowToCommentNoIMG(ResultSet myRs) throws SQLException, IOException {
        int id = myRs.getInt("comment id");
        String text = myRs.getString("text");
        String username = myRs.getString("user");
        int timestamp = myRs.getInt("timestamp");
        byte[] byteArray = myRs.getBytes("image");
        CommentWithoutImage tempComment;

        tempComment = new CommentWithoutImage(id, text, timestamp, username);

        return tempComment;
    }

    /**
     * Get all comments of an issue
     *
     * @param issueId the unique id of the issue to get comments from
     * @return all comments of the issue without images
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList<Comment> getAllComments(int issueId) throws SQLException, IOException {
        ArrayList<Comment> comments = new ArrayList<>();

        PreparedStatement myStmt = con.prepareStatement("select * from comments where `issue id` = ?");
        myStmt.setInt(1, issueId);

        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            Comment tempComment = convertRowToComment(rs);
            comments.add(tempComment);
        }

        return comments;
    }

    /**
     * Get specific comment based on comment id
     *
     * @param commentId the unique id of the comment
     * @return a comment based on unique id
     * @throws SQLException
     * @throws IOException 
     */
    public Comment getComment(int commentId) throws SQLException, IOException {
        
        PreparedStatement myStmt = con.prepareStatement("select * from comments where `comment id` = ?");
        myStmt.setInt(1, commentId);

        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            return convertRowToComment(rs);
        }
        return null;
    }

    /**
     * Edit the comment in the database and enter change log into database
     * 
     * @param newComment the new comment edited by user
     * @param oldComment the old comment which was updated
     * @param updatePicture true if no picture is updated to null, false if not
     * @throws SQLException 
     */
    public void editComment(Comment newComment, Comment oldComment, boolean updatePicture) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("insert into commentlog (`comment id`, text, user, timestamp, image)"
                + " select `comment id`, text, user, timestamp, image from comments "
                + " where `comment id` = ?");

        stmt.setInt(1, (int) oldComment.getComment_id());
        stmt.executeUpdate();
        
        PreparedStatement myStmt = con.prepareStatement("update comments set"
                + " `comment id` = ?, text = ?, user = ?, timestamp = ?"
                + " where `comment id` = ?");

        myStmt.setInt(1, (int) oldComment.getComment_id());
        myStmt.setString(2, newComment.getText());
        myStmt.setString(3, newComment.getUser());
        myStmt.setInt(4, (int) newComment.getTimestamp());
        myStmt.setInt(5, (int) oldComment.getComment_id());

        myStmt.executeUpdate();

        if (updatePicture) {
            myStmt = con.prepareStatement("update comments set image = NULL where `comment id` = ?");
            myStmt.setInt(1, (int) oldComment.getComment_id());
            myStmt.executeUpdate();
        }

    }

    /**
     * Edit the comment in the database and enter change log into database
     * 
     * @param newComment the new comment edited by user
     * @param oldComment the old comment which was updated
     * @param in FileInputStream of image uploaded
     * @throws SQLException 
     */
    public void editComment(Comment newComment, Comment oldComment, FileInputStream in) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("insert into commentlog (`comment id`, text, user, timestamp, image)"
                + " select `comment id`, text, user, timestamp, image from comments "
                + " where `comment id` = ?");

        stmt.setInt(1, (int) oldComment.getComment_id());
        stmt.executeUpdate();

        PreparedStatement myStmt = con.prepareStatement("update comments set"
                + " `comment id` = ?, text = ?, user = ?, timestamp = ?, image = ?"
                + " where `comment id` = ?");

        myStmt.setInt(1, (int) oldComment.getComment_id());
        myStmt.setString(2, newComment.getText());
        myStmt.setString(3, newComment.getUser());
        myStmt.setInt(4, (int) newComment.getTimestamp());
        myStmt.setBlob(5, in);
        myStmt.setInt(6, (int) oldComment.getComment_id());

        myStmt.executeUpdate();
    }

    /**
     * Get comment log from database
     * 
     * @param commentId the unique id of the comment
     * @return comment log of the comment
     * @throws SQLException
     * @throws IOException 
     */
    public ArrayList<CommentLog> getCommentLog(int commentId) throws SQLException, IOException {
        ArrayList<CommentLog> commentLog = new ArrayList<>();

        PreparedStatement myStmt = con.prepareStatement("select * from commentlog"
                + " where `comment id` = ?");
        myStmt.setInt(1, commentId);
        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            CommentLog tempLog = convertRowToCommentLog(rs);
            commentLog.add(tempLog);
        }
        return commentLog;
    }

    /**
     * Used to import comment from JSON file to database
     * Difference is importComment has comment id while addComment method does not have
     * 
     * @param newComment the comment to be imported into the database
     * @param issueId the issueId of the comment
     * @throws SQLException 
     */
    public void importComment(Comment newComment, int issueId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into comments"
                + " (`comment id`, `issue id`, text, user, timestamp)"
                + " values (?, ?, ?, ?, ?)");

        myStmt.setInt(1, (int) newComment.getComment_id());
        myStmt.setInt(2, issueId);
        myStmt.setString(3, newComment.getText());
        myStmt.setString(4, newComment.getUser());
        myStmt.setInt(5, (int) newComment.getTimestamp());

        myStmt.executeUpdate();
    }

    /**
     * Adds new comment in the database which does not have image
     * 
     * @param newComment the new comment to be added to the database
     * @param issueId the issueId of the comment
     * @throws SQLException 
     */
    public void addComment(Comment newComment, int issueId) throws SQLException {
        boolean added = false;

        PreparedStatement myStmt = con.prepareStatement("insert into comments"
                + " (`issue id`, text, user, timestamp)"
                + " values (?, ?, ?, ?)");

        myStmt.setInt(1, issueId);
        myStmt.setString(2, newComment.getText());
        myStmt.setString(3, newComment.getUser());
        myStmt.setInt(4, (int) newComment.getTimestamp());

        myStmt.executeUpdate();
    }

    /**
     * Add new comment in the database which has image
     * 
     * @param newComment the new comment to be added to the database
     * @param issueId the issueId of the comment
     * @param in the FileInputStream of the image to be added
     * @return
     * @throws SQLException 
     */
    public boolean addComment(Comment newComment, int issueId, FileInputStream in) throws SQLException {
        boolean added = false;

        PreparedStatement myStmt = con.prepareStatement("insert into comments"
                + " (`issue id`, text, user, timestamp, image)"
                + " values (?, ?, ?, ?, ?)");

        myStmt.setInt(1, issueId);
        myStmt.setString(2, newComment.getText());
        myStmt.setString(3, newComment.getUser());
        myStmt.setInt(4, (int) newComment.getTimestamp());
        myStmt.setBlob(5, in);

        if (myStmt.executeUpdate() != 0) {
            added = true;
        }

        return added;
    }

    /**
     * clears the database
     */
    public void clear() {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM commentlog");
            stmt.executeUpdate("DELETE FROM comments");
            stmt.executeUpdate("ALTER TABLE comments AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    
    /**
     * Delete comment of a user
     *
     * @param commentId unique comment id of the user
     * @throws SQLException
     */
    public void deleteComment(int commentId) throws SQLException {
        deleteCommentLog(commentId);
        deleteReact(commentId);

        PreparedStatement myStmt = con.prepareStatement("DELETE FROM comments"
                + " where `comment id` = ?");
        myStmt.setInt(1, commentId);
        myStmt.execute();

        Statement stmt = con.createStatement();
        stmt.executeUpdate("ALTER TABLE comments AUTO_INCREMENT = 1");
    }

    /**
     * Delete comment log of a comment
     *
     * @param commentId unique id of comment
     * @throws SQLException
     */
    private void deleteCommentLog(int commentId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("DELETE FROM commentlog"
                + " where `comment id` = ?");
        myStmt.setInt(1, commentId);
        myStmt.execute();
    }

    /**
     * Delete reaction of a comment
     *
     * @param commentId unique id of comment
     * @throws SQLException
     */
    private void deleteReact(int commentId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("DELETE FROM reacts"
                + " where `comment id` = ?");
        myStmt.setInt(1, commentId);
        myStmt.execute();
    }
    
    /**
     * Converts the resultSet to comment
     *
     * @param myRs object which is a table of data representing a database result set
     * @return comment retrieved from database
     * @throws SQLException
     * @throws IOException
     */
    private Comment convertRowToComment(ResultSet myRs) throws SQLException, IOException {
        int id = myRs.getInt("comment id");
        String text = myRs.getString("text");
        String username = myRs.getString("user");
        int timestamp = myRs.getInt("timestamp");
        byte[] byteArray = myRs.getBytes("image");

        Comment tempComment;

        if (byteArray == null) {
            tempComment = new Comment(id, text, timestamp, username);
        } else {
            InputStream in = new ByteArrayInputStream(byteArray);
            BufferedImage img = ImageIO.read(in);
            tempComment = new Comment(id, text, timestamp, username, img);
        }
        return tempComment;
    }

     /**
     * Converts the resultSet to commentLog
     *
     * @param myRs object which is a table of data representing a database result set
     * @return commentLog retrieved from database
     * @throws SQLException
     * @throws IOException
     */
    private CommentLog convertRowToCommentLog(ResultSet myRs) throws SQLException, IOException {
        String oldText = myRs.getString("text");
        String user = myRs.getString("user");
        int timestamp = myRs.getInt("timestamp");
        byte[] byteArray = myRs.getBytes("image");

        CommentLog tempLog;

        if (byteArray == null) {
            tempLog = new CommentLog(oldText, user, timestamp);
        } else {
            InputStream in = new ByteArrayInputStream(byteArray);
            BufferedImage img = ImageIO.read(in);
            tempLog = new CommentLog(oldText, user, timestamp, img);
        }
        return tempLog;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }
}
