package android.app.java.com.duovoc.framework;

import java.util.ArrayList;

public final class ModelList<E> extends ArrayList<E> {

    private static final long serialVersionUID = -8754005886458613904L;

    @Deprecated
    public ModelList() {
        super();
    }

    public ModelList(int initialCapacity) {
        super(initialCapacity);
    }
}
