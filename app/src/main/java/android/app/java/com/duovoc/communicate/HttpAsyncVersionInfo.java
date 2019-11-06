package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;
import android.app.java.com.duovoc.model.holder.SupportedLanguageHolder;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HttpAsyncVersionInfo extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncVersionInfo.class.getName();

    private static final String JSON_PROPERTY_SUPPORTED_DIRECTIONS = "supported_directions";

    protected HttpAsyncVersionInfo() {
    }

    @Override
    protected HttpAsyncResults doInBackground(Void... params) {

        final Request request = new Request();
        request.send(Api.VersionInfo.getUrl(), RequestMethod.Get);

        final List<ModelAccessor> supportedLanguageHolderList = new ArrayList<>();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final JSONObject supportedDirections = jsonObject.getJSONObject(JSON_PROPERTY_SUPPORTED_DIRECTIONS);

            final Iterator iterator = supportedDirections.keys();

            while (iterator.hasNext()) {
                final String key = (String) iterator.next();
                final JSONArray languageCodes = supportedDirections.getJSONArray(key);

                final SupportedLanguageHolder supportedLanguageHolder = new SupportedLanguageHolder();
                supportedLanguageHolder.setFromLanguage(key);

                final List<String> learningLanguageList = new ArrayList<>();

                for (int i = 0, objectSize = languageCodes.length(); i < objectSize; i++) {
                    final String languageCode = languageCodes.getString(i);
                    learningLanguageList.add(languageCode);
                }

                supportedLanguageHolder.setLearningLanguageList(learningLanguageList);
                supportedLanguageHolderList.add(supportedLanguageHolder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(request.getHttpStatus(), supportedLanguageHolderList);
    }
}
