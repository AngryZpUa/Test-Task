package com.angryzpua.testtask;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class BreedCardActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<BreedCard>{
    TextView empty;
    View info;
    private static final int LOADER_ID = 2;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.breed_card);
        id = getIntent().getStringExtra("id");
        empty = findViewById(R.id.empty_breed_card);
        info = findViewById(R.id.breedCardContent);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        }
        else{
            empty.setText(R.string.no_internet_connection);
            empty.setVisibility(View.VISIBLE);

        }
        info.setVisibility(View.GONE);
    }

    @Override
    public Loader<BreedCard> onCreateLoader(int i, Bundle bundle) {
        URL url = null;
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.thecatapi.com")
                    .appendPath("v1")
                    .appendPath("images")
                    .appendPath("search")
                    .appendQueryParameter("breed_ids", id)
                    .appendQueryParameter("api_key", "530afa65-9e01-4f53-9a46-f19b8d59fb3d");
            url = new URL(builder.toString());
        }
        catch(MalformedURLException e){
            Log.e("URL convert error", e.getMessage());
        }
        return new BreedCardLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<BreedCard> loader, BreedCard breedCard) {
        if(breedCard != null){
            empty.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);
            TextView name = findViewById(R.id.cat_breed);
            name.setText(breedCard.getBreed());
            TextView weight = findViewById(R.id.cat_weight);
            weight.setText("Weight in kg: " + breedCard.getWeight());
            TextView lifeTime = findViewById(R.id.cat_lifetime);
            lifeTime.setText("Lifetime: " + breedCard.getLifetime());
            TextView wiki = findViewById(R.id.cat_wiki_url);
            wiki.setText(breedCard.getWikiUrl());
            TextView country = findViewById(R.id.cat_country);
            country.setText("Country: " + breedCard.getCountry());
            TextView temperament = findViewById(R.id.cat_temperament);
            temperament.setText("Temperament:" + breedCard.getTemperament());
            TextView description = findViewById(R.id.cat_description);
            description.setText("Description: " + breedCard.getDescription());
            ImageView picture = findViewById(R.id.cat_picture);
            Glide
                    .with(this)
                    .load(breedCard.getPicture())
                    .into(picture);
        }
        else{
            empty.setVisibility(View.VISIBLE);
            empty.setText(R.string.no_information);
            info.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<BreedCard> loader) {}
}
