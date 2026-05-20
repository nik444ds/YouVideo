package youVideo;

import java.util.Locale;

/**
 * Interface representing a subtitle track contract.
 */
public interface Subtitles {
    Locale getLanguage();
    String getUrl();
}