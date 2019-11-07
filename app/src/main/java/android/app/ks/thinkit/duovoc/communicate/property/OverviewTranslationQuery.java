package android.app.ks.thinkit.duovoc.communicate.property;

import android.app.ks.thinkit.duovoc.framework.communicate.property.IRequestQuery;

public enum OverviewTranslationQuery implements IRequestQuery {
    Sentence(Key.sentence),
    Format(Key.format);

    private Key key;

    OverviewTranslationQuery(Key key) {
        this.key = key;
    }

    @Override
    public String getQueryName() {
        return this.key.name();
    }

    private enum Key {
        sentence,
        format,
    }
}
