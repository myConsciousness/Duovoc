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

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : HttpAsyncOverviewTranslation.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * Duolingoの翻訳情報取得APIに対しリクエストを送信し、
 * JSONデータとして返却されたレスポンスから翻訳情報を抽出する処理を定義したクラスです。
 * <p>
 * 当該クラスのメソッドは非同期で実行され、
 * 当該クラスの非同期処理開始前および終了後の処理は呼び出し元のクラスで実装する必要があります。
 * <p>
 * リクエスト / レスポンス例 :
 * https://d2.duolingo.com/words/hints/ja/en?sentence=%E6%9D%B1%E4%BA%AC&format=new
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public class HttpAsyncOverviewTranslation extends AsyncTask<String, Void, HttpAsyncResults> implements IHttpAsync {

    /**
    * クラス名。
    */
    private static final String TAG = HttpAsyncOverviewTranslation.class.getSimpleName();

    /**
    * JSONデータのtokensキーを表す定数値です。
    *
    * @see #JSON_PROPERTY_HINT_TABLE
    * @see #JSON_PROPERTY_HEADERS
    * @see #JSON_PROPERTY_HEADER_SELECTED
    * @see #JSON_PROPERTY_HEADER_TOKEN
    * @see #JSON_PROPERTY_ROWS
    * @see #JSON_PROPERTY_CELLS
    */
    private static final String JSON_PROPERTY_TOKENS = "tokens";

    /**
    * JSONデータのtokens.hint_tableキーを表す定数値です。
    *
    * @see #JSON_PROPERTY_HEADERS
    * @see #JSON_PROPERTY_HEADER_SELECTED
    * @see #JSON_PROPERTY_HEADER_TOKEN
    * @see #JSON_PROPERTY_ROWS
    * @see #JSON_PROPERTY_CELLS
    */
    private static final String JSON_PROPERTY_HINT_TABLE = "hint_table";

    /**
    * JSONデータのtokens.hint_table.headersキーを表す定数値です。
    *
    * @see #JSON_PROPERTY_HEADER_SELECTED
    * @see #JSON_PROPERTY_HEADER_TOKEN
    */
    private static final String JSON_PROPERTY_HEADERS = "headers";

    /**
    * JSONデータのtokens.hint_table.headers.selectedキーを表す定数値です。
    */
    private static final String JSON_PROPERTY_HEADER_SELECTED = "selected";

    /**
    * JSONデータのtokens.hint_table.headers.tokenキーを表す定数値です。
    */
    private static final String JSON_PROPERTY_HEADER_TOKEN = "token";

    /**
    * JSONデータのtokens.hint_table.token.rowsキーを表す定数値です。
    *
    * @see #JSON_PROPERTY_CELLS
    */
    private static final String JSON_PROPERTY_ROWS = "rows";

    /**
    * JSONデータのtokens.hint_table.token.rows.cellsキーを表す定数値です。
    */
    private static final String JSON_PROPERTY_CELLS = "cells";

    /**
    * 翻訳情報に紐づく識別ID。
    */
    private String overviewId;

    /**
    * 学習言語区分。
    */
    private String language;

    /**
    * 学習時使用言語区分
    */
    private String fromLanguage;

    /**
    * 当該クラスのコンストラクタです。
    *
    * @param overviewId 翻訳情報に紐づく識別ID。
    * @param language 学習言語区分。
    * @param fromLanguage 学習時使用言語区分。
    */
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
                this.createQueryParameter(params));

        final List<OverviewTranslationHolder> overviewTranslationHolderList = new ArrayList<>();

        try {
            final JSONObject jsonObject = new JSONObject(request.getResponse());
            final JSONArray jsonTokensArray = jsonObject.getJSONArray(JSON_PROPERTY_TOKENS);

            for (int i = 0, tokenSize = jsonTokensArray.length(); i < tokenSize; i++) {
                final JSONObject jsonToken = jsonTokensArray.getJSONObject(i);
                final JSONObject jsonObjectHintTable = jsonToken.getJSONObject(JSON_PROPERTY_HINT_TABLE);

                final OverviewTranslationHolder overviewTranslationHolder = new OverviewTranslationHolder();
                overviewTranslationHolder.setId(this.overviewId);

                this.extractSelectedHeader(jsonObjectHintTable, overviewTranslationHolder);
                this.extractRowElements(jsonObjectHintTable, overviewTranslationHolder);

                overviewTranslationHolderList.add(overviewTranslationHolder);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new HttpAsyncResults(request.getHttpStatus(), overviewTranslationHolderList);
    }

    /**
    * リクエスト時のURLクエリパラメータを生成する処理を定義したメソッドです。
    *
    * @param params クエリパラメータの値が格納された文字列配列。
    * @return URLクエリパラメータが格納された連想配列。
    */
    private Map<String, String> createQueryParameter(String[] params) {

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

    /**
    * 翻訳情報取得APIのレスポンス情報から"headers"に含まれる翻訳情報を抽出し、
    * 抽出した情報をデータクラスへ格納する処理を定義したメソッドです。
    *
    * @param jsonObjectHintTable "hint_table"要素まで抽出したJSONオブジェクト。
    * @param overviewTranslationHolder 翻訳情報を格納するデータクラス。
    * @throws JSONException JSONオブジェクトに対する操作で例外が発生した場合に発生します。
    */
    private void extractSelectedHeader(final JSONObject jsonObjectHintTable, final OverviewTranslationHolder overviewTranslationHolder) throws JSONException {

        if (jsonObjectHintTable.has(JSON_PROPERTY_HEADERS)) {
            final JSONArray jsonHeadersArray = jsonObjectHintTable.getJSONArray(JSON_PROPERTY_HEADERS);

            if (jsonHeadersArray.length() > 0) {
                for (int i = 0, rowSize = jsonHeadersArray.length(); i < rowSize; i++) {
                    final JSONObject jsonObjectHeader = jsonHeadersArray.getJSONObject(i);

                    if (jsonObjectHeader.getBoolean(JSON_PROPERTY_HEADER_SELECTED)) {
                        overviewTranslationHolder.setHeader(jsonObjectHeader.getString(JSON_PROPERTY_HEADER_TOKEN));
                    }
                }
            }
        }
    }

    /**
    * 翻訳情報取得APIのレスポンス情報から"rows"に含まれる翻訳情報を抽出し、
    * 抽出した情報をデータクラスへ格納する処理を定義したメソッドです。
    *
    * @param jsonObjectHintTable "hint_table"要素まで抽出したJSONオブジェクト。
    * @param overviewTranslationHolder 翻訳情報を格納するデータクラス。
    * @see #extractCellElements
    * @throws JSONException JSONオブジェクトに対する操作で例外が発生した場合に発生します。
    */
    private void extractRowElements(final JSONObject jsonObjectHintTable, final OverviewTranslationHolder overviewTranslationHolder) throws JSONException {

        final JSONArray jsonArrayRows = jsonObjectHintTable.optJSONArray(JSON_PROPERTY_ROWS);

        if (Objects.requireNonNull(jsonArrayRows).length() > 0) {
            final List<String> hintsList = new ArrayList<>();

            for (int i = 0, rowSize = Objects.requireNonNull(jsonArrayRows).length(); i < rowSize; i++) {
                final JSONObject jsonObjectRow = jsonArrayRows.getJSONObject(i);
                this.extractCellElements(jsonObjectRow, hintsList);
            }

            overviewTranslationHolder.setHints(hintsList);
        }
    }

    /**
    * 翻訳情報取得APIのレスポンス情報から"cells"に含まれるヒント情報を抽出し、
    * 抽出したヒント情報をリストへ格納する処理を定義したメソッドです。
    * ヒント情報が存在しない場合は初期化されたリストが設定されます。
    *
    * @param jsonObjectRow "rows"要素まで抽出したJSONオブジェクト。
    * @param hintsList ヒント情報を格納するリスト。
    * @see #extractRowElements
    * @throws JSONException JSONオブジェクトに対する操作で例外が発生した場合に発生します。
    */
    private void extractCellElements(final JSONObject jsonObjectRow, final List<String> hintsList) throws JSONException {

        if (jsonObjectRow.length() > 0) {
            final JSONArray jsonArrayCells = jsonObjectRow.getJSONArray(JSON_PROPERTY_CELLS);

            for (int i = 0, cellSize = Objects.requireNonNull(jsonArrayCells).length(); i < cellSize; i++) {
                final JSONObject jsonObjectCell = jsonArrayCells.getJSONObject(i);

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
    }
}
