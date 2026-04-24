package youVideo;
/*
 A class which extends VideoStructure

 */
public class Episode extends VideoStructure {
    private final String releaseDate;

    public Episode(String id, int duration, String url, String releaseDate){
        super(id, duration, url);
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate(){
        return releaseDate;
    }
    @Override
    public void display() {
        System.out.println("Episode " + getId() + ": " + getDuration() + " min Date: " + getReleaseDate());
        System.out.println("URL: " + getUrl());
    }
}
