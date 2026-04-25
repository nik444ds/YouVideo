package youVideo;

/**
 * Represents a Show scheduled for transmission.
 * Instead of inheriting from VideoStructure, it uses composition by holding
 * a reference to a PublishableVideo.
 */
public class Shows implements Titled, Authored {
    private final String author;
    private final PublishableVideos video;
    private final String transmissionDate;

    /**
     * Constructs a new Show.
     * @param author the author/host of the show
     * @param video the publishable video associated with this show
     * @param date the scheduled transmission date
     */
    public Shows(String author, PublishableVideos video, String date) {
        this.author = author;
        this.video = video;
        this.transmissionDate = date;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the video object associated with this show.
     * @return the PublishableVideos instance
     */
    public PublishableVideos getvideo() {
        return video;
    }

    public String getTransmissionDate() {
        return transmissionDate;
    }

    /**
     * Delegates the title retrieval to the associated video object.
     * @return the title of the underlying video
     */
    @Override
    public String getTitle() {
        return this.video.getTitle();
    }
}