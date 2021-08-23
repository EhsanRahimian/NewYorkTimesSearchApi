package com.nicootech.nytmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nicootech.nytmaster.adapters.ArticlesAdapter;
import com.nicootech.nytmaster.helper.Helper;
import com.nicootech.nytmaster.listener.EndlessRecyclerViewScrollListener;
import com.nicootech.nytmaster.models.Article;
import com.nicootech.nytmaster.models.ArticlesResponse;
import com.nicootech.nytmaster.network.NYTimesClient;
import com.nicootech.nytmaster.network.NYTimesService;
import com.nicootech.nytmaster.util.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.nicootech.nytmaster.util.Constants.TOP_QUERY;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    public static final NYTimesService NEW_YORK_TIMES_SERVICE = NYTimesClient.getInstance()
            .getNYTimesService();

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvArticles)
    RecyclerView mrvArticles;

    private List<Article> mArticles;
    private ArticlesAdapter mArticlesAdapter;
    private String mQueryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mArticles = new ArrayList<>();
        mArticlesAdapter = new ArticlesAdapter(this, mArticles);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mrvArticles.setAdapter(mArticlesAdapter);
        mrvArticles.setLayoutManager(gridLayoutManager);

        mrvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchArticles(page);
            }
        });

        mQueryText = TOP_QUERY;
        fetchArticles(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Helper.isNetworkAvailable(this)) {
            Helper.showSnackBarForInternetConnection(mrvArticles, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQueryText = query;
                mArticlesAdapter.clear();
                fetchArticles(0);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    private void fetchArticles(int page) {
        if (Helper.isNetworkAvailable(this)) {

            if (!mQueryText.isEmpty()) {

                Call<ArticlesResponse> articlesCall = NEW_YORK_TIMES_SERVICE
                        .getArticles(page, mQueryText);

                articlesCall.enqueue(new Callback<ArticlesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ArticlesResponse> call, @NonNull Response<ArticlesResponse> response) {
                        if (response != null && response.body() != null) {
                            mArticles.addAll(response.body().getResponse().getArticles());
                            mArticlesAdapter.notifyDataSetChanged();
                        } else {
                            if(response.code() == 429) {
                                Helper.showSnackBar(mrvArticles, SearchActivity.this, R.string.request_failed_too_many_requests_text);
                            } else {
                                Helper.showSnackBar(mrvArticles, SearchActivity.this, R.string.request_failed_text);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArticlesResponse> call, @NonNull Throwable t) {
                        Helper.showSnackBar(mrvArticles, SearchActivity.this, R.string.request_failed_text);
                    }
                });
            }
        } else {
            Helper.showSnackBarForInternetConnection(mrvArticles, this);
        }
    }

}