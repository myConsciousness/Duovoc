package android.app.java.com.duovoc.framework.model.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseOpenHelper.class.getCanonicalName();
    private static final String DATABASE_NAME = "duovoc_debug.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        Log.d(TAG, "テーブルの初期化処理を開始しました。");

        this.createTable(database);
        this.initializeTable(database);

        Log.d(TAG, "テーブルの初期化処理を終了しました。");
    }

    @Override
    public void onUpgrade(
            final SQLiteDatabase database,
            final int oldVersion,
            final int newVersion) {

        Log.d(TAG, "onUpgrade version : " + database.getVersion());
        Log.d(TAG, "onUpgrade oldVersion : " + oldVersion);
        Log.d(TAG, "onUpgrade newVersion : " + newVersion);

        Log.d(TAG, "テーブルの初期化処理を開始しました。");

        this.createTable(database);
        this.initializeTable(database);

        Log.d(TAG, "テーブルの初期化処理を終了しました。");
    }

    private void createTable(SQLiteDatabase database) {

        this.execSQL(database, "create");
    }

    private void initializeTable(SQLiteDatabase database) {

        this.execSQL(database, "insert");
    }

    private void execSQL(SQLiteDatabase database, final String fileName) {

        final AssetManager assetManager = this.context.getResources().getAssets();

        try {
            final String[] files = assetManager.list(fileName);

            int fileAmounts = files.length;
            for (int i = 0; i < fileAmounts; i++) {

                final String str = this.readFile(assetManager.open(fileName + "/" + files[i]));
                final String[] sqlList = str.split("/");

                for (String sql : sqlList) {
                    database.execSQL(sql);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ファイルから文字列を読み込みます。
     *
     * @param is
     * @return ファイルの文字列
     * @throws IOException
     */
    private String readFile(InputStream is) throws IOException {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {

            br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return sb.toString();
    }
}
