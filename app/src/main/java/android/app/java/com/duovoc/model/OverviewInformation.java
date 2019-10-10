package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.ModelList;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.holder.OverviewHolder;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.holder.SelectHolder;
import android.app.java.com.duovoc.model.property.CurrentUserColumnKey;
import android.app.java.com.duovoc.model.property.OverviewColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * テーブル名「overview_information」に対するトランザクション処理を管理するモデルオブジェクトです。
 * 当該モデルオブジェクトでは以下のトランザクション操作が実装されています。
 *
 * @author Kato Shinya
 * @version 1.0
 * @see #selectAll()
 * 1, Select
 * ├引数として渡された情報を基にレコードの検索処理を実行します。
 * └検索結果はモデルリストに格納され{@code getModelInfo()}を実行することで取得できます。
 * @see #replace(List)
 * 2, replace
 * ├引数として渡された情報を基にレコードの挿入・更新処理を実行します。
 * └当該処理に依ってモデルリストは更新されません。
 * @see BaseModel
 * @since 1.0
 */
final public class OverviewInformation extends BaseModel {

    /**
     * 定数 : クラス名を保持する。
     */
    private static final String TAG = OverviewInformation.class.getSimpleName();

    /**
     * 定数 : 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final OverviewColumnKey[] OVERVIEW_COLUMN_KEYS = OverviewColumnKey.values();

    /**
     * 変数 : 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static OverviewInformation thisInstance = null;

    /**
     * 変数 : 検索結果を格納するモデルリスト。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.OverviewColumnKey}を使用する必要があります。
     *
     * @see #selectAll()
     * @see android.app.java.com.duovoc.model.property.OverviewColumnKey
     */
    private ModelList<ModelMap<OverviewColumnKey, Object>> modelInfo = new ModelList<>();

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private OverviewInformation(Context context) {
        super(context, Table.OverviewInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #OverviewInformation(Context)
     */
    public static OverviewInformation getInstance(Context context) {

        if (thisInstance == null) {
            thisInstance = new OverviewInformation(context);
        }

        return thisInstance;
    }

    /**
     * 全レコードの検索処理を実行します。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @return 検索処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see BaseModel#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public boolean selectAll() {

        SelectHolder selectHolder = new SelectHolder();
        selectHolder.setColumns(null);

        return super.select(selectHolder);
    }

    public boolean selectByCurrentUserInformation(
            final String userId,
            final String language,
            final String fromLanguage) {

        if (!StringChecker.isEffectiveString(userId)
                || !StringChecker.isEffectiveString(language)
                || !StringChecker.isEffectiveString(fromLanguage)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final String[] selections = new String[]{
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.UserId.getKeyName()),
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.Language.getKeyName()),
                String.format(FORMAT_WHERE_CLAUSE, CurrentUserColumnKey.FromLanguage.getKeyName())
        };

        final SelectHolder selectHolder = new SelectHolder();
        selectHolder.setSelection(String.join(Operand.AND.getValue(), selections));
        selectHolder.setSelectionArgs(new String[]{userId, language, fromLanguage});

        return super.select(selectHolder);
    }

    /**
     * プライマリキーを基にレコードの検索処理を行います。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @param primaryKey 主キー。
     * @see BaseModel#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public boolean selectByPrimaryKey(final String primaryKey) {

        return super.selectByPrimaryKey(OverviewColumnKey.Id, primaryKey);
    }

    /**
     * 語彙素の識別IDを基にレコードの検索処理を行います。
     * 検索結果はモデルリストに格納され、
     * {@code getModelInfo()}を実行することで取得できます。
     *
     * @see BaseModel#select(SelectHolder)
     * @see #onPostSelect(Cursor)
     * @see #getModelInfo()
     */
    public boolean selectByLexemeId(final String lexemeId) {

        return super.selectByPrimaryKey(OverviewColumnKey.LexemeId, lexemeId);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        // 検索結果の初期化
        this.setModelInfo(new ModelList<>());

        if (!super.isSucceeded(cursor)) {
            return false;
        }

        final ModelList<ModelMap<OverviewColumnKey, Object>> modelMaps = new ModelList<>();

        if (cursor.moveToFirst()) {
            for (int i = 0, countRecords = cursor.getCount(); i < countRecords; i++) {

                final ModelMap<OverviewColumnKey, Object> modelMap = new ModelMap<>();

                for (OverviewColumnKey column : OVERVIEW_COLUMN_KEYS) {
                    column.setModelMap(cursor, modelMap);
                }

                modelMaps.add(modelMap);
                cursor.moveToNext();
            }
        }

        this.setModelInfo(modelMaps);

        return true;
    }

    /**
     * 渡された引数の情報を基にレコードの挿入処理を実行します。
     * 当該処理に依ってモデルリストは更新されません。
     *
     * @param overviewHolderList 挿入処理を行う際に必要な情報が格納されたデータクラスのリスト。
     * @return 挿入処理が成功した場合は{@code true}、その他の場合は{@code false}。
     * @see BaseModel#replaceAll(List)
     */
    public boolean replace(List<OverviewHolder> overviewHolderList) {

        List<InsertHolder> insertHolderList = new ArrayList<>();

        for (OverviewHolder overviewHolder : overviewHolderList) {

            InsertHolder insertHolder = new InsertHolder();
            ContentValues contentValues = insertHolder.getContentValues();

            for (OverviewColumnKey overviewColumnKey : OVERVIEW_COLUMN_KEYS) {
                overviewColumnKey.setContentValues(contentValues, overviewHolder);
            }

            insertHolderList.add(insertHolder);
        }

        return super.replaceAll(insertHolderList);
    }

    /**
     * 検索結果で取得したモデルリストを格納したリストを返却します。
     * 検索処理が行われていない状態では空のリストが返却されます。
     * そのため、呼び出し元で検索結果を取得したい場合は、
     * 当該メソッドを実行する前に必ず検索処理を実行する必要があります。
     *
     * @return 検索処理結果を格納したモデルリスト。
     * @see #selectAll()
     * @see #selectByPrimaryKey(String)
     */
    public ModelList<ModelMap<OverviewColumnKey, Object>> getModelInfo() {
        return this.modelInfo;
    }

    /**
     * 検索処理で取得したモデルリストを格納する。
     *
     * @param modelInfo 検索処理結果を格納したモデルリスト。
     * @see #selectAll()
     * @see #selectByPrimaryKey(String)
     * @see #getModelInfo()
     */
    private void setModelInfo(ModelList<ModelMap<OverviewColumnKey, Object>> modelInfo) {
        this.modelInfo = modelInfo;
    }
}
