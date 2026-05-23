package youVideo;

import youVideo.Exceptions.*;

import java.util.*;

/**
 * Manages all videos, podcasts, shows, tags, and authors in the platform.
 * Uses efficient Java collections as required by phase 2 data size requirements.
 */
public class VideoNetworkClass implements VideoNetwork {

    /** Maps video ID (case-insensitive key) to its VideoStructure object. O(1) search/insert/delete. */
    private final Map<String, VideoStructure> videos;

    /** Maps podcast title (lowercase) to its Podcast object. O(1) search/insert/delete. */
    private final Map<String, Podcast> podcasts;

    /** Maps show title (lowercase) to its Show object. O(1) search/insert/delete. */
    private final Map<String, Show> shows;

    /**
     * Maps author canonical name (lowercase key) to an ordered list of their podcasts.
     * List preserves insertion order as required by the authorpodcasts command.
     */
    private final Map<String, List<Podcast>> podcastsByAuthor;

    /**
     * Maps author canonical name (lowercase key) to a SortedSet of their shows.
     * SortedSet uses a comparator to keep shows ordered by date then title,
     * as required by the authorshows command which is called frequently.
     */
    private final Map<String, SortedSet<Show>> showsByAuthor;

    /**
     * Maps title (lowercase) to a SortedSet of tags (alphabetical order).
     * Tags qualify both shows and podcasts by their title.
     * TreeSet ensures alphabetical order for tag listing.
     */
    private final Map<String, SortedSet<String>> tagsByTitle;

    /**
     * Registry that maps lowercase author names to their canonical (first-registered) form.
     * Ensures author name consistency across podcasts and shows.
     */
    private final IdentityRegistry authorRegistry;

    /**
     * Constructs a new VideoNetworkClass with empty collections.
     * All maps use HashMap for O(1) average-case performance on lookup, insert, and delete.
     */
    public VideoNetworkClass() {
        this.videos = new HashMap<>();
        this.podcasts = new HashMap<>();
        this.shows = new HashMap<>();
        this.podcastsByAuthor = new HashMap<>();
        this.showsByAuthor = new HashMap<>();
        this.tagsByTitle = new HashMap<>();
        this.authorRegistry = new IdentityRegistryClass();
    }

    @Override
    public void createPublishable(String id, int duration, String url, String publisher,
                                  String title, String language)
            throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        if (duration <= 0)
            throw new InvalidDurationException();
        // ID must be unique across all videos (publishable + episodes)
        if (videos.containsKey(id.toLowerCase()))
            throw new IdAlreadyExistsException();

