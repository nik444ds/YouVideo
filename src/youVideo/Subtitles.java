package youVideo;

import java.util.Locale;

/**
 * Interface representing a subtitle track contract.
 */
public interface Subtitles {

    /**
     * Returns the language of the subtitle track.
     * @return the Locale object representing the subtitle language
     */

    Locale getLanguage();

    /**
     * Returns the URL where the subtitle file can be accessed.
     * @return the subtitle file URL as a String
     */

    String getUrl();
}