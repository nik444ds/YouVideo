package youVideo;

import youVideo.Exceptions.*;
import java.util.*;

public class VideoNetworkClass implements VideoNetwork {
    private final Map<String, VideoStructure> videos;
    private final List<Podcast> podcasts;
    private final List<Show> shows;
    private final Map<String, SortedSet<String>> tagsByTitle;
    private final IdentityRegistry authorRegistry;
    private final IdentityRegistry titleRegistry;

    public VideoNetworkClass() {
        this.videos = new HashMap<>();
        this.podcasts = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.tagsByTitle = new HashMap<>();
        this.authorRegistry = new IdentityRegistryClass();
        this.titleRegistry = new IdentityRegistryClass();
    }


    @Override
    public void createPublishable(String id, int duration, String url, String publisher, String title, String language)
            throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException {

        if (!isLanguageValid(language))
            throw new InvalidLanguageException();

        if (duration <= 0)
            throw new InvalidDurationException();

        if (videos.containsKey(id))
            throw new IdAlreadyExistsException();
        titleRegistry.register(title);
        VideoStructure newVideo = new PublishableVideosClass(id, duration, url, publisher, title, language);
        videos.put(id, newVideo);
    }

    @Override
    public void createPremium(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl)
            throws InvalidLanguageException,InvalidLanguageSubtitleException, InvalidDurationException, IdAlreadyExistsException {

        if (!isLanguageValid(language))
            throw new InvalidLanguageException();
        if(!isLanguageValid(subLanguage)){
            throw new InvalidLanguageSubtitleException();
        }
        if (duration <= 0)
            throw new InvalidDurationException();

        if (videos.containsKey(id))
            throw new IdAlreadyExistsException();
        //register the title
        titleRegistry.register(title);
        VideoStructure newPremium = new PremiumVideosClass(id, duration, url, publisher, title, language, subLanguage, subUrl);
        videos.put(id, newPremium);
    }

    @Override
    public void createSubtitle(String id,String url , String language)
            throws InvalidLanguageSubtitleException, VideoDoesNotExistException, NotAPremiumVideoException{

        if(!isLanguageValid(language)){
            throw new InvalidLanguageSubtitleException();
        }

        VideoStructure video = videos.get(id);

        if(video == null){
            throw new VideoDoesNotExistException();

        }

        if(!(video instanceof PremiumVideosClass premium)){
            throw new NotAPremiumVideoException();
        }
        premium.addSubtitle(language,url);
    }

    @Override
    public void getVideo(String id) throws InvalidPublishableVideoException{
        VideoStructure video = videos.get(id);
        if(video == null || video instanceof EpisodeClass) {
            throw new InvalidPublishableVideoException();
        }
        video.display();
    }

    @Override
    public PremiumVideosClass getPremiumVideo(String id) throws NotAPremiumVideoException {
        VideoStructure video = videos.get(id);

        if (!(video instanceof PremiumVideosClass premiumVideo)) {
            throw new NotAPremiumVideoException();
        }
        return premiumVideo;
    }

    @Override
    public void createPodcast(String title, String author, String language)
            throws InvalidLanguageException, TitleAlreadyExistException {
        if (!isLanguageValid(language)){
            throw new InvalidLanguageException();
        }
        if (titleAlreadyExist(title)) {
            throw new TitleAlreadyExistException();
        }
        String canonicalAuthor = authorRegistry.getCanonical(author);
        if (!authorRegistry.exists(author)) {
            authorRegistry.register(author);

        }
        titleRegistry.register(title);
        Podcast newPodcast = new PodcastClass(title, canonicalAuthor, language);
        podcasts.add(newPodcast);
    }

    @Override
    public void addEpisode(String title,String id, int duration, String url, String releaseDate)
    throws InvalidDurationException,TitleDoesNotExistsException,IdAlreadyExistsException, InvalidDateException{
        Podcast pod = getPodcast(title);
        if(pod == null){
            throw new TitleDoesNotExistsException();
        }
        if(duration <= 0){
            throw new InvalidDurationException();
        }
        //The id is global, for videos and Episodes
        if(videos.containsKey(id)){
            throw new IdAlreadyExistsException();
        }
        Iterator<Episode> it = pod.getEpisodesIterator();
        if(it.hasNext()){
            Episode latestEpisode = it.next();
            if(releaseDate.compareTo(latestEpisode.getReleaseDate()) < 0){
                throw new InvalidDateException();
            }
        }

        Episode ep = new EpisodeClass(id,duration,url,releaseDate);
        pod.addEpisode(ep);
    }
    @Override
    public void podcastData(String title) throws TitleDoesNotExistsException{
        if(!titleRegistry.exists(title)){
            throw new TitleDoesNotExistsException();
        }

    }



    @Override
    public void listEpisodes(String title) {

    }

    @Override
    public void authorPodcasts(String authorName) {

    }

    @Override
    public void removePodcast(String title) {

    }

    @Override
    public void createShow(String author, PublishableVideosClass video, String date) {

    }

    @Override
    public void getShow(String title) {

    }

    @Override
    public void authorShows(String authorShows) {

    }

    @Override
    public void removeShow(String title) {

    }

    @Override
    public void removeVideo(String id) {

    }

    @Override
    public void authorsProductivity() {

    }

    @Override
    public void addTag(String title, String tag) throws TitleDoesNotExistsException,TaggedException {
        if(!titleRegistry.exists(title)){
            throw new TitleDoesNotExistsException();
        }
        String keyMap = title.toLowerCase();
        SortedSet<String> tags = tagsByTitle.get(keyMap);

        if(tags == null)
        {
            tags = new TreeSet<>();
            tagsByTitle.put(keyMap,tags);

        }
        if(!tags.contains(tag)){
            throw new TaggedException();
        }
        tags.add(tag);


    }

    @Override
    public void removeTag(String title,String tag) throws TitleDoesNotExistsException,TaggedException{
        if(!titleRegistry.exists(title)){
            throw new TitleDoesNotExistsException();
        }
        String keyMap = title.toLowerCase();
        SortedSet<String> tags = tagsByTitle.get(keyMap);
// If 'tags' is null, the title has no tags at all.
        if(tags == null || !tags.contains(tag)){
            throw new TaggedException();
        }
        tags.remove(tag);
        //Clean up empty sets from the map
        if(tags.isEmpty()){
            tagsByTitle.remove(keyMap);
        }
    }

    @Override
    public void tagged() {

    }

    /*-------------------------------------
                AUXILIARY METHODS
    ----------------------------------------*/
    @Override
    public boolean isLanguageValid(String code) {
        String[] languages = Locale.getISOLanguages();
        for (String lang : languages) {
            if (lang.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String getNameAuthor(String authorInput) {
        return authorRegistry.getCanonical(authorInput);
    }
    @Override
    public  boolean titleAlreadyExist(String title) {
        return titleRegistry.exists(title);
    }
    @Override
    public Podcast getPodcast(String title) {
        for(Podcast p: podcasts){
            if(p.getTitle().equalsIgnoreCase(title));
            return p;
        }
        return null;
    }
}
