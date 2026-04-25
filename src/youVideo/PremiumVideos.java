package youVideo;

import dataStructures.*;

/**
 * Represents a premium version of a publishable video that includes subtitles.
 * Inherits all attributes from PublishableVideos and adds a subtitle management system.
 */
public class PremiumVideos extends PublishableVideos {

    private final Array<Subtitles> subtitles = new ArrayClass<>();

    /**
     * Constructs a new PremiumVideo with an initial mandatory subtitle.
     * @param id unique identifier
     * @param duration video length
     * @param url video URL
     * @param publisher publishing entity
     * @param title video title
     * @param language primary video language
     * @param subLanguage language of the first subtitle
     * @param subUrl URL of the first subtitle file
     */
    public PremiumVideos(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl) {
        super(id, duration, url, publisher, title, language);
        Subtitles subtitle = new Subtitles(subLanguage, subUrl);
        subtitles.insertLast(subtitle);
    }

    /**
     * Returns the collection of subtitles available for this premium video.
     * @return an Array of Subtitles
     */
    public Array<Subtitles> getSubtitles() {
        return subtitles;
    }

    /**
     * Adds a new subtitle track to the premium video.
     * @param language the language code for the new subtitle
     * @param url the URL where the subtitle file is located
     */
    public void addSubtitle(String language, String url) {
        subtitles.insertLast(new Subtitles(language, url));
    }

    /**
     * Displays the premium video data, identifying it as a PREMIUM type in the output.
     */
    @Override
    public void display() {
        System.out.println("PREMIUM Video " + getId() + " " + getDuration() + " Title: " + getTitle());
        System.out.println("File: " + getUrl() + " Publisher: " + getPublisher() +
                " Language: " + getLanguage().getDisplayLanguage().toUpperCase());
    }
}