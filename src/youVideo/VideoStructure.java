package youVideo;
/*
An abstract class which implements: ID, Duration and URL.
 */
public abstract class VideoStructure {
   private final String id;
    private final int duration;
    private final String url;

    public VideoStructure(String id, int duration, String url ){
        this.id = id;
        this.duration = duration;
        this.url = url;
    }

    public String getId(){
        return id;
    }
    public int getDuration(){
       return duration;
    }
    public String getUrl(){
         return url;
    }

}
