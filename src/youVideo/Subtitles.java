package youVideo;

import java.util.Locale;

/**
 * Represents a subtitle track for a video.
 * Stores the language as a Locale object for internationalization support.
 */
public class Subtitles {
    private final Locale language;
    private final String url;

    /**
     * Constructs a new Subtitle entry.
     * @param language the ISO language code (e.g., "en", "pt")
     * @param url the web address where the subtitle file is hosted
     */
    public Subtitles(String language, String url) {
        this.language = Locale.of(language);
        this.url = url;
    }

    public Locale getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }
}