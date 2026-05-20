package youVideo;

/**
 * Interface representing a video that can be published with metadata.
 * Extends VideoStructure, Titled, and Languaged.
 */
public interface PublishableVideos extends VideoStructure, Titled, Languaged {
    /**
     * Returns the entity responsible for publishing the video.
     * @return a String representing the publisher
     */
    String getPublisher();
}