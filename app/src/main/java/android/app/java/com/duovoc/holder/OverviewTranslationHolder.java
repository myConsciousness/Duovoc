package android.app.java.com.duovoc.holder;

import java.util.ArrayList;
import java.util.List;

final public class OverviewTranslationHolder {

    /**
     * overview情報に紐付く識別番号を格納する。
     */
    private String id = "";

    /**
     * ヒントリストを格納する。
     */
    private List<String> hints = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }
}
