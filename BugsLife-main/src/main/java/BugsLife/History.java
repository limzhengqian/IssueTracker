/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BugsLife;

public class History {
    
    private String changes;
    private int timestamp;
    private int issueId;

    /**
     * Constructor of history object (change log for issues)
     * 
     * @param issueId unique issue id of the issue
     * @param changes changes that have been made to the issue
     * @param timestamp the time at which the changes have been made
     */
    public History(int issueId, String changes, int timestamp) {
        this.changes = changes;
        this.timestamp = timestamp;
        this.issueId = issueId;
    }

    public String getChanges() {
        return changes;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getIssueId() {
        return issueId;
    }
    
    public String getDate(){
        //times 1000L to format from milliseconds
        return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(this.timestamp * 1000L));
    }
    
    @Override
    public String toString() {
        return "Issue ID: " + issueId + "\t\tDate: " + this.getDate() + "\nChanges" + changes;
    }
    
    
}
