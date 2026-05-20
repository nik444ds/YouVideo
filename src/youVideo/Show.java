package youVideo;

/**
 * Interface representing a Show scheduled for transmission.
 * Extends Titled and Authored.
 */
public interface Show extends Titled, Authored {
    /**
     * Returns the publishable video associated with this show.
     * @return the PublishableVideos interface instance
     */
    PublishableVideos getVideo();

    /**
     * Returns the scheduled transmission date.
     * @return a String representing the transmission date
     */
    String getTransmissionDate();
}