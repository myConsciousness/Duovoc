package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.holder.OverviewTranslationHolder;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public enum OverviewTranslationColumnKey implements IModelMapKey {
    Id(Key.id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewTranslationHolder overviewTranslationHolder) {
            contentValues.put(this.getKeyName(), overviewTranslationHolder.getId());
        }
    },
    Translation(Key.translation) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewTranslationHolder overviewTranslationHolder) {
            final List<String> hintsList = overviewTranslationHolder.getHints();
            contentValues.put(this.getKeyName(), String.join(CommonConstants.STRING_SEPARATOR_PERIOD, hintsList));
        }
    };

    private Key key;

    OverviewTranslationColumnKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<OverviewTranslationColumnKey, Object> modelMap);

    public abstract void setContentValues(ContentValues contentValues, OverviewTranslationHolder overviewTranslationHolder);

    private enum Key {
        id,
        translation,
    }
}
