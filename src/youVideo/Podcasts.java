package youVideo;

import dataStructures.Array;
import dataStructures.ArrayClass;

import java.util.Locale;

public class Podcasts  {
private String title;
private String author;
private Locale language;
private Array<Episode> episode;

    public Podcasts( String title, String author, String language){
        this.title = title;
        this.author = author;
        this.language = new Locale (language);
        this.episode = new ArrayClass<>();
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
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
