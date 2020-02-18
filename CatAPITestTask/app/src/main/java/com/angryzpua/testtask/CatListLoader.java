package com.angryzpua.testtask;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.net.URL;
import java.util.List;

public class CatListLoader extends AsyncTaskLoader<List<CatListItem>> {
    private URL url;

    public CatListLoader(@NonNull Context context, URL url){
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public List<CatListItem> loadInBackground() {
        List<CatListItem> result = QueryUtil.fetchCatList(url);
        return result;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
