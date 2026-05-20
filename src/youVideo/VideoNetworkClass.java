package youVideo;

import youVideo.Exceptions.*;

import java.util.*;

public class VideoNetworkClass implements VideoNetwork{
    private  Map<String, VideoStructure> videos;
    private  List<Podcasts> podcasts;
    private List<Shows> shows;

    public VideoNetworkClass(){
        this.videos = new HashMap<>();
        this.podcasts = new ArrayList<>();
        this.shows = new ArrayList<>();

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
    VideoStructure newVideo = new PublishableVideos(id, duration,url,publisher,title,language);

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

        VideoStructure newVideo = new PremiumVideos(id,duration,url,publisher,title,language,subLanguage,subUrl);

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

        if(!(video instanceof PremiumVideos premium)){
            throw new NotAPremiumVideoException();
        }
        premium.addSubtitle(language,url);
    }

    @Override
    public void getVideo(String id) throws InvalidPublishableVideoException{
        VideoStructure video = videos.get(id);
        if(video == null || video instanceof Episode) {
            throw new InvalidPublishableVideoException();
        }
        video.display();
    }

    @Override
    public PremiumVideos getPremiumVideo(String id) throws NotAPremiumVideoException {
        VideoStructure video = videos.get(id);

        if (!(video instanceof PremiumVideos premiumVideo)) {
            throw new NotAPremiumVideoException();
        }
        return premiumVideo;
    }

    @Override
    public void createPodcast(String title, String author, String language)throws
            InvalidLanguageException, PodcastAlreadyExistsException {
        if (!isLanguageValid(language)){
            throw new InvalidLanguageException();
    }
    for (Podcasts p : podcasts){
        if(p.getTitle().equalsIgnoreCase(title)){
            throw new PodcastAlreadyExistsException();
        }
    }

    Podcasts newPodcast = new Podcasts(title,author,language);
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
    public void createShow(String author, PublishableVideos video, String date) {

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
}
