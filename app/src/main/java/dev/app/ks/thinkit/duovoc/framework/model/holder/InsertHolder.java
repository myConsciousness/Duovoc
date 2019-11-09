package dev.app.ks.thinkit.duovoc.framework.model.holder;

import android.content.ContentValues;

import java.util.Objects;

public final class InsertHolder extends QueryHolder {

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

    @Override
    public String toString() {
        return "InsertHolder{" +
                "cv=" + this.cv +
                ", nullColumnHack='" + this.nullColumnHack + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        InsertHolder that = (InsertHolder) o;
        return this.cv.equals(that.cv) &&
                Objects.equals(this.getNullColumnHack(), that.getNullColumnHack());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.cv, this.getNullColumnHack());
    }
}
