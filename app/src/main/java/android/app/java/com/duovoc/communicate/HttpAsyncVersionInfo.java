package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.holder.SupportedLanguageHolder;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HttpAsyncVersionInfo extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncVersionInfo.class.getSimpleName();

    private static final String JSON_PROPERTY_SUPPORTED_DIRECTIONS = "supported_directions";


    protected HttpAsyncVersionInfo() {

    }

    @Override
    protected HttpAsyncResults doInBackground(Void... params) {

        final Request request = new Request();
        request.send(Api.VersionInfo.getUrl(), RequestMethod.Get);

        final List<SupportedLanguageHolder> supportedLanguageHolderList = new ArrayList<>();

        // TODO:
        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final JSONArray supportedDirections = jsonObject.getJSONArray(JSON_PROPERTY_SUPPORTED_DIRECTIONS);

            final SupportedLanguageHolder supportedLanguageHolder = new SupportedLanguageHolder();
            final JSONObject directions = supportedDirections.getJSONObject(0);
            final Iterator iterator = directions.keys();

            while (iterator.hasNext()) {
                final String key = (String) iterator.next();
                final JSONArray languageCodes = directions.getJSONArray(key);

                for (int i = 0, codesSize = languageCodes.length(); i < codesSize; i++) {
                    final JSONObject languageCode = languageCodes.getJSONObject(i);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(null, null);
    }
}
