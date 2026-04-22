package youVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;

import java.util.Locale;

public class Podcasts implements  Titled, Authored, Languaged {
private final String title;
private final String author;
private final Locale language;
private final Array<Episode> episode;

    public Podcasts( String title, String author, String language){
        this.title = title;
        this.author = author;
        this.language = new Locale (language);
        this.episode = new ArrayClass<>();
    }
    @Override
    public String getTitle(){
        return title;
    }
    @Override
    public String getAuthor(){
        return author;
    }
    @Override
    public Locale getLanguage(){
        return language;
    }

    public Array<Episode> getEpisode(){
        return episode;
    }

    public void addEpisode(Episode episode){
        this.episode.insertAt(episode,0);
    }
}
