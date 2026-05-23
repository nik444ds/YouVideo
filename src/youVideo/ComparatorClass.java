package youVideo;
import java.util.Comparator;
public class ComparatorClass implements Comparator {
    private final boolean isAscending;


    public ComparatorClass(String order) {
        //if the order is ascendent is true, otherwise, false
        this.isAscending = order.equalsIgnoreCase("ASC");
    }

    @Override
    public int compare(TaggedContent c1, TaggedContent c2) {

        // 1º Critério: Comparar os títulos ignorando maiúsculas/minúsculas
        int titleCompare = c1.getTitle().compareToIgnoreCase(c2.getTitle());

        // Se os títulos forem diferentes, aplicamos a ordem (ASC ou DES)
        if (titleCompare != 0) {
            // Se for ASC devolvemos o valor normal, se for DES invertemos o sinal
            return isAscending ? titleCompare : -titleCompare;
        }

        // 2º Critério: Equal titles
        // The Show comes first
        boolean c1IsShow = c1 instanceof Show;
        boolean c2IsShow = c2 instanceof Show;

        if (c1IsShow && !c2IsShow) {
            return -1; // c1 (Show) sobe na lista
        } else if (!c1IsShow && c2IsShow) {
            return 1;  // c2 (Show) sobe na lista
        }

        // 3º Critério: São os dois Shows ou os dois Podcasts com o mesmo nome
        return 0;
    }
}
}
