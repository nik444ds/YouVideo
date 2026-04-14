package youVideo;

import dataStructures.*;
/*
A class which extends the PublishableVideos and have a subtitle implementation
 */
public class PremiumVideos extends PublishableVideos {

    private Array<Subtitles> subtitles = new ArrayClass<>();
    //Constructor
    public PremiumVideos(String id, int duration, String url, String publisher, String title, String language, String subLanguage, String subUrl ){
        super(id, duration, url, publisher, title,language);
        Subtitles subtitle = new Subtitles(subLanguage, subUrl);
        subtitles.insertLast(subtitle);
    }
    //@return subtitles from the library of subtitles
    public Array<Subtitles> getSubtitles(){
        return subtitles;
    }

    /*
    @param language get the language of subtitle
    @param url get the url from subtitle
     */
    public void addSubtitle(String language, String url){
        subtitles.insertLast(new Subtitles(language, url));
    }



}