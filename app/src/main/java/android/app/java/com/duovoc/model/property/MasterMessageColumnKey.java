package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.MasterDataMap;
import android.database.Cursor;

public enum MasterMessageColumnKey implements IModelMapKey {
    MessageId(Key.message_id) {
        @Override
        public void setMasterDataMap(final Cursor cursor, final MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {

            this.setStringIfNotEmpty(cursor, masterDataMap);
        }
    },

    Message(Key.message) {
        @Override
        public void setMasterDataMap(final Cursor cursor, final MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {

            this.setStringIfNotEmpty(cursor, masterDataMap);
        }
    },

    LanguageKind(Key.language_kind) {
        @Override
        public void setMasterDataMap(final Cursor cursor, final MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {

            this.setStringIfNotEmpty(cursor, masterDataMap);
        }
    },

    ErrorKind(Key.error_kind) {
        @Override
        public void setMasterDataMap(final Cursor cursor, final MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {

            this.setStringIfNotEmpty(cursor, masterDataMap);
        }
    };

    private Key key;

    MasterMessageColumnKey(Key key) {
        this.key = key;
    }

    protected void setStringIfNotEmpty(final Cursor cursor, final MasterDataMap<MasterMessageColumnKey, Object> masterDataMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            masterDataMap.put(this, cursor.getString(index));
        }
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setMasterDataMap(Cursor cursor, MasterDataMap<MasterMessageColumnKey, Object> masterDataMap);

    private enum Key {
        message_id,
        message,
        language_kind,
        error_kind;
    }
}
