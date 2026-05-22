package youVideo;

/**
 * Interface representing a registry for unique identities (e.g., authors, titles).
 * Ensures case-insensitive lookups while preserving the original canonical formatting.
 */
public interface IdentityRegistry {

    /**
     * Registers a new identity in the system if it does not already exist.
     * @param identity the name or title to register
     */
    void register(String identity);

    /**
     * Checks if an identity already exists in the registry (case-insensitive).
     * @param identity the identity to check
     * @return true if it exists, false otherwise
     */
    boolean exists(String identity);

    /**
     * Retrieves the original, correctly capitalized (canonical) version of a registered identity.
     * @param identity the lookup string (case-insensitive)
     * @return the original registered String, or the input string if not found
     */
    String getCanonical(String identity);

}