package youVideo;

/**
 * Interface representing a specific episode of a podcast.
 * Extends VideoStructure as it shares core playback attributes.
 */
public interface Episode extends VideoStructure {
    /**
     * Returns the date when the episode was published.
     * @return a String representing the release date
     */
    String getReleaseDate();
}