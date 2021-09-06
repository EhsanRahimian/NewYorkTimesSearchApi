package com.nicootech.nytmaster.models;

public class ArticleResponse {

    private String status;
    private String copyright;
    private ResponseBean response;

    public ArticleResponse(String status, String copyright,ResponseBean response) {
        this.status = status;
        this.copyright = copyright;
        this.response = response;
    }

    public ArticleResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }


}
