package BugsLife;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectsDAO {

    private String url, username, password;
    private Connection con;

    /**
     * Constructor of projectDAO object 
     * Connects to database
     *
     * @throws SQLException
     */
    public ProjectsDAO() throws SQLException {
//        url = "jdbc:mysql://usjhvwoirne9qesm:mX5sqtRsCidWZSWgmWv4@boekrueabx7qaseoc2ov-mysql.services.clever-cloud.com:3306/boekrueabx7qaseoc2ov";
//        username = "usjhvwoirne9qesm";
//        password = "mX5sqtRsCidWZSWgmWv4";
        url = "jdbc:mysql://localhost:3306/tester";
        username = "root";
        password = "password";
        con = DriverManager.getConnection(url, username, password);
    }

    /**
     * Gets all project from the database
     *
     * @return array list of projects
     * @throws SQLException
     */
    public ArrayList<Project> getAllProject() throws SQLException {
        ArrayList<Project> projects = new ArrayList<>();
        Statement myStmt = con.createStatement();
        ResultSet rs = myStmt.executeQuery("select * from projects");

        while (rs.next()) {
            Project tempProject = convertRowToProjects(rs);
            projects.add(tempProject);
        }

        return projects;
    }

    /**
     * Used to import projects from the JSON file
     *
     * @param project the project to be added into the database
     * @throws SQLException
     */
    public void addProject(Project project) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("insert into projects"
                + " (`project id`, name, `no of issue`)"
                + " values (?, ?, ?)");
        myStmt.setInt(1, (int) project.getId());
        myStmt.setString(2, project.getName());
        myStmt.setInt(3, project.getNoOfIssue());
        myStmt.execute();
    }

    /**
     * Updates number of issue of the project
     *
     * @param projectId unique id of project to update the number of issue
     * @throws SQLException
     */
    public void updateNoOfIssue(int projectId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("update projects set `no of issue` = ? where `project id` = ? ");
        Project currentProject = getProject(projectId);
        int noOfIssue = currentProject.getNoOfIssue() + 1;

        myStmt.setInt(1, noOfIssue);
        myStmt.setInt(2, projectId);

        myStmt.executeUpdate();
    }

    /**
     * Clears the database of projects
     */
    public void clear() {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM projects");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Get project based on unique id of project passed into the constructor
     *
     * @param projectId unique id of project to find from database
     * @return project with specified unique id
     * @throws SQLException
     */
    private Project getProject(int projectId) throws SQLException {
        PreparedStatement myStmt = con.prepareStatement("select * from projects where `project id` = ?");
        myStmt.setInt(1, projectId);

        ResultSet rs = myStmt.executeQuery();

        while (rs.next()) {
            return convertRowToProjects(rs);
        }

        return null;
    }

    /**
     * Converts the resultSet to project
     *
     * @param myRs object which is a table of data representing a database
     * result set
     * @return project retrieved from database
     * @throws SQLException
     * @throws IOException
     */
    private Project convertRowToProjects(ResultSet myRs) throws SQLException {
        int id = myRs.getInt("project id");
        String name = myRs.getString("name");
        int noOfIssue = myRs.getInt("no of issue");

        Project tempProject = new Project(id, name, noOfIssue);

        return tempProject;
    }
    
    public void closeConnection() throws SQLException {
        con.close();
    }
}
