package com.nicootech.nytmaster.models;

public class Meta {

    private int hits;
    private int offset;
    private int time;

    public Meta(int hits, int offset, int time) {
        this.hits = hits;
        this.offset = offset;
        this.time = time;
    }

    public Meta() {
    }

    public void setHits(int hits){
        this.hits = hits;
    }

    public int getHits(){
        return hits;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }


}

