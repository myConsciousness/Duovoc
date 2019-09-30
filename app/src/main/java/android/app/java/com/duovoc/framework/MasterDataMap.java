package android.app.java.com.duovoc.framework;

import java.util.HashMap;

final public class MasterDataMap<K, V> extends HashMap<K, V> {

    public MasterDataMap() {
        super();
    }

    public String getString(IModelMapKey key) {
        return (String) this.get(key);
    }
}
