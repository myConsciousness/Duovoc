package android.app.java.com.duovoc.framework.model.holder;

final public class SelectHolder extends QueryHolder {

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
}
