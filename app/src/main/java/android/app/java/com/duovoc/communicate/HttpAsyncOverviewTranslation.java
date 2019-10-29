package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.OverviewTranslationJsonProperties;
import android.app.java.com.duovoc.communicate.property.OverviewTranslationQuery;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.model.holder.OverviewTranslationHolder;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpAsyncOverviewTranslation extends AsyncTask<String, Void, HttpAsyncResults> implements IHttpAsync {

    private static final String TAG = HttpAsyncOverviewTranslation.class.getSimpleName();

    private static final String JSON_PROPERTY_TOKENS = "tokens";
    private static final String JSON_PROPERTY_HINT_TABLE = "hint_table";
    private static final String JSON_PROPERTY_ROWS = "rows";
    private static final String JSON_PROPERTY_CELLS = "cells";

    private String overviewId;
    private String language;
    private String fromLanguage;

    protected HttpAsyncOverviewTranslation(
            final String overviewId,
            final String language,
            final String fromLanguage) {

        if (!StringChecker.isEffectiveString(overviewId)
                || !StringChecker.isEffectiveString(language)
                || !StringChecker.isEffectiveString(fromLanguage)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        this.overviewId = overviewId;
        this.language = language;
        this.fromLanguage = fromLanguage;
    }

    @Override
    protected HttpAsyncResults doInBackground(String... params) {

        final String requestUrl = String.format(Api.OverviewTranslation.getUrl(), this.language, this.fromLanguage);

        final Request request = new Request();
        request.send(
                requestUrl,
                RequestMethod.Get,
                this.makeQueryMap(params));

        final List<OverviewTranslationHolder> overviewTranslationHolderList = new ArrayList<>();
        final OverviewTranslationHolder overviewTranslationHolder = new OverviewTranslationHolder();
        overviewTranslationHolder.setId(this.overviewId);

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final JSONObject jsonObjectTokens = jsonObject.getJSONArray(JSON_PROPERTY_TOKENS).getJSONObject(0);
            final JSONObject jsonObjectHintTable = jsonObjectTokens.getJSONObject(JSON_PROPERTY_HINT_TABLE);
            final JSONArray jsonArrayRows = jsonObjectHintTable.optJSONArray(JSON_PROPERTY_ROWS);

            final List<String> hintsList = new ArrayList<>();

            for (int i = 0, rowSize = Objects.requireNonNull(jsonArrayRows).length(); i < rowSize; i++) {
                final JSONObject jsonObjectRow = jsonArrayRows.getJSONObject(i);
                final JSONArray jsonArrayCells = jsonObjectRow.getJSONArray(JSON_PROPERTY_CELLS);

                for (int j = 0, cellSize = jsonArrayCells.length(); j < cellSize; j++) {
                    final JSONObject jsonObjectCell = jsonArrayCells.getJSONObject(j);

                    if (jsonObjectCell.length() > 0) {
                        final OverviewTranslationJsonProperties[] overviewTranslationJsonProperties = OverviewTranslationJsonProperties.values();

                        for (OverviewTranslationJsonProperties property : overviewTranslationJsonProperties) {
                            property.set(jsonObjectCell, hintsList);
                        }
                    }
                }

                if (this.isCancelled()) {
                    overviewTranslationHolderList.add(new OverviewTranslationHolder());
                    return new HttpAsyncResults(request.getHttpStatus(), overviewTranslationHolderList);
                }
            }

            overviewTranslationHolder.setHints(hintsList);
            overviewTranslationHolderList.add(overviewTranslationHolder);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(request.getHttpStatus(), overviewTranslationHolderList);
    }

    private Map<String, String> makeQueryMap(String[] params) {

        final Map<String, String> queryMap = new HashMap<>();
        final OverviewTranslationQuery[] overviewTranslationQueries = OverviewTranslationQuery.values();
        final int paramLength = params.length;

        if (overviewTranslationQueries.length != paramLength) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < paramLength; i++) {
            queryMap.put(overviewTranslationQueries[i].getQueryName(), params[i]);
        }

        return queryMap;
    }
}
