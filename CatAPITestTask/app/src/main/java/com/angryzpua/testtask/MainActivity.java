package com.angryzpua.testtask;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CatListItem>>{
    private static final int LOADER_ID = 1;
    private TextView emptyStateTextView;
    private CatListAdapter catListAdapter;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView catListView = findViewById(R.id.list);
        emptyStateTextView = findViewById(R.id.empty_view);
        catListView.setEmptyView(emptyStateTextView);
        catListAdapter = new CatListAdapter(context, new ArrayList<CatListItem>());
        catListView.setAdapter(catListAdapter);
        catListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                CatListItem selectedCatListItem = catListAdapter.getItem(position);
                Intent intent = new Intent(context, BreedCardActivity.class);
                intent.putExtra("id", selectedCatListItem.getID());
                startActivity(intent);
            }
        });
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        }
        else{
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<CatListItem>> onCreateLoader(int i, Bundle bundle) {
        URL url = null;
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.thecatapi.com")
                    .appendPath("v1")
                    .appendPath("breeds")
                    .appendQueryParameter("api_key", "530afa65-9e01-4f53-9a46-f19b8d59fb3d");
            url = new URL(builder.toString());
        }
        catch(MalformedURLException e){
            Log.e("URL convert error", e.getMessage());
        }
        return new CatListLoader(context, url);
    }

    @Override
    public void onLoadFinished(Loader<List<CatListItem>> loader, List<CatListItem> catListItems) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_information);
        catListAdapter.clear();
        if (catListItems != null && !catListItems.isEmpty()) {
            catListAdapter.addAll(catListItems);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<CatListItem>> loader) {
        catListAdapter.clear();
    }
}
