package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.SwitchLanguageQuery;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

public class HttpAsyncSwitchLanguage extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncSwitchLanguage.class.getSimpleName();

    private final String learningLanguage;
    private final String fromLanguage;

    protected HttpAsyncSwitchLanguage(final String learningLanguage, final String fromLanguage) {

        if (!StringChecker.isEffectiveString(learningLanguage)
                || !StringChecker.isEffectiveString(fromLanguage)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        this.learningLanguage = learningLanguage;
        this.fromLanguage = fromLanguage;
    }

    @Override
    protected HttpAsyncResults doInBackground(Void... params) {

        final Map<String, String> queryMap = new HashMap<>();
        queryMap.put(SwitchLanguageQuery.LearningLanguage.getQueryName(), this.getLearningLanguage());
        queryMap.put(SwitchLanguageQuery.FromLanguage.getQueryName(), this.getFromLanguage());

        final Request request = new Request();
        request.send(
                Api.SwitchLanguage.getUrl(),
                RequestMethod.Post,
                queryMap);

        final HttpStatusCode httpStatusCode
                = HttpStatusCode.getStatusFromCode(request.getResponseCode());

        return new HttpAsyncResults(httpStatusCode, null);
    }

    private String getLearningLanguage() {
        return this.learningLanguage;
    }

    private String getFromLanguage() {
        return this.fromLanguage;
    }
}
