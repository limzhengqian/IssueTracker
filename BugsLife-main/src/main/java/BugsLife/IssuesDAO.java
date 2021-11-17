package BugsLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class IssuesDAO {

    private String url, username, password;
    private Connection con;

    /**
     * Constructor of issueDAO object 
     * Connects to database
     *
     * @throws SQLException
     */
    public IssuesDAO() throws SQLException {
//        url = "jdbc:mysql://usjhvwoirne9qesm:mX5sqtRsCidWZSWgmWv4@boekrueabx7qaseoc2ov-mysql.services.clever-cloud.com:3306/boekrueabx7qaseoc2ov";
//        username = "usjhvwoirne9qesm";
//        password = "mX5sqtRsCidWZSWgmWv4";
        url = "jdbc:mysql://localhost:3306/tester";
        username = "root";
        password = "password";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Get all issue of the project
     * 
     * @param projectId the unique id of the project
     * @return all the issue of the project
     * @throws SQLException 
     */
    public ArrayList<Issue> getAllIssue(int projectId) throws SQLException {
        ArrayList<Issue> issues = new ArrayList<>();

        PreparedStatement myStmt = con.prepareStatement("select * from issues where `project id` = ?");
        myStmt.setInt(1, projectId);
        
        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            Issue tempIssue = convertRowToIssue(rs);
            issues.add(tempIssue);
        }

        return issues;
    }

    /**
     * Get specific issue from unique issue id
     * 
     * @param issueId unique issue id the issue
     * @return issue specified by the id
     * @throws SQLException 
     */
    public Issue getIssue(int issueId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("select * from issues where `issue id` = ?");
        myStmt.setInt(1, issueId);
        
        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            return convertRowToIssue(rs);
        }
        return null;
    }

    /**
     * Adds any changes of the issues to the issue log
     *
     * @param projectId project id of the issue in which changes were made
     * @param issueId issue id of the issue in which changes were made
     * @param changes specify the changes that have been made
     * @param timestamp the time in which the changes were made
     * @throws SQLException
     */
    public void addIssueLog(int projectId, int issueId, String changes, int timestamp) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into issuelog"
                + " (`project id` ,`issue id`, changes, timestamp)"
                + " values (?, ?, ?, ?)");
        myStmt.setInt(1, projectId);
        myStmt.setInt(2, issueId);
        myStmt.setString(3, changes);
        myStmt.setInt(4, timestamp);
        myStmt.executeUpdate();
    }

    /**
     * Get all issue log of a project
     *
     * @param projectId the project id to get the issue log
     * @return history of all changes made to issues of the project
     * @throws SQLException
     */
    public ArrayList<History> getIssueLog(int projectId) throws SQLException {
        ArrayList<History> history = new ArrayList<>();

        PreparedStatement myStmt = con.prepareStatement("select * from issuelog where `project id` = ?");
        myStmt.setInt(1, projectId);
        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            History tempHistory = convertRowToIssueLog(rs);
            history.add(tempHistory);
        }

        return history;
    }

    /**
     * converts row to issue log
     *
     * @param myRs object which is a table of data representing a database result set
     * @return History (issue log)
     * @throws SQLException
     */
    private History convertRowToIssueLog(ResultSet myRs) throws SQLException {
        int id = myRs.getInt("issue id");
        String changes = myRs.getString("changes");
        int timestamp = myRs.getInt("timestamp");
        History history = new History(id, changes, timestamp);
        return history;
    }

    /**
     * Used to import issue from JSON file to database
     * 
     * @param newIssue issue to be imported
     * @param projectId project id of the issue
     * @throws SQLException 
     */
    public void importIssue(Issue newIssue, int projectId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into issues"
                + " (`issue id`, `project id`, text, createdby, `assigned to`, tags, priority, title, status, timestamp)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        myStmt.setInt(1, newIssue.getId());
        myStmt.setInt(2, projectId);
        myStmt.setString(3, newIssue.getDescriptionText());
        myStmt.setString(4, newIssue.getCreatedBy());
        myStmt.setString(5, newIssue.getAssignee());

        String tag = Arrays.toString(newIssue.getTag().toArray());
        tag = tag.substring(1, tag.length() - 1);

        myStmt.setString(6, tag);
        myStmt.setInt(7, newIssue.getPriority());
        myStmt.setString(8, newIssue.getTitle());
        myStmt.setString(9, newIssue.getStatus());
        myStmt.setInt(10, (int) newIssue.getTimestamp());

        myStmt.executeUpdate();
    }

    /**
     * Used to add issue to the database 
     * 
     * @param newIssue issue to be imported
     * @param projectId project id of the issue
     * @throws SQLException 
     */
    public void addIssue(Issue newIssue, int projectId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into issues"
                + " (`project id`, text, createdby, `assigned to`, tags, priority, title, status, timestamp)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        myStmt.setInt(1, projectId);
        myStmt.setString(2, newIssue.getDescriptionText());
        myStmt.setString(3, newIssue.getCreatedBy());
        myStmt.setString(4, newIssue.getAssignee());

        String tag = Arrays.toString(newIssue.getTag().toArray());
        tag = tag.substring(1, tag.length() - 1);

        myStmt.setString(5, tag);
        myStmt.setInt(6, newIssue.getPriority());
        myStmt.setString(7, newIssue.getTitle());
        myStmt.setString(8, newIssue.getStatus());
        myStmt.setInt(9, (int) newIssue.getTimestamp());

        myStmt.executeUpdate();
    }

    /**
     * edit issue from edit issue page without updating status because edit
     * issue page does not allow editing status
     *
     * @param editIssue the newly edited issue
     * @param oldIssue the old issue
     * @throws SQLException
     */
    public void editIssue(Issue editIssue, Issue oldIssue) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("update issues set"
                + " text = ?, `assigned to` = ?, tags = ?, priority = ?, title = ?, timestamp = ?"
                + " where `issue id` = ?");

        String tag = Arrays.toString(editIssue.getTag().toArray());
        tag = tag.substring(1, tag.length() - 1);

        myStmt.setString(1, editIssue.getDescriptionText());
        myStmt.setString(2, editIssue.getAssignee());
        myStmt.setString(3, tag);
        myStmt.setInt(4, editIssue.getPriority());
        myStmt.setString(5, editIssue.getTitle());
        myStmt.setInt(6, (int) editIssue.getTimestamp());
        myStmt.setInt(7, oldIssue.getId());

        myStmt.executeUpdate();
    }

    /**
     * Edit the status of an issue only
     *
     * @param issueId the unique id of the issue to update the status
     * @param status the status of the edited issue
     */
    public void editIssueStatus(int issueId, String status) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("update issues set"
                + " status = ?"
                + " where `issue id` = ?");

        myStmt.setString(1, status);
        myStmt.setInt(2, issueId);

        myStmt.executeUpdate();
    }

    /**
     * Edits the assignee of the issue only
     * 
     * @param issueId the unique id of the issue to update the status
     * @param assignee the assignee of the issue
     * @throws SQLException 
     */
    public void editIssueAssignee(int issueId, String assignee) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("update issues set"
                + " `assigned to` = ?"
                + " where `issue id` = ?");

        myStmt.setString(1, assignee);
        myStmt.setInt(2, issueId);

        myStmt.executeUpdate();
    }

    /**
     * clears the database
     */
    public void clear() {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM issuelog");
            stmt.executeUpdate("DELETE FROM issues");
            stmt.executeUpdate("ALTER TABLE issues AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Converts the resultSet to issue
     *
     * @param myRs object which is a table of data representing a database result set
     * @return issue retrieved from database
     * @throws SQLException
     * @throws IOException
     */
    private Issue convertRowToIssue(ResultSet myRs) throws SQLException {
        int id = myRs.getInt("issue id");
        String descriptionText = myRs.getString("text");
        String createdBy = myRs.getString("createdby");
        String assignedTo = myRs.getString("assigned to");

        String tempTag = myRs.getString("tags");
        String[] tempTagArr = tempTag.split(",");
        ArrayList<String> tagArrList = new ArrayList<>();

        for (int i = 0; i < tempTagArr.length; i++) {
            tagArrList.add(tempTagArr[i].replaceAll("\\s", ""));
        }

        int priority = myRs.getInt("priority");
        String title = myRs.getString("title");
        String status = myRs.getString("status");
        long timestamp = myRs.getInt("timestamp");
        Issue tempIssue = new Issue(id, priority, title, descriptionText, createdBy, assignedTo, status, timestamp, tagArrList);

        return tempIssue;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }
}
