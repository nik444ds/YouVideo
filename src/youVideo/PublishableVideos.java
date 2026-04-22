package youVideo;
import java.util.Locale;
/*
 A class which extend VideoStructure and implements a publisher, title and languages for a video .

 */
public class PublishableVideos extends VideoStructure implements Titled,Languaged{
    private final String publisher;
    private final String title;
    private final Locale language;

    /*
    Constructor class
     */
    public PublishableVideos(String id, int duration, String url, String publisher, String title, String language) {
        super(id, duration, url);
        this.publisher = publisher;
        this.title = title;
        this.language = new Locale(language);
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



}
