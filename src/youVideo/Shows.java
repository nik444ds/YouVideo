package youVideo;

public class Shows implements  Titled, Authored {
    private String author;
    private PublishableVideos video;
    private String transmissionDate;

    public Shows(String author, PublishableVideos video, String date) {
        this.author = author;
        this.video = video;
        this.transmissionDate = date;
    }

    public String getAuthor() {
        return author;
    }

    public PublishableVideos getvideo() {
        return video;
    }

    public String getTransmissionDate() {
        return transmissionDate;
    }
    public String getTitle(){
        return video.getTitle();
    }
}