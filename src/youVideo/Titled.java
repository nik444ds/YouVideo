package youVideo;

/**
 * Interface defining a contract for objects that possess a title.
 * Used to provide a consistent way to retrieve titles across different media types.
 */
public interface Titled {
    /**
     * Returns the title of the object.
     * @return a String representing the title
     */
    String getTitle();
}