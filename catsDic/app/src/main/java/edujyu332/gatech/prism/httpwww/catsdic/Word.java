package edujyu332.gatech.prism.httpwww.catsdic;

/**
 * Created by Jinming on 6/6/2015.
 */
public class Word {
    String content, selected;
    public Word(String selected) {
        this.selected = selected;
        content = getContent(selected);
    }
    public String getContent(String t) {
        t = t.replace("'s","");
        t = t.replace("'d","");
        t = t.replace("~"," ");
        t = t.replace("_"," ");
        t = t.replace(".", "");
        t = t.replace(",","");
        t = t.replace(";","");
        t = t.replace("!", "");
        t = t.replace("?","");
        t = t.replace(",", "");
        t = t.replace("_","");
        return t;
    }
}
