package BugsLife;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Comment {

    private String text, user;
    private long comment_id;
    private long timestamp;
    private ArrayList<React> react;
    private BufferedImage img;
    private ArrayList<CommentLog> commentLog;

    /**
     * Empty constructor of comment class
     */
    public Comment() {
    }

    /**
     * Constructor of comment class with image
     *
     * @param id unique id of comment
     * @param text comment text
     * @param timestamp time in which comment was posted
     * @param user the user who posted the comment
     * @param img image attached by user
     */
    public Comment(long id, String text, long timestamp, String user, BufferedImage img) {
        this.comment_id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.user = user;
        this.img = img;
    }

    /**
     * Constructor of comment class with image
     *
     * @param id unique id of comment
     * @param text comment text
     * @param timestamp time in which comment was posted
     * @param user the user who posted the comment
     */
    public Comment(long id, String text, long timestamp, String user) {
        this.comment_id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.user = user;
    }

    public BufferedImage getImg() {
        return img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        //times 1000L to format in milliseconds
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(this.timestamp * 1000L));
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
        str += "\nText: " + this.text;
        return str;
    }
}
