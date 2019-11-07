package android.app.ks.thinkit.duovoc.framework.model.holder;

import java.util.Arrays;
import java.util.Objects;

public final class SelectHolder extends QueryHolder {

    private String[] columns = null;
    private String selection = null;
    private String[] selectionArgs = null;
    private String groupBy = null;
    private String having = null;
    private String orderBy = null;
    private String limit = null;

    public String[] getColumns() {
        return this.columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String getSelection() {
        return this.selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String[] getSelectionArgs() {
        return this.selectionArgs;
    }

    public void setSelectionArgs(String[] selectionArgs) {
        this.selectionArgs = selectionArgs;
    }

    public String getGroupBy() {
        return this.groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getHaving() {
        return this.having;
    }

    public void setHaving(String having) {
        this.having = having;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getLimit() {
        return this.limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    @Override
    public void clearAll() {
        this.setColumns(null);
        this.setSelection(null);
        this.setSelectionArgs(null);
        this.setGroupBy(null);
        this.setHaving(null);
        this.setOrderBy(null);
        this.setLimit(null);
    }

    @Override
    public String toString() {
        return "SelectHolder{" +
                "columns=" + Arrays.toString(this.columns) +
                ", selection='" + this.selection + '\'' +
                ", selectionArgs=" + Arrays.toString(this.selectionArgs) +
                ", groupBy='" + this.groupBy + '\'' +
                ", having='" + this.having + '\'' +
                ", orderBy='" + this.orderBy + '\'' +
                ", limit='" + this.limit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SelectHolder that = (SelectHolder) o;
        return Arrays.equals(this.getColumns(), that.getColumns()) &&
                Objects.equals(this.getSelection(), that.getSelection()) &&
                Arrays.equals(this.getSelectionArgs(), that.getSelectionArgs()) &&
                Objects.equals(this.getGroupBy(), that.getGroupBy()) &&
                Objects.equals(this.getHaving(), that.getHaving()) &&
                Objects.equals(this.getOrderBy(), that.getOrderBy()) &&
                Objects.equals(this.getLimit(), that.getLimit());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getSelection(), this.getGroupBy(), this.getHaving(), this.getOrderBy(), this.getLimit());
        result = 31 * result + Arrays.hashCode(this.getColumns());
        result = 31 * result + Arrays.hashCode(this.getSelectionArgs());
        return result;
    }
}
