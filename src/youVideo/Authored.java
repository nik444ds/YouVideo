package youVideo;

/**
 * Interface defining a contract for objects that have an identifiable author or creator.
 */
public interface Authored {
    /**
     * Returns the name of the author or creator.
     * @return a String representing the author
     */
    String getAuthor();
}