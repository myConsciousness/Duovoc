package dev.app.ks.thinkit.duovoc.model.property;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import dev.app.ks.thinkit.duovoc.framework.CalendarHandler;
import dev.app.ks.thinkit.duovoc.framework.IModelMapKey;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.StringHandler;
import dev.app.ks.thinkit.duovoc.framework.model.CursorHandler;
import dev.app.ks.thinkit.duovoc.model.holder.OverviewTranslationHolder;

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

    Header(Key.header) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewTranslationHolder overviewTranslationHolder) {
            contentValues.put(this.getKeyName(), StringHandler.trim(overviewTranslationHolder.getHeader()));
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
            contentValues.put(this.getKeyName(), StringHandler.trim(String.join("", hintsList)));
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewTranslationColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewTranslationHolder overviewTranslationHolder) {
            final String currentClientDatetime = CalendarHandler.getClientDatetime();
            contentValues.put(this.getKeyName(), currentClientDatetime);
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
        header,
        translation,
        modified_datetime,
    }
}
