package dev.app.ks.thinkit.duovoc.communicate;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.app.ks.thinkit.duovoc.communicate.property.Api;
import dev.app.ks.thinkit.duovoc.communicate.property.SwitchLanguageQuery;
import dev.app.ks.thinkit.duovoc.framework.IHttpAsync;
import dev.app.ks.thinkit.duovoc.framework.StringChecker;
import dev.app.ks.thinkit.duovoc.framework.communicate.Request;
import dev.app.ks.thinkit.duovoc.framework.communicate.holder.HttpAsyncResults;
import dev.app.ks.thinkit.duovoc.framework.communicate.property.RequestMethod;
import dev.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;
import dev.app.ks.thinkit.duovoc.model.holder.SwitchLanguageHolder;

public class HttpAsyncSwitchLanguage extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncSwitchLanguage.class.getName();

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

        final List<ModelAccessor> switchLanguageHolderList = new ArrayList<>();
        final SwitchLanguageHolder switchLanguageHolder = new SwitchLanguageHolder();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final boolean firstTime = jsonObject.getBoolean(JSON_PROPERTY_FIRST_TIME);
            switchLanguageHolder.setFirstTime(firstTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switchLanguageHolderList.add(switchLanguageHolder);

        return new HttpAsyncResults(request.getHttpStatus(), switchLanguageHolderList);
    }

    private String getLearningLanguage() {
        return this.learningLanguage;
    }

    private String getFromLanguage() {
        return this.fromLanguage;
    }
}
