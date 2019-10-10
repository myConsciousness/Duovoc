package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.SwitchLanguageQuery;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.holder.SwitchLanguageHolder;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpAsyncSwitchLanguage extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncSwitchLanguage.class.getSimpleName();

    private static final String JSON_PROPERTY_FIRST_TIME = "first_time";

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

        final SwitchLanguageHolder switchLanguageHolder = new SwitchLanguageHolder();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final boolean firstTime = jsonObject.getBoolean(JSON_PROPERTY_FIRST_TIME);
            switchLanguageHolder.setFirstTime(firstTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final HttpStatusCode httpStatusCode
                = HttpStatusCode.getStatusFromCode(request.getResponseCode());

        return new HttpAsyncResults(httpStatusCode, switchLanguageHolder);
    }

    private String getLearningLanguage() {
        return this.learningLanguage;
    }

    private String getFromLanguage() {
        return this.fromLanguage;
    }
}
