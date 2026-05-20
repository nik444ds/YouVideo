package youVideo;

public interface VideoStructure {
    public String getId();

    public int getDuration();

    public String getUrl();

    /**
     * Abstract method to be implemented by subclasses to define their specific
     * data presentation format.
     */
    public abstract void display();


}
