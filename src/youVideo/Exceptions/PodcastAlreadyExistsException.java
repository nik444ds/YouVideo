package youVideo.Exceptions;

public class PodcastAlreadyExistsException extends Exception{
    public PodcastAlreadyExistsException() {
        super("Podcast with this title already exists.");

    }
}
