package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.LoginQuery;
import android.app.java.com.duovoc.communicate.property.UserJsonProperties;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;
import android.app.java.com.duovoc.model.holder.UserHolder;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpAsyncLogin extends AsyncTask<String, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncLogin.class.getSimpleName();

    protected HttpAsyncLogin() {
    }

    @Override
    protected HttpAsyncResults doInBackground(String... params) {

        final Request request = new Request();
        request.send(
                Api.Login.getUrl(),
                RequestMethod.Post,
                this.makeQueryMap(params));

        final List<ModelAccessor> userHolderList = new ArrayList<>();
        final UserHolder userHolder = new UserHolder();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());

            if (!jsonObject.has(UserJsonProperties.Response.getKeyName())) {
                userHolderList.add(userHolder);
                return new HttpAsyncResults(request.getHttpStatus(), userHolderList);
            }

            final UserJsonProperties[] userJsonPropertiesList = UserJsonProperties.values();

            for (UserJsonProperties userJsonProperty : userJsonPropertiesList) {
                userJsonProperty.set(jsonObject, userHolder);
            }

            userHolderList.add(userHolder);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(request.getHttpStatus(), userHolderList);
    }

    private Map<String, String> makeQueryMap(String[] params) {

        final Map<String, String> queryMap = new HashMap<>();
        final LoginQuery[] loginQueries = LoginQuery.values();
        final int paramLength = params.length;

        if (loginQueries.length != paramLength) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < paramLength; i++) {
            queryMap.put(loginQueries[i].getQueryName(), params[i]);
        }

        return queryMap;
    }
}
