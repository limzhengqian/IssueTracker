package BugsLife;

import java.util.ArrayList;

public class CommentWithoutImage {

    private String comment_text, user;
    private long comment_id;
    private long comment_timestamp;
    private ArrayList<React> react;

    /**
     * Constructor of commentWithoutImage Only used to export JSON file
     *
     * @param id unique id of comment
     * @param text comment text
     * @param timestamp time in which comment was posted
     * @param user the user who posted the comment
     */
    public CommentWithoutImage(long id, String text, long timestamp, String user) {
        this.comment_id = id;
        this.comment_text = text;
        this.comment_timestamp = timestamp;
        this.user = user;
    }

    public String getText() {
        return comment_text;
    }

    public void setText(String text) {
        this.comment_text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public long getTimestamp() {
        return comment_timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.comment_timestamp = timestamp;
    }

    public String getDate() {
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(this.comment_timestamp * 1000L));
    }

    public ArrayList<React> getReact() {
        return react;
    }

    public void setReact(ArrayList<React> react) {
        this.react = react;
    }

    @Override
    public String toString() {
        String str = "Comment ID: " + this.comment_id + "\t\tDate: " + getDate();
        str += "\nUsername: " + this.user;
        str += "\nText: " + this.comment_text;
        return str;
    }
}
