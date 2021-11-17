package BugsLife;

public class React {
    
    private String reaction;
    private int count;
    
    public React() {
    }
    
    public React(String reaction, int count) {
        this.reaction = reaction;
        this.count = count;
    }
    
    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return reaction + ": " + count;
    }
}
