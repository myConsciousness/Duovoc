package android.app.java.com.duovoc.framework.communicate;

import android.app.java.com.duovoc.framework.communicate.property.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

final public class Request {

    private static final int CONNECTION_TIMEOUT = 100000;
    private static final int READ_TIMEOUT = 100000;

    private static final String DEFAULT_CHARSET = "utf-8";

    private String response;

    public boolean send(final String url, final RequestMethod method) {

        return this.send(url, method, new HashMap<>());
    }

    public boolean send(
            final String url,
            final RequestMethod method,
            final Map<String, String> queryMap) {

        final String requestUrl = this.makeRequestUrl(url, queryMap);

        try {

            final HttpURLConnection connection = this.connect(requestUrl, method);

            if (this.isValidStatusCode(connection)) {
                final String response = this.getStringResponse(connection);
                this.setResponse(response);
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private String makeRequestUrl(final String url, final Map<String, String> queryMap) {

        StringBuilder requestUrl = new StringBuilder();
        requestUrl.append(url);

        if (queryMap.isEmpty()) {
            return requestUrl.toString();
        }

        requestUrl.append("?");

        for (Map.Entry<String, String> entrySet : queryMap.entrySet()) {
            requestUrl.append(entrySet.getKey())
                    .append("=")
                    .append(entrySet.getValue())
                    .append("&");
        }

        // 通信処理上問題はないが末尾の余分な"&"を除去
        requestUrl.setLength(requestUrl.length() - 1);

        return requestUrl.toString();
    }

    private HttpURLConnection connect(
            final String requestUrl,
            final RequestMethod method) throws IOException {

        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method.getMethodName());
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setDoOutput(false);
        connection.setDoInput(true);
        connection.connect();

        return connection;
    }

    private boolean isValidStatusCode(final HttpURLConnection con) throws IOException {
        return con.getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    private String getStringResponse(final HttpURLConnection conn) throws IOException {

        StringBuilder response = new StringBuilder();

        final InputStream in = conn.getInputStream();

        final String encoding = conn.getContentEncoding();
        final InputStreamReader inReader = new InputStreamReader(in, encoding != null ? encoding : DEFAULT_CHARSET);
        final BufferedReader bufferedReader = new BufferedReader(inReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        bufferedReader.close();
        inReader.close();
        in.close();
        conn.disconnect();

        return response.toString();
    }

    public String getResponse() {
        return this.response;
    }

    private void setResponse(final String response) {
        this.response = response;
    }
}
