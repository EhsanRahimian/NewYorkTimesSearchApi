package com.nicootech.nytmaster.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nicootech.nytmaster.models.ResponseBean;

public class ArticleSearchResponse {


    @SerializedName("status")
    @Expose()
    private String status;

    @SerializedName("copyright")
    @Expose()
    private String copyright;

    @SerializedName("response")
    @Expose()
    private ResponseBean response;

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public ResponseBean getResponse() {
        return response;
    }


}

