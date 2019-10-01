package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.ModelBase;
import android.app.java.com.duovoc.holder.OverviewTranslationHolder;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

final public class OverviewTranslationInformation extends ModelBase {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = OverviewTranslationInformation.class.getSimpleName();

    /**
     * 定数 : 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final OverviewTranslationColumnKey[] OVERVIEW_TRANSLATION_COLUMN_KEY = OverviewTranslationColumnKey.values();
    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static OverviewTranslationInformation thisInstance = null;
    /**
     * 変数 : 検索結果を格納するモデルマップ。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.OverviewTranslationColumnKey}を使用する必要があります。
     *
     * @see android.app.java.com.duovoc.model.property.OverviewColumnKey
     */
    private ModelMap<OverviewTranslationColumnKey, Object> modelMap = new ModelMap<>();

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private OverviewTranslationInformation(final Context context) {
        super(context, Table.OverviewTranslationInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #OverviewTranslationInformation(Context)
     */
    public static OverviewTranslationInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new OverviewTranslationInformation(context);
        }

        return thisInstance;
    }

    public boolean selectByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(OverviewTranslationColumnKey.Id, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        if (!super.isSucceeded(cursor)) {
            // should not be happened
            return false;
        }

        if (cursor.moveToFirst()) {

            final ModelMap<OverviewTranslationColumnKey, Object> modelMap = new ModelMap<>();

            // 一意制約検索のため検索結果は一件のみ
            for (OverviewTranslationColumnKey column : OVERVIEW_TRANSLATION_COLUMN_KEY) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);
        }

        return true;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルリストは更新されません。
     *
     * @param overviewTranslationHolder 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @return 挿入処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see ModelBase#replaceAll(List)
     */
    public boolean replace(OverviewTranslationHolder overviewTranslationHolder) {

        final List<InsertHolder> insertHolderList = new ArrayList<>();

        InsertHolder insertHolder = new InsertHolder();
        ContentValues contentValues = insertHolder.getContentValues();

        for (OverviewTranslationColumnKey column : OVERVIEW_TRANSLATION_COLUMN_KEY) {
            column.setContentValues(contentValues, overviewTranslationHolder);
        }

        insertHolderList.add(insertHolder);

        return super.replaceAll(insertHolderList);
    }

    public ModelMap<OverviewTranslationColumnKey, Object> getModelInfo() {
        return this.modelMap;
    }

    private void setModelInfo(ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
        this.modelMap = modelMap;
    }

}