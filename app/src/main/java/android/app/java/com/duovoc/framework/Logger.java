package android.app.java.com.duovoc.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Logger {
    Info {
        @Override
        public void write(final String tag, final String methodName, final String message, final String... replaceArgs) {

            this.write(tag, methodName, message, this.getReplaceList(replaceArgs));
        }

        @Override
        public void write(final String tag, final String methodName, final String message, final List<String> replaceList) {

            Log.i(tag, this.formatMessage(methodName, message, replaceList));
        }
    },

    Debug {
        @Override
        public void write(final String tag, final String methodName, final String message, final String... replaceArgs) {

            this.write(tag, methodName, message, this.getReplaceList(replaceArgs));
        }

        @Override
        public void write(final String tag, final String methodName, final String message, final List<String> replaceList) {

            Log.d(tag, this.formatMessage(methodName, message, replaceList));
        }
    },

    Warn {
        @Override
        public void write(final String tag, final String methodName, final String message, final String... replaceArgs) {

            this.write(tag, methodName, message, this.getReplaceList(replaceArgs));
        }

        @Override
        public void write(final String tag, final String methodName, final String message, final List<String> replaceList) {

            Log.w(tag, this.formatMessage(methodName, message, replaceList));
        }
    },

    Error  {
        @Override
        public void write(final String tag, final String methodName, final String message, final String... replaceArgs) {

            this.write(tag, methodName, message, this.getReplaceList(replaceArgs));
        }

        @Override
        public void write(final String tag, final String methodName, final String message, final List<String> replaceList) {

            Log.e(tag, this.formatMessage(methodName, message, replaceList));
        }
    };

    private static final String LOG_FORMAT = "%s [%s] :: %s";

    Logger() {}

    protected String formatMessage(final String methodName, final String message, final List<String> replaceList) {

        final String replacedMessage = replaceList.isEmpty() ? message : String.format(message, replaceList);

        return String.format(LOG_FORMAT, this.name(), methodName, replacedMessage);
    }

    protected List<String> getReplaceList(final String[] replaceArgs) {

        List<String> replaceList = new ArrayList<>();

        if (replaceArgs.length > 0) {
            Arrays.asList(replaceArgs)
                    .stream()
                    .forEach(arg -> replaceList.add(arg));
        }

        return replaceList;
    }

    public abstract void write(final String tag, final String methodName, final String message, final String... replaceArgs);
    public abstract void write(final String tag, final String methodName, final String message, final List<String> replaceList);
}
