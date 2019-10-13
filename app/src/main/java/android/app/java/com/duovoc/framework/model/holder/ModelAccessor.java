package android.app.java.com.duovoc.framework.model.holder;

public abstract class ModelAccessor {
  
  private static final String TAG = ModelAccessor.class.getSimpleName();
  
  private final EnumMap<Class, Object> currentModelMap;
  
  protected ModelAccessor(Class mapKeys) {
      this.currentModelMap = new EnumMap<>(mapKeys);
  }
}
