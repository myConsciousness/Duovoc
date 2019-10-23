package android.app.java.com.duovoc.framework.model.holder;

import java.util.Arrays;
import java.util.Objects;

public final class DeleteHolder extends QueryHolder {

    private String whereClause = null;
    private String[] whereArgs = null;

    public String getWhereClause() {
        return this.whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public String[] getWhereArgs() {
        return this.whereArgs;
    }

    public void setWhereArgs(String[] whereArgs) {
        this.whereArgs = whereArgs;
    }

    @Override
    public void clearAll() {
        this.setWhereClause(null);
        this.setWhereArgs(null);
    }

    @Override
    public String toString() {
        return "DeleteHolder{" +
                "whereClause='" + this.whereClause + '\'' +
                ", whereArgs=" + Arrays.toString(this.whereArgs) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        DeleteHolder that = (DeleteHolder) o;
        return Objects.equals(this.getWhereClause(), that.getWhereClause()) &&
                Arrays.equals(this.getWhereArgs(), that.getWhereArgs());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getWhereClause());
        result = 31 * result + Arrays.hashCode(this.getWhereArgs());
        return result;
    }
}
