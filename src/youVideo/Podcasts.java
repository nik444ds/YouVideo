package youVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;
import java.util.Locale;

/**
 * Represents a Podcast entity that aggregates multiple episodes.
 * Implements Titled, Authored, and Languaged interfaces to ensure
 * consistent access to metadata.
 */
public class Podcasts implements Titled, Authored, Languaged {
    private final String title;
    private final String author;
    private final Locale language;
    private final Array<Episode> episode;

    /**
     * Constructs a new Podcast and initializes its episode collection.
     * @param title the title of the podcast
     * @param author the creator/author of the podcast
     * @param language the ISO language code (converted to Locale)
     */
    public Podcasts(String title, String author, String language) {
        this.title = title;
        this.author = author;
        this.language = Locale.of(language);
        this.episode = new ArrayClass<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Locale getLanguage() {
        return language;
    }

    /**
     * Returns the list of episodes associated with this podcast.
     * @return an Array of Episode objects
     */
    public Array<Episode> getEpisode() {
        return episode;
    }

    /**
     * Adds a new episode to the podcast.
     * New episodes are inserted at the beginning of the list (position 0)
     * to maintain a "latest first" order.
     * @param episode the episode to be added
     */
    public void addEpisode(Episode episode) {
        this.episode.insertAt(episode, 0);
    }
}