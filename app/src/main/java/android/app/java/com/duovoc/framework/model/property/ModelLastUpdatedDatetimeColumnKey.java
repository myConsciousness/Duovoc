package android.app.java.com.duovoc.framework.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.framework.model.holder.ModelLastUpdatedDatetimeHolder;
import android.content.ContentValues;
import android.database.Cursor;

public enum ModelLastUpdatedDatetimeColumnKey implements IModelMapKey {
    ModelPhysicalName(Key.model_physical_name) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder) {
            contentValues.put(this.getKeyName(), modelLastUpdatedDatetimeHolder.getModelPhysicalName());
        }
    },

    last_updated_datetime(Key.last_updated_datetime) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder) {
            contentValues.put(this.getKeyName(), modelLastUpdatedDatetimeHolder.getLastUpdatedDatetime());
        }
    },

    last_updated_date(Key.last_updated_date) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder) {
            contentValues.put(this.getKeyName(), modelLastUpdatedDatetimeHolder.getLastUpdatedDate());
        }
    },

    last_updated_time(Key.last_updated_time) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder) {
            contentValues.put(this.getKeyName(), modelLastUpdatedDatetimeHolder.getLastUpdatedTime());
        }
    };

    private Key key;

    ModelLastUpdatedDatetimeColumnKey(Key key) {
        this.key = key;
    }

    private enum Key {
        model_physical_name,
        last_updated_datetime,
        last_updated_date,
        last_updated_time,
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    /**
     * モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param cursor   カーソルオブジェクト。
     * @param modelMap 値を設定するモデルマップ。
     */
    public abstract void setModelMap(Cursor cursor, ModelMap<ModelLastUpdatedDatetimeColumnKey, Object> modelMap);

    /**
     * モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param contentValues                  挿入情報を保持するオブジェクト。
     * @param modelLastUpdatedDatetimeHolder モデル最終更新日時情報を保持するオブジェクト。
     */
    public abstract void setContentValues(ContentValues contentValues, ModelLastUpdatedDatetimeHolder modelLastUpdatedDatetimeHolder);
}
