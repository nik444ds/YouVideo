package youVideo;

import java.util.Iterator;

/**
 * Interface representing a Podcast entity that aggregates multiple episodes.
 * Extends Titled, Authored, Languaged, and Iterable.
 */
public interface Podcast extends TaggedContent, Languaged, Iterable<Episode> {
    /**
     * Adds a new episode to the podcast.
     * @param episode the episode to be added
     */
    void addEpisode(Episode episode);

    /**
     * Returns an iterator over the episodes to preserve encapsulation.
     * @return an Iterator of Episode objects
     */
    Iterator<Episode> getEpisodesIterator();


    /**
     * Displays all episodes of the podcast to standard output.
     * Shows each episode's information (title, duration, etc.) on a new line.
     */

    void displayEpisodes();

    /**
     * Displays podcast metadata to standard output.
     * Includes title, author, language, tags, and total episode count.
     */

    void display();
}