package android.app.java.com.duovoc.framework;

import java.util.HashMap;
import java.util.List;

final public class ModelMap<K, V> extends HashMap<K, V> {

    public ModelMap() {
        super();
    }

    public String getString(IModelMapKey key) {
        return (String) this.get(key);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(IModelMapKey key) {
        return (List<String>) this.get(key);
    }
}
