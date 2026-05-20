package youVideo;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of the IdentityRegistry using a HashMap.
 */
public class IdentityRegistryClass implements IdentityRegistry {
    // Mapeia a versão lowercase para a versão com a capitalização original
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
        // Se existir, devolve o formato original guardado; caso contrário, devolve o input
        return registry.getOrDefault(identity.toLowerCase(), identity);
    }
}