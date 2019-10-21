package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.CalendarHandler;
import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.model.holder.SupportedLanguageHolder;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public enum SupportedLanguageColumnKey implements IModelMapKey {
    FromLanguage(Key.from_language) {
        @Override
        public void setModelMap(final Cursor cursor, final ModelMap<SupportedLanguageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(final ContentValues contentValues, final SupportedLanguageHolder supportedLanguageHolder) {
            contentValues.put(this.getKeyName(), supportedLanguageHolder.getFromLanguage());
        }
    },

    LearningLanguage(Key.learning_language) {
        @Override
        public void setModelMap(final Cursor cursor, final ModelMap<SupportedLanguageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(final ContentValues contentValues, final SupportedLanguageHolder supportedLanguageHolder) {

            final List<String> learningLanguageList = supportedLanguageHolder.getLearningLanguageList();
            final StringBuilder sb = new StringBuilder();

            learningLanguageList
                    .forEach(learningLanguage -> sb.append(learningLanguage).append(CommonConstants.CHAR_SEPARATOR_PERIOD));

            // 末尾の区切り文字を消去
            sb.setLength(sb.length() - 1);

            contentValues.put(this.getKeyName(), sb.toString());
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, final ModelMap<SupportedLanguageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(final ContentValues contentValues, final SupportedLanguageHolder supportedLanguageHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
        }
    };

    private Key key;

    SupportedLanguageColumnKey(Key key) {
        this.key = key;
    }

    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(final Cursor cursor, final ModelMap<SupportedLanguageColumnKey, Object> modelMap);

    /**
     * モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param contentValues           挿入情報を保持するオブジェクト。
     * @param supportedLanguageHolder サポート言語情報を保持するオブジェクト。
     */
    public abstract void setContentValues(final ContentValues contentValues, final SupportedLanguageHolder supportedLanguageHolder);

    private enum Key {
        from_language,
        learning_language,
        modified_datetime,
    }

}
