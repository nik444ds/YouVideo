package youVideo;

import youVideo.Exceptions.*;

import java.util.Iterator;
import java.util.Map;

/**
 * Top-level interface for the YouVideo platform.
 * Each method corresponds to one user command.
 * All output is handled by the Main class; these methods return data via iterators or objects.
 */
public interface VideoNetwork {

    // -----------------------------------------------------------------------
    //  Video commands
    // -----------------------------------------------------------------------

    /**
     * Creates a new publishable video.
     *
     * @param id        unique video identifier
     * @param duration  duration in minutes (must be > 0)
     * @param url       file URL
     * @param publisher publisher name
     * @param title     video title
     * @param language  two-letter ISO 639-1 language code
     * @throws InvalidLanguageException  if the language code is invalid
     * @throws InvalidDurationException  if duration <= 0
     * @throws IdAlreadyExistsException  if the id is already in use
     */
    void createPublishable(String id, int duration, String url, String publisher,
                           String title, String language)
            throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException;

    /**
     * Creates a new premium video with an initial subtitle.
     *
     * @param id          unique video identifier
     * @param duration    duration in minutes (must be > 0)
     * @param url         file URL
     * @param publisher   publisher name
     * @param title       video title
     * @param language    primary language code
     * @param subLanguage subtitle language code
     * @param subUrl      subtitle file URL
     * @throws InvalidLanguageException         if the primary language code is invalid
     * @throws InvalidLanguageSubtitleException if the subtitle language code is invalid
     * @throws InvalidDurationException         if duration <= 0
     * @throws IdAlreadyExistsException         if the id is already in use
     */
    void createPremium(String id, int duration, String url, String publisher, String title,
                       String language, String subLanguage, String subUrl)
            throws InvalidLanguageException, InvalidLanguageSubtitleException,
            InvalidDurationException, IdAlreadyExistsException;

    /**
     * Adds a subtitle to an existing premium video.
     *
     * @param id       premium video identifier
     * @param url      subtitle file URL
     * @param language subtitle language code
     * @throws InvalidLanguageSubtitleException if the subtitle language code is invalid
     * @throws VideoDoesNotExistException       if no video with id exists
     * @throws NotAPremiumVideoException        if the video is not a premium video
     */
    void createSubtitle(String id, String url, String language)
            throws InvalidLanguageSubtitleException, VideoDoesNotExistException,
            NotAPremiumVideoException;

    /**
     * Returns a publishable video by its identifier.
     * Episodes are not considered publishable and will cause an exception.
     *
     * @param id video identifier
     * @return the PublishableVideos object
     * @throws InvalidPublishableVideoException if id does not reference a publishable video
     */
    PublishableVideos getVideo(String id) throws InvalidPublishableVideoException;

    /**
     * Returns a premium video by its identifier.
     *
     * @param id video identifier
     * @return the PremiumVideos object
     * @throws NotAPremiumVideoException if id does not reference a premium video
     */
    PremiumVideos getPremiumVideo(String id) throws NotAPremiumVideoException;

    /**
     * Removes a publishable video by its identifier.
     * Episodes and videos used in shows cannot be removed.
     *
     * @param id video identifier
     * @throws VideoDoesNotExistException      if no video with id exists
     * @throws CannotRemoveEpisodeException    if the video is a podcast episode
     * @throws CannotRemoveShowVideoException  if the video is referenced by a show
     */
    void removeVideo(String id)
            throws VideoDoesNotExistException, CannotRemoveEpisodeException,
            CannotRemoveShowVideoException;

    // -----------------------------------------------------------------------
    //  Podcast commands
    // -----------------------------------------------------------------------

    /**
     * Creates a new empty podcast.
     *
     * @param title    unique podcast title
     * @param author   author name
     * @param language two-letter ISO 639-1 language code
     * @throws InvalidLanguageException  if the language code is invalid
     * @throws TitleAlreadyExistException if a podcast with this title already exists
     */
    void createPodcast(String title, String author, String language)
            throws InvalidLanguageException, TitleAlreadyExistException;

    /**
     * Adds a new episode to a podcast.
     *
     * @param title       podcast title
     * @param id          unique episode identifier
     * @param duration    duration in minutes (must be > 0)
     * @param url         episode file URL
     * @param releaseDate release date in YYYY-MM-DD format
     * @throws InvalidDurationException   if duration <= 0
     * @throws TitleDoesNotExistsException if no podcast with this title exists
     * @throws IdAlreadyExistsException    if the episode id is already in use
     * @throws InvalidDateException        if the release date is earlier than the latest episode
     */
    void addEpisode(String title, String id, int duration, String url, String releaseDate)
            throws InvalidDurationException, TitleDoesNotExistsException,
            IdAlreadyExistsException, InvalidDateException;

