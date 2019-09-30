package android.app.java.com.duovoc.framework.model.adapter;

import android.app.java.com.duovoc.framework.model.helper.DatabaseOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {

    private static final String TAG = DatabaseAdapter.class.getSimpleName();

    private final Context context;
    private DatabaseOpenHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        this.context = context;
    }

    public void open() {
        this.databaseHelper = new DatabaseOpenHelper(this.context);
        this.setDatabase(this.databaseHelper.getWritableDatabase());
    }

    public void close(){
        if (this.databaseHelper != null) {
            this.databaseHelper.close();
        }
    }

    public void beginTransaction() {
        this.database.beginTransaction();
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }

    public void endTransaction() {
        this.database.endTransaction();
    }

    private void setDatabase(SQLiteDatabase database) {
        this.database = database;
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }
}
