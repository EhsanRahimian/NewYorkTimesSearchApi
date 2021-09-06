package com.nicootech.nytmaster.utils;

import android.util.Log;

import com.nicootech.nytmaster.models.Docs;

import java.util.List;

public class Testing {
    public static void print(List<Docs> list, String tag){
        for(Docs doc : list){
            Log.d(tag, "onChanged: "+doc.getHeadline().getMain());
        }
    }
}