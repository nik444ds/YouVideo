package youVideo;

import java.util.Locale;

/**
 * Concrete implementation of the Subtitles interface.
 */
public class SubtitlesClass implements Subtitles {
    private final Locale language;
    private final String url;

    public SubtitlesClass(String language, String url) {
        this.language = Locale.of(language);
        this.url = url;
    }

    @Override
    public Locale getLanguage() {
        return language;
    }

    @Override
    public String getUrl() {
        return url;
    }
}