package android.app.java.com.duovoc.framework;

final public class StringChecker {

    private StringChecker() {

    }

    public static boolean isEffectiveString(String value) {
        return value != null && value.length() > 0;
    }
}
