package youVideo;

/**
 * Represents a specific episode of a podcast.
 * Extends the basic VideoStructure by adding podcast-specific metadata like release date.
 */
public class EpisodeClass extends AbstractVideoStructureClass implements Episode {
    private final String releaseDate;

    /**
     * Constructs a new Episode.
     * @param id the unique identifier for the episode
     * @param duration the length of the episode in minutes
     * @param url the web address of the episode
     * @param releaseDate the date when the episode was published
     */
    public EpisodeClass(String id, int duration, String url, String releaseDate) {
        super(id, duration, url);
        this.releaseDate = releaseDate;
    }

    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Implements the display contract from VideoStructure.
     */
    @Override
    public void display() {
        System.out.println("Episode " + getId() + ": " + getDuration() + " min Date: " + getReleaseDate());
        System.out.println("URL: " + getUrl());
    }
}