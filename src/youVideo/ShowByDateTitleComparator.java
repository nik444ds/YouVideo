package youVideo;

import java.util.Comparator;

/**
 * Comparator for Show objects.
 * Orders shows by ascending transmission date.
 * When dates are equal, orders alphabetically by show title.
 */
public class ShowByDateTitleComparator implements Comparator<Show> {

    @Override
    public int compare(Show s1, Show s2) {
        // Primary criterion: ascending transmission date
        int dateCmp = s1.getTransmissionDate().compareTo(s2.getTransmissionDate());
        if (dateCmp != 0)
            return dateCmp;

        // Secondary criterion: alphabetical order by title (case-insensitive)
        return s1.getTitle().compareToIgnoreCase(s2.getTitle());
    }
}