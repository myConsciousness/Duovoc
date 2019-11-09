package dev.app.ks.thinkit.duovoc.framework.model.property;

import android.database.Cursor;

import dev.app.ks.thinkit.duovoc.framework.IModelMapKey;
import dev.app.ks.thinkit.duovoc.framework.ModelMap;
import dev.app.ks.thinkit.duovoc.framework.model.CursorHandler;

public enum MasterMessageColumnKey implements IModelMapKey {
    MessageId(Key.message_id) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }
    },

    Message(Key.message) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }
    },

    LanguageKind(Key.language_kind) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }
    },

    ErrorKind(Key.error_kind) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }
    },

    ModifiedDatetime(Key.modified_datetime) {
        @Override
        public void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }
    };

    private Key key;

    MasterMessageColumnKey(Key key) {
        this.key = key;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<MasterMessageColumnKey, Object> modelMap);

    private enum Key {
        message_id,
        message,
        language_kind,
        error_kind,
        modified_datetime
    }
}
