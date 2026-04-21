package youVideo;
/*
 A class which extends VideoStructure

 */
public class Episode extends VideoStructure {
    private String releaseDate;

    public Episode(String id, int duration, String url, String releaseDate){
        super(id, duration, url);
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate(){
        return releaseDate;
    }
}
