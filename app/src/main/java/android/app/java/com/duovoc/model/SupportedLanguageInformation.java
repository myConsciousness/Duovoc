package android.app.java.com.duovoc.model;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.framework.model.BaseModel;
import android.app.java.com.duovoc.holder.SupportedLanguageHolder;
import android.app.java.com.duovoc.model.holder.InsertHolder;
import android.app.java.com.duovoc.model.property.SupportedLanguageColumnKey;
import android.app.java.com.duovoc.model.property.Table;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class SupportedLanguageInformation extends BaseModel {

    /**
     * クラス名。
     */
    private static final String TAG = SupportedLanguageInformation.class.getSimpleName();

    /**
     * 当該モデルで扱う全カラムの振る舞いを保持する。
     */
    private static final SupportedLanguageColumnKey[] SUPPORTED_LANGUAGE_COLUMN_KEYS = SupportedLanguageColumnKey.values();

    /**
     * 当該クラスのインスタンスを格納する。
     * 当該クラスにシングルトンパターンを適用するためnullで初期化する。
     *
     * @see #getInstance(Context)
     */
    private static SupportedLanguageInformation thisInstance = null;

    /**
     * 変数 : 検索結果を格納するモデルマップ。
     * 各レコード情報を取得する際には、
     * {@link android.app.java.com.duovoc.model.property.SupportedLanguageColumnKey}を使用する必要があります。
     *
     * @see #selectByPrimaryKey(String)
     * @see android.app.java.com.duovoc.model.property.SupportedLanguageColumnKey
     */
    private ModelMap<SupportedLanguageColumnKey, String> modelMap = new ModelMap<>();

    /**
     * 主キーの言語区分に紐づく対応言語を格納するリスト。
     * 対応言語はモデル上CSV形式で保存されており、
     * 当該リストにはカンマ区切りで分割した情報を設定します。
     *
     * @see #getSupportedDirections()
     */
    private List<String> supportedDirections = new ArrayList<>();

    /**
     * 当該クラスのコンストラクタ。
     * 当該クラスにシングルトンパターンを適用するため修飾子をprivate指定する。
     *
     * @param context アプリケーション情報。
     * @see #getInstance(Context)
     */
    private SupportedLanguageInformation(final Context context) {
        super(context, Table.SupportedLanguageInformation);
    }

    /**
     * 当該クラスのインスタンスを返却する。
     * 初回実行時に当該クラスのインスタンスを生成した後に返却し、
     * 初回以降に実行された際には初回時に生成されたインスタンスを返却する。
     *
     * @param context アプリケーション情報。
     * @return 当該クラスのインスタンス。
     * @see #SupportedLanguageInformation(Context)
     */
    public static SupportedLanguageInformation getInstance(final Context context) {

        if (thisInstance == null) {
            thisInstance = new SupportedLanguageInformation(context);
        }

        return thisInstance;
    }

    public boolean selectByPrimaryKey(final String primaryKey) {
        return super.selectByPrimaryKey(SupportedLanguageColumnKey.FromLanguage, primaryKey);
    }

    @Override
    protected boolean onPostSelect(final Cursor cursor) {

        // 検索結果の初期化
        this.setModelInfo(new ModelMap<>());
        this.setSupportedDirections(new ArrayList<>());

        if (!super.isSucceeded(cursor)) {
            return false;
        }

        if (cursor.moveToFirst()) {
            final ModelMap<SupportedLanguageColumnKey, String> modelMap = new ModelMap<>();

            for (SupportedLanguageColumnKey column : SUPPORTED_LANGUAGE_COLUMN_KEYS) {
                column.setModelMap(cursor, modelMap);
            }

            this.setModelInfo(modelMap);

            final String csvTypeDirections = modelMap.getString(SupportedLanguageColumnKey.LearningLanguage);
            final String[] directionsArray = StringHandler.split(csvTypeDirections, CommonConstants.CHAR_SEPARATOR_PERIOD);
            this.setSupportedDirections(Arrays.asList(directionsArray));
        }

        return true;
    }

    public boolean replace(final SupportedLanguageHolder supportedLanguageHolder) {

        final InsertHolder insertHolder = new InsertHolder();
        final ContentValues contentValues = insertHolder.getContentValues();

        for (SupportedLanguageColumnKey column : SUPPORTED_LANGUAGE_COLUMN_KEYS) {
            column.setContentValues(contentValues, supportedLanguageHolder);
        }

        return super.replace(insertHolder);
    }

    public ModelMap<SupportedLanguageColumnKey, String> getModelInfo() {
        return this.modelMap;
    }

    private void setModelInfo(final ModelMap<SupportedLanguageColumnKey, String> modelMap) {
        this.modelMap = modelMap;
    }

    public List<String> getSupportedDirections() {
        return this.supportedDirections;
    }

    private void setSupportedDirections(final List<String> supportedDirections) {
        this.supportedDirections = supportedDirections;
    }
}