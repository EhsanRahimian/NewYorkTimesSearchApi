package com.nicootech.nytmaster.requests;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nicootech.nytmaster.AppExecutors;
import com.nicootech.nytmaster.models.Docs;
import com.nicootech.nytmaster.requests.responses.ArticleSearchResponse;
import com.nicootech.nytmaster.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.nicootech.nytmaster.utils.Constants.NETWORK_TIMEOUT;

public class ArticleApiClient {

    private static final String TAG = "ArticleApiClient";

    private static ArticleApiClient instance;
    private MutableLiveData<List<Docs>> mDocs;
    private RetrieveArticlesRunnable mRetrieveArticlesRunnable;

    public static ArticleApiClient getInstance() {
        if(instance == null){
            instance = new ArticleApiClient();
        }
        return instance;
    }

    private ArticleApiClient() {
        mDocs = new MutableLiveData<>();
    }

    public LiveData<List<Docs>> getDocs() {
        return mDocs;
    }

    public void searchArticlesApi(String query, int pageNumber){
        if(mRetrieveArticlesRunnable != null){
            mRetrieveArticlesRunnable = null;
        }
        mRetrieveArticlesRunnable = new RetrieveArticlesRunnable(query,pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveArticlesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // let the user know its timed out.
                handler.cancel(true);
            }
        },NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveArticlesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveArticlesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getArticles(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){

                    List<Docs> list = new ArrayList<>(((ArticleSearchResponse)response.body()).getResponse().getDocs());
                    if(pageNumber == 0){
                        mDocs.postValue(list);
                    }
                    else{
                        List<Docs> current = mDocs.getValue();
                        current.addAll(list);
                        mDocs.postValue(current);
                    }

                }
                else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: "+ error );
                    mDocs.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mDocs.postValue(null);
            }

        }

        private Call<ArticleSearchResponse> getArticles(String query, int pageNumber){
            return ServiceGenerator.getNytApi().searchArticle(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the searchRequest");
            cancelRequest = true;
        }
    }
    public void cancelRequest(){
        if(mRetrieveArticlesRunnable != null){
            mRetrieveArticlesRunnable.cancelRequest();
        }
    }

}

