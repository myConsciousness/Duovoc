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

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : HttpAsyncLogin.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * Duolingoのユーザ認証APIに対しリクエストを送信し、
 * JSONデータとして返却されたレスポンスから認証情報を抽出する処理を定義したクラスです。
 * <p>
 * 当該クラスのメソッドは非同期で実行され、
 * 当該クラスの非同期処理開始前および終了後の処理は呼び出し元のクラスで実装する必要があります。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public class HttpAsyncLogin extends AsyncTask<String, Void, HttpAsyncResults> implements IHttpAsync {

    /**
     * クラス名。
     */
    private static final String TAG = HttpAsyncLogin.class.getSimpleName();

    /**
     * 当該クラスのコンストラクタ。
     */
    protected HttpAsyncLogin() {
    }

    @Override
    protected HttpAsyncResults doInBackground(String... params) {

        final Request request = new Request();
        request.send(
                Api.Login.getUrl(),
                RequestMethod.Post,
                this.createQueryParameter(params));

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

    /**
     * リクエスト時のURLクエリパラメータを生成する処理を定義したメソッドです。
     *
     * @param params クエリパラメータの値が格納された文字列配列。
     * @return URLクエリパラメータが格納された連想配列。
     */
    private Map<String, String> createQueryParameter(String[] params) {

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