        VideoStructure newVideo = new PublishableVideosClass(id, duration, url, publisher, title, language);
        videos.put(id.toLowerCase(), newVideo);
    }

    @Override
    public void createPremium(String id, int duration, String url, String publisher,
                              String title, String language, String subLanguage, String subUrl)
            throws InvalidLanguageException, InvalidLanguageSubtitleException,
            InvalidDurationException, IdAlreadyExistsException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        if (!isLanguageValid(subLanguage))
            throw new InvalidLanguageSubtitleException();
        if (duration <= 0)
            throw new InvalidDurationException();
        if (videos.containsKey(id.toLowerCase()))
            throw new IdAlreadyExistsException();

        VideoStructure newPremium = new PremiumVideosClass(id, duration, url, publisher,
                title, language, subLanguage, subUrl);
        videos.put(id.toLowerCase(), newPremium);
    }

    @Override
    public void createSubtitle(String id, String url, String language)
            throws InvalidLanguageSubtitleException, VideoDoesNotExistException,
            NotAPremiumVideoException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageSubtitleException();

        VideoStructure video = videos.get(id.toLowerCase());
        if (video == null)
            throw new VideoDoesNotExistException();
        if (!(video instanceof PremiumVideosClass premium))
            throw new NotAPremiumVideoException();

        premium.addSubtitle(language, url);
    }

    @Override
    public void getVideo(String id) throws InvalidPublishableVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        // Episodes are not publishable videos — both null and episode cases throw
        if (video == null || video instanceof EpisodeClass)
            throw new InvalidPublishableVideoException();
        video.display();
    }

    @Override
    public PremiumVideos getPremiumVideo(String id) throws NotAPremiumVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        if (!(video instanceof PremiumVideos premiumVideo))
            throw new NotAPremiumVideoException();
        return premiumVideo;
    }

    @Override
    public void createPodcast(String title, String author, String language)
            throws InvalidLanguageException, TitleAlreadyExistException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        // Podcast titles are unique across all podcasts (case-insensitive)
        if (podcasts.containsKey(title.toLowerCase()))
            throw new TitleAlreadyExistException();

        // Resolve canonical author name (first-registered casing wins)
        String canonicalAuthor = authorRegistry.getCanonical(author);
        if (!authorRegistry.exists(author))
            authorRegistry.register(author);

        Podcast newPodcast = new PodcastClass(title, canonicalAuthor, language);
        podcasts.put(title.toLowerCase(), newPodcast);

        // Register podcast under its author for efficient authorpodcasts lookup
        podcastsByAuthor
                .computeIfAbsent(canonicalAuthor.toLowerCase(), k -> new ArrayList<>())
                .add(newPodcast);
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String releaseDate)
            throws InvalidDurationException, TitleDoesNotExistsException,
            IdAlreadyExistsException, InvalidDateException {
        // Validations must follow the order specified in the assignment
        if (duration <= 0)
            throw new InvalidDurationException();

        Podcast pod = podcasts.get(title.toLowerCase());
        if (pod == null)
            throw new TitleDoesNotExistsException();

        // Episode IDs share the global video ID space
        if (videos.containsKey(id.toLowerCase()))
            throw new IdAlreadyExistsException();

        // New episodes must not be earlier than the latest existing episode
        Iterator<Episode> it = pod.getEpisodesIterator();
        if (it.hasNext()) {
            Episode latestEpisode = it.next();
            if (releaseDate.compareTo(latestEpisode.getReleaseDate()) < 0)
                throw new InvalidDateException();
        }

        Episode ep = new EpisodeClass(id, duration, url, releaseDate);
        pod.addEpisode(ep);
        // Episodes must also be registered in the global video map for ID uniqueness checks
        videos.put(id.toLowerCase(), ep);
    }

    @Override
    public Podcast getPodcast(String title) throws TitleDoesNotExistsException {
        Podcast pod = podcasts.get(title.toLowerCase());
        if (pod == null)
            throw new TitleDoesNotExistsException();
        return pod;
    }

    @Override
    public Iterator<String> getTagsForTitle(String title) {
        SortedSet<String> tags = tagsByTitle.get(title.toLowerCase());
        if (tags == null || tags.isEmpty())
            return Collections.emptyIterator();
        return tags.iterator();
    }

    @Override
    public void listEpisodes(String title) throws TitleDoesNotExistsException {
        Podcast pod = getPodcast(title);
        pod.displayEpisodes();
    }

    @Override
    public Iterator<Podcast> authorPodcasts(String authorName) {
        String canonicalName = authorRegistry.getCanonical(authorName);
        List<Podcast> authorList = podcastsByAuthor.get(canonicalName.toLowerCase());
        if (authorList == null)
            return Collections.emptyIterator();
        return authorList.iterator();
    }

    @Override
    public void removePodcast(String title) throws PodcastDoesNotExistsException {
        Podcast pod = podcasts.remove(title.toLowerCase());
        if (pod == null)
            throw new PodcastDoesNotExistsException();

        // Cascading removal: remove all episodes from the global video map
        Iterator<Episode> it = pod.getEpisodesIterator();
        while (it.hasNext())
            videos.remove(it.next().getId().toLowerCase());

        // Remove from author index
        List<Podcast> authorList = podcastsByAuthor.get(pod.getAuthor().toLowerCase());
        if (authorList != null)
            authorList.remove(pod);

        // Remove tags only if no show shares this title
        if (!shows.containsKey(title.toLowerCase()))
            tagsByTitle.remove(title.toLowerCase());
    }

    @Override
    public void createShow(String author, String videoId, String date)
            throws VideoForShowDoesNotExistException, ShowAlreadyExistsException {
        VideoStructure videoStructure = videos.get(videoId.toLowerCase());

        // Only publishable videos (not episodes) can be used in shows
        if (!(videoStructure instanceof PublishableVideos publishable))
            throw new VideoForShowDoesNotExistException();

        String showTitle = publishable.getTitle();

        // Show titles must be unique (case-insensitive)
        if (shows.containsKey(showTitle.toLowerCase()))
            throw new ShowAlreadyExistsException();

        // Resolve canonical author name
        String canonicalAuthor = authorRegistry.getCanonical(author);
        if (!authorRegistry.exists(author))
            authorRegistry.register(author);

        Show newShow = new ShowClass(canonicalAuthor, publishable, date);
        shows.put(showTitle.toLowerCase(), newShow);

        // Register show under its author for efficient authorshows lookup
        // SortedSet with comparator keeps shows ordered by date then title
        showsByAuthor
                .computeIfAbsent(canonicalAuthor.toLowerCase(),
                        k -> new TreeSet<>(new ShowByDateTitleComparator()))
                .add(newShow);
    }

    @Override
    public void getShow(String title) throws ShowDoesNotExistException {
        Show show = shows.get(title.toLowerCase());
        if (show == null)
            throw new ShowDoesNotExistException();

        System.out.println("Show Date: " + show.getTransmissionDate()
                + " Author: " + show.getAuthor());
        System.out.println("Video: " + show.getTitle());

        // Print tags in alphabetical order if any exist
        Iterator<String> tagIt = getTagsForTitle(title);
        if (tagIt.hasNext()) {
            System.out.println("Tags:");
            while (tagIt.hasNext())
                System.out.println(tagIt.next());
        }
    }

    @Override
    public void authorShows(String authorName) {
        String canonicalAuthor = authorRegistry.getCanonical(authorName);
        SortedSet<Show> authorShowSet = showsByAuthor.get(canonicalAuthor.toLowerCase());

        System.out.println("Shows by author " + authorName + ":");
        if (authorShowSet == null || authorShowSet.isEmpty()) {
            System.out.println("No shows found for this author.");
            return;
        }
        // SortedSet already maintains date-then-title order
        for (Show show : authorShowSet) {
            System.out.println("Date: " + show.getTransmissionDate()
                    + " Show: " + show.getTitle()
                    + " Duration: " + show.getVideo().getDuration()
                    + " Language: " + show.getVideo().getLanguage()
                    .getLanguage().toUpperCase());
        }
    }

    @Override
    public void removeShow(String title) throws ShowDoesNotExistException {
        Show show = shows.remove(title.toLowerCase());
        if (show == null)
            throw new ShowDoesNotExistException();

        // Remove from author index
        SortedSet<Show> authorShowSet = showsByAuthor.get(show.getAuthor().toLowerCase());
        if (authorShowSet != null)
            authorShowSet.remove(show);

        // Remove tags only if no podcast shares this title
        if (!podcasts.containsKey(title.toLowerCase()))
            tagsByTitle.remove(title.toLowerCase());
    }

    @Override
    public void removeVideo(String id)
            throws VideoDoesNotExistException, CannotRemoveEpisodeException,
            CannotRemoveShowVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        if (video == null)
            throw new VideoDoesNotExistException();
        if (video instanceof EpisodeClass)
            throw new CannotRemoveEpisodeException();

        // Check if any show references this video
        for (Show show : shows.values()) {
            if (show.getVideo().getId().equalsIgnoreCase(id))
                throw new CannotRemoveShowVideoException();
        }
        videos.remove(id.toLowerCase());
    }

    @Override
    public void authorsProductivity() {
        // Build a list of all authors with their total contribution count
        // This is computed on demand (once a month per spec), so no need to maintain live
        Map<String, Integer> productivity = new HashMap<>();

        for (Map.Entry<String, List<Podcast>> entry : podcastsByAuthor.entrySet())
            productivity.merge(entry.getKey(), entry.getValue().size(), Integer::sum);

        for (Map.Entry<String, SortedSet<Show>> entry : showsByAuthor.entrySet())
            productivity.merge(entry.getKey(), entry.getValue().size(), Integer::sum);

        if (productivity.isEmpty()) {
            System.out.println("No productive authors.");
            return;
        }

        // Sort by count descending, then alphabetically by canonical name
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(productivity.entrySet());
        sorted.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            return authorRegistry.getCanonical(a.getKey())
                    .compareToIgnoreCase(authorRegistry.getCanonical(b.getKey()));
        });

        System.out.println("Authors productivity:");
        for (Map.Entry<String, Integer> entry : sorted) {
            String canonicalName = authorRegistry.getCanonical(entry.getKey());
            System.out.println(canonicalName + " with " + entry.getValue() + " contributions.");
        }
    }

    @Override
    public void addTag(String title, String tag)
            throws TitleDoesNotExistsException, TaggedException {
        boolean hasPodcast = podcasts.containsKey(title.toLowerCase());
        boolean hasShow = shows.containsKey(title.toLowerCase());

        if (!hasPodcast && !hasShow)
            throw new TitleDoesNotExistsException();

        String keyMap = title.toLowerCase();
        SortedSet<String> tags = tagsByTitle.computeIfAbsent(keyMap, k -> new TreeSet<>());

        // Check case-insensitively if the tag is already present
        for (String existing : tags) {
            if (existing.equalsIgnoreCase(tag))
                throw new TaggedException(tag);
        }
        tags.add(tag);
    }

    @Override
    public void removeTag(String title, String tag)
            throws TitleDoesNotExistsException, TagNotPresentException {
        boolean hasPodcast = podcasts.containsKey(title.toLowerCase());
        boolean hasShow = shows.containsKey(title.toLowerCase());

        if (!hasPodcast && !hasShow)
            throw new TitleDoesNotExistsException();

        SortedSet<String> tags = tagsByTitle.get(title.toLowerCase());

        // Find and remove the tag case-insensitively
        String toRemove = null;
        if (tags != null) {
            for (String existing : tags) {
                if (existing.equalsIgnoreCase(tag)) {
                    toRemove = existing;
                    break;
                }
            }
        }
        if (toRemove == null)
            throw new TagNotPresentException(tag);

        tags.remove(toRemove);
        if (tags.isEmpty())
            tagsByTitle.remove(title.toLowerCase());
    }

    @Override
    public void tagged(String tag, String content, String order)
            throws InvalidTagParametersException, NoContentTaggedException {
        boolean validContent = content.equalsIgnoreCase("SHOW")
                || content.equalsIgnoreCase("PODCAST")
                || content.equalsIgnoreCase("ALL");
        boolean validOrder = order.equalsIgnoreCase("ASC")
                || order.equalsIgnoreCase("DES");

        if (!validContent || !validOrder)
            throw new InvalidTagParametersException();

        List<TaggedContent> result = new ArrayList<>();

        // Collect matching shows
        if (content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("SHOW")) {
            for (Show show : shows.values()) {
                if (hasTag(show.getTitle(), tag))
                    result.add(show);
            }
        }
        // Collect matching podcasts
        if (content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("PODCAST")) {
            for (Podcast pod : podcasts.values()) {
                if (hasTag(pod.getTitle(), tag))
                    result.add(pod);
            }
        }

        if (result.isEmpty())
            throw new NoContentTaggedException();

        // Sort using the comparator: by title (ASC/DES), show before podcast on tie
        result.sort(new ComparatorClass(order));

        String orderLabel = order.equalsIgnoreCase("ASC") ? "Ascending" : "Descending";
        System.out.println("Content tagged with " + tag + " in " + orderLabel + " order:");
        for (TaggedContent c : result) {
            if (c instanceof Show show)
                System.out.println("Show Title: " + show.getTitle()
                        + " Author: " + show.getAuthor());
            else if (c instanceof Podcast pod)
                System.out.println("Podcast Title: " + pod.getTitle()
                        + " Author: " + pod.getAuthor());
        }
    }

    /* -----------------------------------------------------------------------
                               AUXILIARY METHODS
       ----------------------------------------------------------------------- */

    @Override
    public boolean isLanguageValid(String code) {
        String[] languages = Locale.getISOLanguages();
        for (String lang : languages) {
            if (lang.equalsIgnoreCase(code))
                return true;
        }
        return false;
    }

    @Override
    public String getCanonicalAuthor(String authorInput) {
        return authorRegistry.getCanonical(authorInput);
    }

    /**
     * Checks whether a given tag qualifies a title, case-insensitively.
     *
     * @param title     the title to check
     * @param searchTag the tag to look for
     * @return {@code true} if the tag is present for this title
     */
    private boolean hasTag(String title, String searchTag) {
        SortedSet<String> tags = tagsByTitle.get(title.toLowerCase());
        if (tags == null) return false;
        for (String t : tags) {
            if (t.equalsIgnoreCase(searchTag))
                return true;
        }
        return false;
    }
}