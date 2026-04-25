package youVideo;

import java.util.Locale;

/**
 * Interface defining a contract for objects associated with a specific language.
 * Ensures the use of Locale objects for standardized language handling.
 */
public interface Languaged {
    /**
     * Returns the language associated with the object.
     * @return a Locale object representing the language
     */
    Locale getLanguage();
}