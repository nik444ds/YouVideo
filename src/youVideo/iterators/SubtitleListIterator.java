package youVideo.iterators;
import youVideo.SubtitlesClass;

import java.util.Iterator;
import java.util.List;

public class SubtitleListIterator implements Iterator<SubtitlesClass> {
    private final Iterator<SubtitlesClass> it;
    public SubtitleListIterator (List<SubtitlesClass> subtitles){
        this.it = subtitles.iterator();
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public SubtitlesClass next() {
        return it.next();
    }


}
