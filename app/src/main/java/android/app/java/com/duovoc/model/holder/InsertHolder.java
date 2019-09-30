package android.app.java.com.duovoc.model.holder;

import android.content.ContentValues;

final public class InsertHolder extends QueryHolder {

    private String nullColumnHack = null;
    private final ContentValues cv = new ContentValues();

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
