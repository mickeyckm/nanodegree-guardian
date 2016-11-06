package ng.cheo.android.guardian;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Article>> {

    ArrayAdapter mAdapter;
    ListView mListView;
    ArrayList<Article> mArticles;

    public static final int ARTICLE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup adapter
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Setup listview
        mListView = (ListView) findViewById(R.id.news);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = mArticles.get(position);
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(article.getWebUrl()));
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, this);
    }

    @Override
    public Loader<ArrayList<Article>> onCreateLoader(int id, Bundle args) {
        String url = "http://content.guardianapis.com/search?q=debates&api-key=test";
        return new NewsLoader(MainActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Article>> loader, ArrayList<Article> articles) {
        mAdapter.clear();

        mArticles = articles;

        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
            mListView.setAdapter(mAdapter);
            mListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Article>> loader) {
        mAdapter.clear();
    }
}