    /**
     * Returns a podcast by its title.
     *
     * @param title podcast title
     * @return the Podcast object
     * @throws TitleDoesNotExistsException if no podcast with this title exists
     */
    Podcast getPodcast(String title) throws TitleDoesNotExistsException;

    /**
     * Returns an iterator over the tags associated with a title, in alphabetical order.
     *
     * @param title the title (podcast or show) to look up
     * @return iterator of tag strings; empty if none exist
     */
    Iterator<String> getTagsForTitle(String title);

    /**
     * Validates episode listing prerequisites.
     *
     * @param title podcast title
     * @throws TitleDoesNotExistsException if no podcast with this title exists
     */
    void listEpisodes(String title) throws TitleDoesNotExistsException;

    /**
     * Returns an iterator over all podcasts by the given author, in insertion order.
     * Always succeeds; returns an empty iterator if the author has no podcasts.
     *
     * @param authorName author name
     * @return iterator of Podcast objects
     */
    Iterator<Podcast> authorPodcasts(String authorName);

    /**
     * Removes a podcast and all its episodes from the system.
     *
     * @param title podcast title
     * @throws PodcastDoesNotExistsException if no podcast with this title exists
     */
    void removePodcast(String title) throws PodcastDoesNotExistsException;

    // -----------------------------------------------------------------------
    //  Show commands
    // -----------------------------------------------------------------------

    /**
     * Creates a new show using an existing publishable video.
     *
     * @param author  show author
     * @param videoId identifier of the publishable video to broadcast
     * @param date    transmission date
     * @throws VideoForShowDoesNotExistException if videoId does not reference a publishable video
     * @throws ShowAlreadyExistsException        if a show with the same title already exists
     */
    void createShow(String author, String videoId, String date)
            throws VideoForShowDoesNotExistException, ShowAlreadyExistsException;

    /**
     * Returns a show by its title.
     *
     * @param title show title
     * @return the Show object
     * @throws ShowDoesNotExistException if no show with this title exists
     */
    Show getShow(String title) throws ShowDoesNotExistException;

    /**
     * Returns an iterator over all shows by the given author, ordered by date then title.
     * Always succeeds; returns an empty iterator if the author has no shows.
     *
     * @param authorName author name
     * @return iterator of Show objects
     */
    Iterator<Show> authorShows(String authorName);

    /**
     * Removes a show without affecting its underlying video.
     *
     * @param title show title
     * @throws ShowDoesNotExistException if no show with this title exists
     */
    void removeShow(String title) throws ShowDoesNotExistException;

    // -----------------------------------------------------------------------
    //  Author productivity
    // -----------------------------------------------------------------------

    /**
     * Returns an iterator over author productivity entries, sorted by contribution count
     * descending and alphabetically by name on ties.
     * Returns an empty iterator if no productive authors exist.
     *
     * @return iterator of Map.Entry(lowercaseAuthorKey, contributionCount)
     */
    Iterator<Map.Entry<String, Integer>> authorsProductivity();

    // -----------------------------------------------------------------------
    //  Tag commands
    // -----------------------------------------------------------------------

    /**
     * Adds a tag to a title (show and/or podcast with that title).
     * @param title the title to tag
     * @param tag   the tag to add
     * @throws TitleDoesNotExistsException if no show or podcast has this title
     * @throws TaggedException             if this title is already tagged with the tag
     */
    void addTag(String title, String tag)
            throws TitleDoesNotExistsException, TaggedException;

    /**
     * Removes a tag from a title.
     *
     * @param title the title to untag
     * @param tag   the tag to remove
     * @throws TitleDoesNotExistsException if no show or podcast has this title
     * @throws TagNotPresentException      if this title is not tagged with the tag
     */
    void removeTag(String title, String tag)
            throws TitleDoesNotExistsException, TagNotPresentException;

    /**
     * Returns an iterator over content tagged with the given tag, sorted as specified.
     *
     * @param tag     the tag to search for
     * @param content filter: "SHOW", "PODCAST", or "ALL"
     * @param order   sort order: "ASC" or "DES"
     * @return iterator of TaggedContent (shows and/or podcasts)
     * @throws InvalidTagParametersException if content or order values are invalid
     * @throws NoContentTaggedException      if no content is tagged with this tag
     */
    Iterator<TaggedContent> tagged(String tag, String content, String order)
            throws InvalidTagParametersException, NoContentTaggedException;

    // -----------------------------------------------------------------------
    //  Utility methods
    // -----------------------------------------------------------------------

    /**
     * Verifies whether the provided language code exists in the ISO 639-1 standard.
     *
     * @param code the language code to validate
     * @return true if valid, false otherwise
     * @pre code != null
     */
    boolean isLanguageValid(String code);

    /**
     * Returns the canonical (first-registered) form of an author name.
     *
     * @param authorInput the author name to look up
     * @return the canonical name, or the input if not registered
     */
    String getCanonicalAuthor(String authorInput);
}