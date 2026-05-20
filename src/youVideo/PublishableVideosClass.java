package youVideo;

import java.util.Locale;

/**
 * Represents a video that can be published with metadata such as title and publisher.
 */
public class PublishableVideosClass extends AbstractVideoStructureClass implements PublishableVideos {
    private final String publisher;
    private final String title;
    private final Locale language;

    public PublishableVideosClass(String id, int duration, String url, String publisher, String title, String language) {
        super(id, duration, url);
        this.publisher = publisher;
        this.title = title;
        this.language = Locale.of(language);
    }

    @Override
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

    @Override
    public void display() {
        System.out.println("Video " + getId() + " " + getDuration() + " Title: " + getTitle());
        System.out.println("File: " + getUrl() + " Publisher: " + getPublisher());
    }
}