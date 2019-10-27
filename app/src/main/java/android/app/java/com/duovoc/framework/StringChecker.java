package android.app.java.com.duovoc.framework;

public final class StringChecker {

    private StringChecker() {

    }

    public static boolean isEffectiveString(String value) {
        return value != null && value.length() > 0;
    }
}
