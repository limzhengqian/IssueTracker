package BugsLife;

import java.awt.image.BufferedImage;

public class CommentLog {
    private String oldText, user;
    private long timestamp;
    private BufferedImage img;

    /**
     * Empty constructor of commentLog
     */
    public CommentLog() {
    }
    
    /**
     * Constructor of commentLog without image
     * 
     * @param oldText the old edited text of the comment
     * @param user the user who commented
     * @param timestamp the timestamp at which the comment was edited
     */
    public CommentLog(String oldText, String user, long timestamp){
        this.oldText = oldText;
        this.user = user;
        this.timestamp = timestamp;
    }
    
     /**
     * Constructor of commentLog with image
     * 
     * @param oldText the old edited text of the comment
     * @param user the user who commented
     * @param timestamp the timestamp at which the comment was posted/edited
     * @param img the image of the edited comment
     */
    public CommentLog(String oldText, String user, long timestamp, BufferedImage img) {
        this.oldText = oldText;
        this.user = user;
        this.timestamp = timestamp;
        this.img = img;
    }

    public String getNewText() {
        return oldText;
    }

    public String getUser() {
        return user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BufferedImage getImg() {
        return img;
    }
    
    public String getDate(){
        //times 1000L to format in milliseconds
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(this.timestamp * 1000L));
    }

    @Override
    public String toString() {
        return "User: " + user + "\t\tDate: " + this.getDate() + "\nText: " +  this.getNewText();
    }

}
