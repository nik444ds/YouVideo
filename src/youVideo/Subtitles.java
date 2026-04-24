package youVideo;
import java.util.Locale;
/*
This class contains the subtitles of videos that can be used by PremiumVideos.
 */
public class Subtitles  {
    private final Locale language;
    private final String url;

    public Subtitles (String language, String url){
        this.language =  Locale.of(language);
        this.url = url;
    }

    public Locale getLanguage(){
        return language;
    }
    public String getUrl(){
        return url;
    }

}
