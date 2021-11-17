package BugsLife;

import java.util.ArrayList;

public class Issue {
    private int id,priority;
    private String title,descriptionText,createdBy,assignee,status;
    private long timestamp;
    private ArrayList<Comment>comments;
    private ArrayList<String>tag;
    private ArrayList<CommentWithoutImage>cnoimg;

    //used for exporting to JSON file only
    public ArrayList<CommentWithoutImage> getCnoimg() {
        return cnoimg;
    }

    //used for exporting to JSON file only
    public void setCnoimg(ArrayList<CommentWithoutImage> cnoimg) {
        this.cnoimg = cnoimg;
    }
    
    public Issue() {
    }

    public Issue(int id, int priority, String title, String descriptionText, String createdBy, 
            String assignee, String status, long timestamp,ArrayList<Comment> comments, ArrayList<String> tag) {
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.descriptionText = descriptionText;
        this.createdBy = createdBy;
        this.assignee = assignee;
        this.status = status;
        this.timestamp = timestamp;
        this.comments = comments;
        this.tag = tag;
    }

    public Issue(int id, int priority, String title, String descriptionText, String createdBy, 
            String assignee, String status, long timestamp, ArrayList<String> tag) {
        this.id = id;
        this.priority = priority;
        this.title = title;
        this.descriptionText = descriptionText;
        this.createdBy = createdBy;
        this.assignee = assignee;
        this.status = status;
        this.timestamp = timestamp;
        this.tag = tag;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignee() {
        return assignee;
    }
    
    public void setAssignee(String assignee){
        this.assignee = assignee;
    }

    @Override
    public String toString() {
            return "\n-------------------------------------------------------------------------------------------------------------" +
                    "\nIssue ID=" + getId() +
                    "\nIssue Title: " + getTitle() +
                    "\nPriority: " + getPriority() +
                    "\nStatus: " + getStatus() +
                    "\nTag: " + getTag() +
                    "\nDescriptionText: " + getDescriptionText() +
                    "\nCreated By: " + getCreatedBy() +
                    "\nassignee: " + getAssignee() +
                    "\ntimestamp: " + getTimestamp()+
                    "\nComments: " + comments.toString()
                    ;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }
    
    public String getDate() {
        return new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(this.timestamp * 1000L));
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}

