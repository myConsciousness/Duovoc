package android.app.java.com.duovoc.framework.model.holder;

import android.content.ContentValues;

final public class InsertHolder extends QueryHolder {

    private final ContentValues cv = new ContentValues();
    private String nullColumnHack = null;

    public String getNullColumnHack() {
        return this.nullColumnHack;
    }

    public void setNullColumnHack(String nullColumnHack) {
        this.nullColumnHack = nullColumnHack;
    }

    public ContentValues getContentValues() {
        return this.cv;
    }

    @Override
    public void clearAll() {
        this.setNullColumnHack(null);
        this.getContentValues().clear();
    }
}
