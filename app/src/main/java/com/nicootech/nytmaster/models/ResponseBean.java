package com.nicootech.nytmaster.models;

import java.util.List;

public class ResponseBean {
    private List<Docs> docs;
    private Meta meta;

    public ResponseBean(List<Docs> docs, Meta meta) {
        this.docs = docs;
        this.meta = meta;
    }

    public ResponseBean() {
    }



    public List<Docs> getDocs() {
        return docs;
    }

    public void setDocs(List<Docs> docs) {
        this.docs = docs;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }


}

