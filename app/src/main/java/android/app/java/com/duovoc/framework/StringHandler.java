package android.app.java.com.duovoc.framework;

import java.util.ArrayList;
import java.util.List;

public class StringHandler {

    private StringHandler() {}

    public static List<String> removeEmptyValue(final List<String> stringList) {

        final List<String> result = new ArrayList<>();

        for (String value : stringList) {
            if (StringChecker.isEffectiveString(value)) {
                result.add(value);
            }
        }

        return result;
    }
}
