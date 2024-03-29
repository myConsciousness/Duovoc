package dev.app.ks.thinkit.duovoc.framework;

import java.util.EnumMap;
import java.util.List;

public final class ModelMap<E extends Enum<E> & IModelMapKey, V> extends EnumMap<E, V> {

    private static final long serialVersionUID = 5033524892917061928L;

    public ModelMap(Class<E> keyType) {
        super(keyType);
    }

    public String getString(IModelMapKey key) {
        return (String) this.get(key);
    }

    public Integer getInteger(IModelMapKey key) {
        return (Integer) this.get(key);
    }

    public Double getDouble(IModelMapKey key) {
        return (Double) this.get(key);
    }

    @SuppressWarnings("unchecked")
    public List<String> getStringList(IModelMapKey key) {
        return (List<String>) this.get(key);
    }
}
