package com.nicootech.nytmaster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nicootech.nytmaster.models.Article;
import org.parceler.Parcels;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.MenuItemCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nicootech.nytmaster.util.Constants.ARTICLE_DATA_KEY;

public class ArticleActivity extends AppCompatActivity {



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.wvArticle)
    WebView mwvArticle;

    private ShareActionProvider provider;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mwvArticle.getSettings().setLoadsImagesAutomatically(true);
        mwvArticle.getSettings().setJavaScriptEnabled(true);
        mwvArticle.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mwvArticle.setWebViewClient(new ArticleBrowser());

        Article article = Parcels.unwrap(getIntent().getParcelableExtra(ARTICLE_DATA_KEY));

        mwvArticle.loadUrl(article.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_article, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(provider != null){
        setShareActionIntent();
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, mwvArticle.getUrl());
        provider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ArticleBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
