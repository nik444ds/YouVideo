package youVideo;

import java.util.Locale;

/**
 * Represents a video that can be published with metadata such as title and publisher.
 * Extends VideoStructure and implements Titled and Languaged interfaces.
 */
public class PublishableVideos extends VideoStructure implements Titled, Languaged {
    private final String publisher;
    private final String title;
    private final Locale language;

    /**
     * Constructs a new PublishableVideo.
     * @param id the unique identifier for the video
     * @param duration the length of the video in minutes
     * @param url the web address of the video
     * @param publisher the entity responsible for publishing
     * @param title the title of the video
     * @param language the ISO language code
     */
    public PublishableVideos(String id, int duration, String url, String publisher, String title, String language) {
        super(id, duration, url);
        this.publisher = publisher;
        this.title = title;
        this.language = Locale.of(language);
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Locale getLanguage() {
        return language;
    }

    /**
     * Displays the publishable video data in a formatted two-line output.
     */
    @Override
    public void display() {
        System.out.println("Video " + getId() + " " + getDuration() + " Title: " + getTitle());
        System.out.println("File: " + getUrl() + " Publisher: " + getPublisher() + " Language: " + getLanguage().getDisplayLanguage().toUpperCase());
    }
}