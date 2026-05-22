package youVideo;

public interface VideoNetwork {


    /**
     *
     * @param id
     * @param duration
     * @param url
     * @param publisher
     * @param title
     * @param language
     */
    public void createPublishable(String id, int duration, String url, String publisher, String title, String language);

    /**
     *
     * @param id
     * @param duration
     * @param url
     * @param publisher
     * @param title
     * @param language
     * @param subLanguage
     * @param subUrl
     */
    public void createPremium(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl);

    /**
     *
     * @param language
     * @param url
     */
    public void createSubtitle(String id,String language, String url);

    /**
     *
     * @param id
     */
    public void getVideo(String id);

    /**
     *Method t
     * @param id
     */
    public PremiumVideosClass getPremiumVideo(String id);

    /**
     *
     * @param title
     * @param author
     * @param language
     */
    public void createPodcast(String title, String author, String language);

    /**
     *
     * @param id
     * @param duration
     * @param url
     * @param releaseDate
     */
    public void addEpisode(String id, int duration, String url, String releaseDate);

    /**
     *
     * @param title
     */
    public Podcast getPodcast(String title);

    /**
     *
     * @param title
     */
    public void listEpisodes(String title);

    /**
     *
     * @param authorName
     */
    public void authorPodcasts(String authorName);

    /**
     *
     * @param title
     */
    public void removePodcast(String title);

    /**
     *
     * @param author
     * @param video
     * @param date
     */
    public void createShow(String author, PublishableVideosClass video, String date);;

    /**
     *
     * @param title
     */
    public void getShow(String title);

    /**
     *
     * @param authorShows
     */
    public void authorShows(String authorShows);

    /**
     *
     * @param title
     */
    public void removeShow(String title);

    /**
     *
     * @param id
     */
    public void removeVideo(String id);

    /**
     * New Command to implement
     */
    public void authorsProductivity();

    /**
     * New command and need the tag parameter
     * @param title
     */
    public void addTag(String title);

    /**
     * New command and need the tag parameter
     * @param title
     */
    public void removeTag(String title);

    /**
     * New command
     */
    public void tagged();

    /**
     * Verifies if the provided language code exists in the ISO 639 standard library
     * @param code the language code to validate
     * @return true if the language exists, false otherwise
     * @pre code != null
     */
    boolean isLanguageValid(String code);

    /**
     * Search for a name in a map, if the name exist, return, else return the name created
     * @param authorInput
     * @return
     */
    String getNameAuthor(String authorInput);

    boolean titleAlreadyExist(String title);
}
