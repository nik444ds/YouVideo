package youVideo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Represents a Podcast entity that aggregates multiple episodes.
 */
public class PodcastClass implements Podcast {
    private final String title;
    private final String author;
    private final Locale language;
    private final List<Episode> episode;

    public PodcastClass(String title, String author, String language) {
        this.title = title;
        this.author = author;
        this.language = Locale.of(language);
        this.episode = new LinkedList<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Locale getLanguage() {
        return language;
    }

    @Override
    public void addEpisode(Episode ep) {
        //  "latest first"
        this.episode.add(0, ep);
    }

    @Override
    public Iterator<Episode> getEpisodesIterator() {
        return episode.iterator();
    }

    @Override
    public Iterator<Episode> iterator() {
        return getEpisodesIterator();
    }

    // new helper method

    public void displayEpisodes() {
        Iterator<Episode> it = getEpisodesIterator();
        if (!it.hasNext()) {
            System.out.println("No episodes available for this podcast.");
            return;
        }
        System.out.println("Episodes for podcast " + getTitle() + ":");
        while (it.hasNext()) {
            Episode ep = it.next();
            System.out.println("Episode " + ep.getId() + ": " + ep.getDuration() + " min Date : " + ep.getReleaseDate());
            System.out.println("URL: " + ep.getUrl());
        }
    }

    public void display() {
        System.out.println("Podcast: " + getTitle() + " Author: " + getAuthor() + " Language: " + getLanguage().getLanguage().toUpperCase());
    }
}