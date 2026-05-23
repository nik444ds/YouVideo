package youVideo;

/**
 * Represents a Show scheduled for transmission using composition.
 */
public class ShowClass implements Show {
    private final String author;
    private final PublishableVideos video;
    private final String transmissionDate;

    public ShowClass(String author, PublishableVideos video, String date) {
        this.author = author;
        this.video = video;
        this.transmissionDate = date;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public PublishableVideos getVideo() {
        return video;
    }

    @Override
    public String getTransmissionDate() {
        return transmissionDate;
    }

    @Override
    public String getTitle() {
        return this.video.getTitle();
    }
}