package com.angryzpua.testtask;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.URL;

public class BreedCardLoader extends AsyncTaskLoader<BreedCard> {
    private URL url;

    public BreedCardLoader(@NonNull Context context, URL url){
        super(context);
        this.url = url;
    }

    @Nullable
    @Override
    public BreedCard loadInBackground() {
        BreedCard result = QueryUtil.fetchBreedCard(url);
        return result;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
