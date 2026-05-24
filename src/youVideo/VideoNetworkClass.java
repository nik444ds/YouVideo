package youVideo;

import youVideo.Exceptions.*;

import java.util.*;

/**
 * Manages all videos, podcasts, shows, tags, and authors in the platform.
 * Uses efficient Java collections as required by phase 2 data size requirements.
 */
public class VideoNetworkClass implements VideoNetwork {

    /** Maps video ID (lowercase key) to its VideoStructure object. O(1) search/insert/delete. */
    private final Map<String, VideoStructure> videos;

    /** Maps podcast title (lowercase key) to its Podcast object. O(1) search/insert/delete. */
    private final Map<String, Podcast> podcasts;

    /** Maps show title (lowercase key) to its Show object. O(1) search/insert/delete. */
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
     * Maps title (lowercase key) to a SortedSet of tags (alphabetical order).
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
        this.videos          = new HashMap<>();
        this.podcasts        = new HashMap<>();
        this.shows           = new HashMap<>();
        this.podcastsByAuthor = new HashMap<>();
        this.showsByAuthor   = new HashMap<>();
        this.tagsByTitle     = new HashMap<>();
        this.authorRegistry  = new IdentityRegistryClass();
    }

    // -----------------------------------------------------------------------
    //  Video commands
    // -----------------------------------------------------------------------

    @Override
    public void createPublishable(String id, int duration, String url, String publisher,
                                  String title, String language)
            throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        if (duration <= 0)
            throw new InvalidDurationException();
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

        // FIX: use PremiumVideos interface, not PremiumVideosClass
        if (!(video instanceof PremiumVideos premium))
            throw new NotAPremiumVideoException();

        premium.addSubtitle(language, url);
    }

    @Override
    public PublishableVideos getVideo(String id) throws InvalidPublishableVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        // Episodes are not publishable videos — both null and episode cases throw
        // FIX: use Episode interface, not EpisodeClass
        if (video == null || video instanceof Episode)
            throw new InvalidPublishableVideoException();
        return (PublishableVideos) video;
    }

    @Override
    public PremiumVideos getPremiumVideo(String id) throws NotAPremiumVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        // FIX: use PremiumVideos interface, not PremiumVideosClass
        if (!(video instanceof PremiumVideos premiumVideo))
            throw new NotAPremiumVideoException();
        return premiumVideo;
    }

    @Override
    public void removeVideo(String id)
            throws VideoDoesNotExistException, CannotRemoveEpisodeException,
            CannotRemoveShowVideoException {
        VideoStructure video = videos.get(id.toLowerCase());
        if (video == null)
            throw new VideoDoesNotExistException();
        // FIX: use Episode interface, not EpisodeClass
        if (video instanceof Episode)
            throw new CannotRemoveEpisodeException();

        for (Show show : shows.values()) {
            if (show.getVideo().getId().equalsIgnoreCase(id))
                throw new CannotRemoveShowVideoException();
        }
        videos.remove(id.toLowerCase());
    }

    // -----------------------------------------------------------------------
    //  Podcast commands
    // -----------------------------------------------------------------------

    @Override
    public void createPodcast(String title, String author, String language)
            throws InvalidLanguageException, TitleAlreadyExistException {
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        if (podcasts.containsKey(title.toLowerCase()))
            throw new TitleAlreadyExistException();

        // FIX: register before getCanonical so first-seen casing is preserved
        if (!authorRegistry.exists(author))
            authorRegistry.register(author);
        String canonicalAuthor = authorRegistry.getCanonical(author);

        Podcast newPodcast = new PodcastClass(title, canonicalAuthor, language);
        podcasts.put(title.toLowerCase(), newPodcast);

        podcastsByAuthor
                .computeIfAbsent(canonicalAuthor.toLowerCase(), k -> new ArrayList<>())
                .add(newPodcast);
    }

    @Override
    public void addEpisode(String title, String id, int duration, String url, String releaseDate)
            throws InvalidDurationException, TitleDoesNotExistsException,
            IdAlreadyExistsException, InvalidDateException {
        if (duration <= 0)
            throw new InvalidDurationException();

        Podcast pod = podcasts.get(title.toLowerCase());
        if (pod == null)
            throw new TitleDoesNotExistsException();

        if (videos.containsKey(id.toLowerCase()))
            throw new IdAlreadyExistsException();

        Iterator<Episode> it = pod.getEpisodesIterator();
        if (it.hasNext()) {
            Episode latestEpisode = it.next();
            if (releaseDate.compareTo(latestEpisode.getReleaseDate()) < 0)
                throw new InvalidDateException();
        }

        Episode ep = new EpisodeClass(id, duration, url, releaseDate);
        pod.addEpisode(ep);
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
        // Kept for interface compliance; episode listing is driven by Main via getPodcast
        if (!podcasts.containsKey(title.toLowerCase()))
            throw new TitleDoesNotExistsException();
    }

    @Override
    public Iterator<Podcast> authorPodcasts(String authorName) {
        // Use getCanonical: if author unknown it returns the input, giving an empty list lookup
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

        Iterator<Episode> it = pod.getEpisodesIterator();
        while (it.hasNext())
            videos.remove(it.next().getId().toLowerCase());

        List<Podcast> authorList = podcastsByAuthor.get(pod.getAuthor().toLowerCase());
        if (authorList != null)
            authorList.remove(pod);

        if (!shows.containsKey(title.toLowerCase()))
            tagsByTitle.remove(title.toLowerCase());
    }

    // -----------------------------------------------------------------------
    //  Show commands
    // -----------------------------------------------------------------------

    @Override
    public void createShow(String author, String videoId, String date)
            throws VideoForShowDoesNotExistException, ShowAlreadyExistsException {
        VideoStructure videoStructure = videos.get(videoId.toLowerCase());

        if (!(videoStructure instanceof PublishableVideos publishable))
            throw new VideoForShowDoesNotExistException();

        String showTitle = publishable.getTitle();

        if (shows.containsKey(showTitle.toLowerCase()))
            throw new ShowAlreadyExistsException();

        // FIX: register before getCanonical so first-seen casing is preserved
        if (!authorRegistry.exists(author))
            authorRegistry.register(author);
        String canonicalAuthor = authorRegistry.getCanonical(author);

        Show newShow = new ShowClass(canonicalAuthor, publishable, date);
        shows.put(showTitle.toLowerCase(), newShow);

        showsByAuthor
                .computeIfAbsent(canonicalAuthor.toLowerCase(),
                        k -> new TreeSet<>(new ShowByDateTitleComparator()))
                .add(newShow);
    }

    @Override
    public Show getShow(String title) throws ShowDoesNotExistException {
        Show show = shows.get(title.toLowerCase());
        if (show == null)
            throw new ShowDoesNotExistException();
        return show;
    }

    @Override
    public Iterator<Show> authorShows(String authorName) {
        String canonicalAuthor = authorRegistry.getCanonical(authorName);
        SortedSet<Show> authorShowSet = showsByAuthor.get(canonicalAuthor.toLowerCase());
        if (authorShowSet == null || authorShowSet.isEmpty())
            return Collections.emptyIterator();
        return authorShowSet.iterator();
    }

    @Override
    public void removeShow(String title) throws ShowDoesNotExistException {
        Show show = shows.remove(title.toLowerCase());
        if (show == null)
            throw new ShowDoesNotExistException();

        SortedSet<Show> authorShowSet = showsByAuthor.get(show.getAuthor().toLowerCase());
        if (authorShowSet != null)
            authorShowSet.remove(show);

        if (!podcasts.containsKey(title.toLowerCase()))
            tagsByTitle.remove(title.toLowerCase());
    }

    // -----------------------------------------------------------------------
    //  Author productivity
    // -----------------------------------------------------------------------

    @Override
    public Iterator<Map.Entry<String, Integer>> authorsProductivity() {
        Map<String, Integer> productivity = new HashMap<>();

        // Count podcasts per author — only if they have at least one
        for (Map.Entry<String, List<Podcast>> entry : podcastsByAuthor.entrySet())
            if (!entry.getValue().isEmpty())
                productivity.merge(entry.getKey(), entry.getValue().size(), Integer::sum);

        // Count shows per author — only if they have at least one
        for (Map.Entry<String, SortedSet<Show>> entry : showsByAuthor.entrySet())
            if (!entry.getValue().isEmpty())
                productivity.merge(entry.getKey(), entry.getValue().size(), Integer::sum);

        // No authors with content — return empty iterator
        if (productivity.isEmpty())
            return Collections.emptyIterator();

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(productivity.entrySet());
        sorted.sort((a, b) -> {
            // Primary: descending by contribution count
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            // Secondary: alphabetical by canonical author name
            return authorRegistry.getCanonical(a.getKey())
                    .compareToIgnoreCase(authorRegistry.getCanonical(b.getKey()));
        });

        return sorted.iterator();
    }

    // -----------------------------------------------------------------------
    //  Tag commands
    // -----------------------------------------------------------------------

    @Override
    public void addTag(String title, String tag)
            throws TitleDoesNotExistsException, TaggedException {
        boolean hasPodcast = podcasts.containsKey(title.toLowerCase());
        boolean hasShow    = shows.containsKey(title.toLowerCase());

        if (!hasPodcast && !hasShow)
            throw new TitleDoesNotExistsException();

        SortedSet<String> tags = tagsByTitle.computeIfAbsent(
                title.toLowerCase(), k -> new TreeSet<>());

        for (String existing : tags) {
            if (existing.equalsIgnoreCase(tag))
                throw new TaggedException();
        }
        tags.add(tag);
    }

    @Override
    public void removeTag(String title, String tag)
            throws TitleDoesNotExistsException, TagNotPresentException {
        boolean hasPodcast = podcasts.containsKey(title.toLowerCase());
        boolean hasShow    = shows.containsKey(title.toLowerCase());

        if (!hasPodcast && !hasShow)
            throw new TitleDoesNotExistsException();

        SortedSet<String> tags = tagsByTitle.get(title.toLowerCase());

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
            throw new TagNotPresentException();

        tags.remove(toRemove);
        if (tags.isEmpty())
            tagsByTitle.remove(title.toLowerCase());
    }

    @Override
    public Iterator<TaggedContent> tagged(String tag, String content, String order)
            throws InvalidTagParametersException, NoContentTaggedException {
        boolean validContent = content.equalsIgnoreCase("SHOW")
                || content.equalsIgnoreCase("PODCAST")
                || content.equalsIgnoreCase("ALL");
        boolean validOrder = order.equalsIgnoreCase("ASC")
                || order.equalsIgnoreCase("DES");

        if (!validContent || !validOrder)
            throw new InvalidTagParametersException();

        List<TaggedContent> result = new ArrayList<>();

        if (content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("SHOW")) {
            for (Show show : shows.values()) {
                if (hasTag(show.getTitle(), tag))
                    result.add(show);
            }
        }
        if (content.equalsIgnoreCase("ALL") || content.equalsIgnoreCase("PODCAST")) {
            for (Podcast pod : podcasts.values()) {
                if (hasTag(pod.getTitle(), tag))
                    result.add(pod);
            }
        }

        if (result.isEmpty())
            throw new NoContentTaggedException();

        result.sort(new ComparatorClass(order));
        return result.iterator();
    }

    // -----------------------------------------------------------------------
    //  Auxiliary / helper methods
    // -----------------------------------------------------------------------

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