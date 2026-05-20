package youVideo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a premium version of a publishable video that includes subtitles.
 */
public class PremiumVideosClass extends PublishableVideosClass implements PremiumVideos {

    private final List<Subtitles> subtitles = new ArrayList<>();

    public PremiumVideosClass(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl) {
        super(id, duration, url, publisher, title, language);
        SubtitlesClass subtitle = new SubtitlesClass(subLanguage, subUrl);
        subtitles.add(new SubtitlesClass(subLanguage,subUrl));
    }

    @Override
    public void addSubtitle(String language, String url) {
        subtitles.add(new SubtitlesClass(language, url));
    }

    @Override
    public Iterator<Subtitles> getSubtitlesIterator() {
        return subtitles.iterator();
    }

    @Override
    public Iterator<SubtitlesClass> iterator() {
        return getSubtitlesIterator();
    }

    @Override
    public void display() {
        System.out.println("PREMIUM Video " + getId() + " " + getDuration() + " Title: " + getTitle());
        System.out.println("File: " + getUrl() + " Publisher: " + getPublisher());
    }
}