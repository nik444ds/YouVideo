package youVideo;

public class Shows implements  Titled, Authored {
    private final String author;
    private final PublishableVideos video;
    private final String transmissionDate;

    public Shows(String author, PublishableVideos video, String date) {
        this.author = author;
        this.video = video;
        this.transmissionDate = date;
    }
    @Override
    public String getAuthor() {
        return author;
    }

    public PublishableVideos getvideo() {
        return video;
    }

    public String getTransmissionDate() {
        return transmissionDate;
    }
    @Override
    public String getTitle(){
        return this.video.getTitle();
    }
}