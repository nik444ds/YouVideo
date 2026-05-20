package youVideo;

import youVideo.Exceptions.*;

import java.util.*;

public class VideoNetworkClass implements VideoNetwork{
    private final Map<String, VideoStructure> videos;
    private final List<PodcastClass> podcasts;
    private final List<ShowClass> shows;
    private final IdentityRegistry authorRegistry = new IdentityRegistry();
    private final IdentityRegistry titleRegistry = new IdentityRegistry();

    public VideoNetworkClass(){
        this.videos = new HashMap<>();
        this.podcasts = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.authorRegistry = new IdentityRegistry();
        this.titleRegistry = new IdentityRegistry();

    }


    @Override
    public void createPublishable(String id, int duration, String url, String publisher, String title, String language)
    throws InvalidLanguageException, InvalidDurationException, IdAlreadyExistsException {
        // 1.  Language
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();

        // 2. Duration
        if (duration <= 0)
            throw new InvalidDurationException();

        // 3.  ID (using map)
        if (videos.containsKey(id))
            throw new IdAlreadyExistsException();
    VideoStructure newVideo = new PublishableVideosClass(id, duration,url,publisher,title,language);

    videos.put(id, newVideo);
    }

    @Override
    public void createPremium(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl)
    throws InvalidLanguageException, InvalidLanguageSubtitleException, InvalidDurationException, IdAlreadyExistsException{
        // 1.  Language
        if (!isLanguageValid(language))
            throw new InvalidLanguageException();

        //2. Init language
        if(!isLanguageValid(subLanguage)){
            throw new InvalidLanguageSubtitleException();
        }
        // 3. Duration
        if (duration <= 0)
            throw new InvalidDurationException();

        // 4.  ID (using map)
        if (videos.containsKey(id))
            throw new IdAlreadyExistsException();

        VideoStructure newVideo = new PremiumVideosClass(id,duration,url,publisher,title,language,subLanguage,subUrl);

        videos.put(id, newVideo);
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
    public void createPodcast(String title, String author, String language)throws
            InvalidLanguageException, TitleAlreadyExist {
        if (!isLanguageValid(language)){
            throw new InvalidLanguageException();
        }
        if(){
            throw new TitleAlreadyExist();
        }
        String canonicalName = getNameAuthor(author);
        if(authorMap.containsKey(author.toLowerCase())){
            authorMap.put(author.toLowerCase(),author);
        }

    PodcastClass newPodcast = new PodcastClass(title,canonicalName,language);
    podcasts.add(newPodcast);
    }

    @Override
    public void addEpisode(String id, int duration, String url, String releaseDate) {

    }

    @Override
    public void getPodcast(String title) {

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
    public void addTag(String title) {

    }

    @Override
    public void removeTag(String title) {

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
    public String getNameAuthor(String authorInput){
        return authorMap.getOrDefault(authorInput.toLowerCase(),authorInput);
    }
    /**
     * Checks if a podcast title is already in use within the system
     * @param title the title to verify
     * @param podcast the global list of podcasts
     * @return true if the title already exists (case-insensitive), false otherwise
     * @pre title != null && podcast != null
     */
    private  boolean titleAlreadyExist(String title){
       for(PodcastClass p:podcasts){

       }
        return false;
    }
}
