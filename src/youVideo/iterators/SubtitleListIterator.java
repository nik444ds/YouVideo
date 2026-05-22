package youVideo.iterators;
import youVideo.Subtitles;


import java.util.Iterator;
import java.util.List;

public class SubtitleListIterator implements Iterator<Subtitles> {
    private final Iterator<Subtitles> it;
    public SubtitleListIterator (List<Subtitles> subtitles){
        this.it = subtitles.iterator();
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public Subtitles next() {
        return it.next();
    }


}
