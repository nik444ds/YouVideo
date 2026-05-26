package youVideo;
import java.util.Comparator;
public class ComparatorClass implements Comparator<TaggedContent> {
    private final boolean isAscending;


    public ComparatorClass(String order) {
        //if the order is ascendent is true, otherwise, false
        this.isAscending = order.equalsIgnoreCase("ASC");
    }

    @Override
    public int compare(TaggedContent c1, TaggedContent c2) {

        //  Compare titles  ignore case
        int titleCompare = c1.getTitle().compareToIgnoreCase(c2.getTitle());

        // if titles are different, we apply (ASC ou DES)
        if (titleCompare != 0) {
            // if is ASC return normal value, if DES invert the sign
            return isAscending ? titleCompare : -titleCompare;
        }

        // If Equal titles
        // The Show comes first
        boolean c1IsShow = c1 instanceof Show;
        boolean c2IsShow = c2 instanceof Show;

        if (c1IsShow && !c2IsShow) {
            return -1; // c1 (Show) go to the top of list
        } else if (!c1IsShow && c2IsShow) {
            return 1;  // c2 (Show) go to the top of list
        }

        // 3º: Both Shows or Both Podcasts with the same name
        return 0;
    }
}

