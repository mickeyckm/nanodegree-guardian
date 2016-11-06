package ng.cheo.android.guardian;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by mickey on 6/11/16.
 */

public class QueryUtils {

    public static final String LOG_TAG = "QueryUtils";

    public static ArrayList<Article> fetchArticleData(String url) {
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(createUrl(url));
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error with connecting: ", e);
        }

        ArrayList<Article> articles = extractFromJSON(jsonResponse);
        return articles;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error with creating URL: ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the search JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Article> extractFromJSON(String jsonResponse) {

        ArrayList<Article> articles = new ArrayList<Article>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // If there are results in the features array
            if (resultsArray.length() > 0) {

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject itemObject = resultsArray.getJSONObject(i);

                    // Data
                    String title = itemObject.getString("webTitle");
                    String section = itemObject.getString("sectionName");
                    String webUrl = itemObject.getString("webUrl");

                    // Article
                    Article article = new Article(title, section);
                    article.setWebUrl(webUrl);
                    articles.add(article);
                }

                return articles;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the search JSON results", e);
        }

        return null;
    }
    
}
