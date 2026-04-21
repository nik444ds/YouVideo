package youVideo;

public class Shows {
    private String author;
    private PublishableVideos video;
    private String date;

    public Shows(String author, PublishableVideos video, String date) {
        this.author = author;
        this.video = video;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public PublishableVideos getvideo() {
        return video;
    }

    public String getDate() {
        return date;
    }
    public String getTitle(){
        return video.getTitle();
    }
}