package youVideo;

/**
 * Base abstract class representing the core structure of any video in the system.
 * Defines shared attributes like ID, duration, and URL that all video types must possess.
 */
public abstract class VideoStructure {
    // Using final for immutability as these core attributes shouldn't change after creation
    private final String id;
    private final int duration;
    private final String url;

    /**
     * Constructs a new VideoStructure.
     * @param id the unique identifier for the video
     * @param duration the length of the video in minutes
     * @param url the web address of the video content
     */
    public VideoStructure(String id, int duration, String url) {
        this.id = id;
        this.duration = duration;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Abstract method to be implemented by subclasses to define their specific
     * data presentation format.
     */
    public abstract void display();

}