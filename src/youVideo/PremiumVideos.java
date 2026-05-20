package youVideo;

import java.util.Iterator;

/**
 * Interface representing a premium video with subtitle management.
 * Extends PublishableVideos and Iterable to protect encapsulation.
 */
public interface PremiumVideos extends PublishableVideos, Iterable<Subtitles> {
    /**
     * Adds a new subtitle track to the premium video.
     * @param language the language code for the new subtitle
     * @param url the URL where the subtitle file is located
     */
    void addSubtitle(String language, String url);

    /**
     * Returns an iterator over the subtitles to avoid exposing internal lists.
     * @return an Iterator of Subtitles
     */
    Iterator<Subtitles> getSubtitlesIterator();
}