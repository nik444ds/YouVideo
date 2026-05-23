package youVideo;

import java.util.HashMap;
import java.util.Map;

/**
 * This interface defines the contract for any identity registration system (whether for authors, titles, or tags).
 */
public class IdentityRegistryClass implements IdentityRegistry {
    // Map the version on lowerCase to Original Version
    private final Map<String, String> registry;

    public IdentityRegistryClass() {
        this.registry = new HashMap<>();
    }

    @Override
    public void register(String identity) {
        if (identity != null && !exists(identity)) {
            registry.put(identity.toLowerCase(), identity);
        }
    }

    @Override
    public boolean exists(String identity) {
        if (identity == null) {
            return false;
        }
        return registry.containsKey(identity.toLowerCase());
    }

    @Override
    public String getCanonical(String identity) {
        if (identity == null) {
            return null;
        }
        // If exists, return the original stored; otherwise, return the input
        return registry.getOrDefault(identity.toLowerCase(), identity);
    }

    @Override
    public void remove(String identity) {
        if (identity != null) {
            registry.remove(identity.toLowerCase());
        }
    }
}