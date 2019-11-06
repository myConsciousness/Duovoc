package android.app.java.com.duovoc.communicate;

import android.app.java.com.duovoc.communicate.property.Api;
import android.app.java.com.duovoc.communicate.property.OverViewJsonProperties;
import android.app.java.com.duovoc.framework.IHttpAsync;
import android.app.java.com.duovoc.framework.communicate.Request;
import android.app.java.com.duovoc.framework.communicate.holder.HttpAsyncResults;
import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;
import android.app.java.com.duovoc.model.holder.OverviewHolder;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : HttpAsyncOverview.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * Duolingoの概要情報取得APIに対しリクエストを送信し、
 * JSONデータとして返却されたレスポンスから概要情報を抽出する処理を定義したクラスです。
 * <p>
 * 当該クラスのメソッドは非同期で実行され、
 * 当該クラスの非同期処理開始前および終了後の処理は呼び出し元のクラスで実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public class HttpAsyncOverview extends AsyncTask<Void, Void, HttpAsyncResults> implements IHttpAsync {

    /**
     * クラス名。
     */
    private static final String TAG = HttpAsyncOverview.class.getName();

    /**
     * JSONデータのlearning_languageキーを表す定数値です。
     */
    private static final String JSON_PROPERTY_LEARNING_LANGUAGE = "learning_language";

    /**
     * JSONデータのlanguage_stringキーを表す定数値です。
     */
    private static final String JSON_PROPERTY_LANGUAGE_STRING = "language_string";

    /**
     * JSONデータのfrom_languageキーを表す定数値です。
     */
    private static final String JSON_PROPERTY_FROM_LANGUAGE = "from_language";

    /**
     * JSONデータのvocab_overviewキーを表す定数値です。
     */
    private static final String JSON_PROPERTY_VOCAB_OVERVIEW = "vocab_overview";

    /**
     * ユーザに紐づく識別ID。
     */
    private final String userId;

    /**
     * 概要情報から取得した学習言語区分。
     */
    private String learningLanguage = "";

    /**
     * 当該クラスのコンストラクタです。
     *
     * @param userId ユーザに紐づく識別ID。
     */
    protected HttpAsyncOverview(final String userId) {
        this.userId = userId;
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

            // 言語切替時に概要情報が空の場合でも初回時起動ダイアログで学習言語を表示できるようにする。
            this.setLearningLanguage(learningLanguage);

            final JSONArray jsonArrayOverview = jsonObject.getJSONArray(JSON_PROPERTY_VOCAB_OVERVIEW);

            for (int i = 0, rowSize = Objects.requireNonNull(jsonArrayOverview).length(); i < rowSize; i++) {

                final OverviewHolder overviewHolder = new OverviewHolder();
                final OverViewJsonProperties[] overViewJsonProperties = OverViewJsonProperties.values();

                for (OverViewJsonProperties property : overViewJsonProperties) {
                    property.setOverviewHolder(jsonArrayOverview.getJSONObject(i), overviewHolder);
                }

                overviewHolder.setUserId(this.userId);
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

    /**
     * 概要情報から取得した学習言語区分を返却するGetterメソッドです。
     *
     * @return 概要情報から取得した学習言語区分。
     */
    protected String getLearningLanguage() {
        return this.learningLanguage;
    }

    /**
     * 概要情報から取得した学習言語区分を設定するSetterメソッドです。
     *
     * @param learningLanguage 概要情報から取得した学習言語。
     */
    private void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }
}
