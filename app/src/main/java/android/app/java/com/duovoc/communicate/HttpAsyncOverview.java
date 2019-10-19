package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.OverViewJsonProperties;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.model.holder.OverviewHolder;
import android.app.java.com.duovoc.model.property.UserColumnKey;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HttpAsyncOverview extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncOverview.class.getSimpleName();

    private static final String JSON_PROPERTY_LEARNING_LANGUAGE = "learning_language";
    private static final String JSON_PROPERTY_LANGUAGE_STRING = "language_string";
    private static final String JSON_PROPERTY_FROM_LANGUAGE = "from_language";
    private static final String JSON_PROPERTY_VOCAB_OVERVIEW = "vocab_overview";

    private Intent intent;

    private String learningLanguage = "";

    protected HttpAsyncOverview(final Intent intent) {
        this.intent = intent;
    }

    @Override
    protected HttpAsyncResults doInBackground(Void... params) {

        final Request request = new Request();
        request.send(Api.Overview.getUrl(), RequestMethod.Get);

        final List<OverviewHolder> overviewHolderList = new ArrayList<>();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());

            final String languageString = jsonObject.getString(JSON_PROPERTY_LANGUAGE_STRING);
            final String learningLanguage = jsonObject.getString(JSON_PROPERTY_LEARNING_LANGUAGE);
            final String fromLanguage = jsonObject.getString(JSON_PROPERTY_FROM_LANGUAGE);

            this.setLearningLanguage(learningLanguage);

            final JSONArray jsonArray = (JSONArray) jsonObject.get(JSON_PROPERTY_VOCAB_OVERVIEW);
            final String userId = this.intent.getStringExtra(UserColumnKey.UserId.getKeyName());

            final int jsonArraySize = jsonArray.length();
            for (int i = 0; i < jsonArraySize; i++) {

                final OverviewHolder overviewHolder = new OverviewHolder();
                final OverViewJsonProperties[] overViewJsonProperties = OverViewJsonProperties.values();

                for (OverViewJsonProperties property : overViewJsonProperties) {
                    property.setOverviewHolder((JSONObject) jsonArray.get(i), overviewHolder);
                }

                overviewHolder.setUserId(userId);
                overviewHolder.setLanguageString(languageString);
                overviewHolder.setLanguage(learningLanguage);
                overviewHolder.setFromLanguage(fromLanguage);

                overviewHolderList.add(overviewHolder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(request.getHttpStatus(), overviewHolderList);
    }

    public String getLearningLanguage() {
        return this.learningLanguage;
    }

    private void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }
}
