package youVideo;

public interface VideoStructure {
    /**
     * Returns the unique identifier of the video.
     * @return the video ID as a String
     */

    public String getId();

    /**
     * Returns the duration of the video.
     * @return the duration in seconds (or appropriate time unit)
     */


    public int getDuration();

    /**
     * Returns the URL where the video can be accessed.
     * @return the video URL as a String
     */


    public String getUrl();

    /**
     * Abstract method to be implemented by subclasses to define their specific
     * data presentation format.
     */
    public abstract void display();


}
