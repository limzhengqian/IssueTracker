package BugsLife;

import java.util.ArrayList;

public class Project {
    private ArrayList<Issue> issues;
    private long id;
    private String name;
    private int noOfIssue;

    public int getNoOfIssue() {
        return noOfIssue;
    }
    
    public Project(long id, String name, int noOfIssue) {
        this.name = name;
        this.id = id;
        this.noOfIssue = noOfIssue;
    }

    public boolean add(Issue issues) {
        noOfIssue++;
        return this.issues.add(issues);
    } // end add

    public int getCurrentSize() {
        return noOfIssue;
    }

    public boolean isEmpty() {
        return issues.isEmpty();
    } // end isEmpty    

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
