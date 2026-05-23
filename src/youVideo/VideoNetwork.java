package youVideo;
import java.util.List;
import youVideo.Exceptions.*;

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
     void createPublishable(String id, int duration, String url, String publisher, String title, String language)
            throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException;


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
     void createPremium(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl)
            throws InvalidLanguageException,InvalidLanguageSubtitleException, InvalidDurationException, IdAlreadyExistsException;

    /**
     *
     * @param language
     * @param url
     */
     void createSubtitle(String id,String language, String url)
             throws InvalidLanguageSubtitleException, VideoDoesNotExistException, NotAPremiumVideoException;

    /**
     *
     * @param id
     */
     void getVideo(String id)
            throws InvalidPublishableVideoException;

    /**
     *Method t
     * @param id
     */
     PremiumVideosClass getPremiumVideo(String id)
             throws NotAPremiumVideoException ;

    /**
     *
     * @param title
     * @param author
     * @param language
     */
     void createPodcast(String title, String author, String language)
             throws InvalidLanguageException, TitleAlreadyExistException;

    /**
     *
     * @param title
     * @param id
     * @param duration
     * @param url
     * @param releaseDate
     */
     void addEpisode(String title,String id, int duration, String url, String releaseDate)
            throws InvalidDurationException,TitleDoesNotExistsException,IdAlreadyExistsException, InvalidDateException;



    /**
     *
     * @param title
     */
    Podcast getPodcast(String title)
            throws TitleDoesNotExistsException;

    /**
     *
     * @param title
     */
     void listEpisodes(String title)
            throws TitleDoesNotExistsException;

    /**
     *
     * @param authorName
     */
      List<Podcast> authorPodcasts (String authorName);

    /**
     *
     * @param title
     */
     void removePodcast(String title)
            throws PodcastDoesNotExistsException;

    /**
     *
     * @param author
     * @param videoId
     * @param date
     */
     void createShow(String author, String videoId, String date)
            throws VideoForShowDoesNotExistException, ShowAlreadyExistsException;

    /**
     *
     * @param title
     */
     void getShow(String title);

    /**
     *
     * @param authorShows
     */
     void authorShows(String authorShows);

    /**
     *
     * @param title
     */
     void removeShow(String title);

    /**
     *
     * @param id
     */
     void removeVideo(String id);

    /**
     * New Command to implement
     */
     void authorsProductivity();

    /**
     * New command and need the tag parameter
     * @param title
     */
     void addTag(String title,String tag)
             throws TitleDoesNotExistsException,TaggedException;

    /**
     * New command and need the tag parameter
     * @param title
     */
     void removeTag(String title, String tag)
             throws TitleDoesNotExistsException,TaggedException;

    /**
     * New command
     */
     void tagged(String tag,String content, String order)
             throws NoContentTaggedException,TaggedException;

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

    /**
     *
     * @param title
     * @return
     */
    boolean titleAlreadyExist(String title);



}
