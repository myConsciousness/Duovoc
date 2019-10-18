package android.app.java.com.duovoc.framework;

import java.util.EnumMap;
import java.util.List;

final public class ModelMap<E extends Enum<E> & IModelMapKey, V> extends EnumMap<E, V> {

    private static final long serialVersionUID = 5033524892917061928L;

    public ModelMap(Class<E> keyType) {
        super(keyType);
    }

    public String getString(IModelMapKey key) {
        return (String) this.get(key);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(IModelMapKey key) {
        return (List<String>) this.get(key);
    }
}
