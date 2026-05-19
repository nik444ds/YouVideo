package youVideo;

import youVideo.Exceptions.IdAlreadyExistsException;
import youVideo.Exceptions.InvalidDurationException;
import youVideo.Exceptions.InvalidLanguageException;

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
    public void createPremium(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl) {
        //verify if the id already exists
        if(videos.containsKey(id))
            return;
        VideoStructure newVideo = new PremiumVideos(id,duration,url,publisher,title,language,subLanguage,subUrl);

        videos.put(id, newVideo);
    }

    @Override
    public void createSubtitle(String id,String language, String url) {
        VideoStructure video = videos.get(id);

        if(video instanceof PremiumVideos premiumVideos){
            premiumVideos.addSubtitle(language, url);
        }

    }

    @Override
    public void getVideo(String id) {
        VideoStructure video = videos.get(id);
        if(video == null){
            throw new IllegalArgumentException("Publishable video " + id + " does not exist.");
        }
        if(video instanceof Episode ){
            throw new IllegalArgumentException("Publishable video " + id + " does not exist.");
        }
        video.display();
    }

    @Override
    public void subList(String id) {

    }

    @Override
    public void createPodcast(String title, String author, String language) {

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
