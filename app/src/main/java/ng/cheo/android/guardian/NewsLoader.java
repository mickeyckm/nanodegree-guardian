package ng.cheo.android.guardian;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by mickey on 6/11/16.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<Article>> {

    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        ArrayList<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}
