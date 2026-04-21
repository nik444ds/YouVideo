package youVideo;
/*
 A class which extends VideoStructure

 */
public class Episode extends VideoStructure {
    String releaseDate;
    String title;

    public Episode(String id, String title, int duration, String url, String releaseDate){
        super(id,duration,url);
        this.releaseDate = releaseDate;
        this.title = title;
    }

}
