package com.angryzpua.testtask;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtil {

    private static BreedCard extractBreedListFromJson(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return null;
        }
        try{
            JSONArray baseArray = new JSONArray(uri);
            JSONObject element = (JSONObject) baseArray.get(0);
            String picture = element.optString("url");
            JSONArray breeds = element.getJSONArray("breeds");
            JSONObject breedElement = (JSONObject) breeds.get(0);
            String country = breedElement.optString("origin");
            String temperament = breedElement.optString("temperament");
            String catBreed = breedElement.optString("name");
            String lifetime = breedElement.optString("life_span");
            String wikiurl = breedElement.optString("wikipedia_url");
            String description = breedElement.optString("description");
            JSONObject full_weight = breedElement.getJSONObject("weight");
            String weight = full_weight.optString("metric");
            return new BreedCard(catBreed, weight, lifetime, country, temperament, wikiurl, description, picture);
        }
        catch (JSONException e) {
            Log.e("Error card from json", e.getMessage());
        }
        return null;
    }

    private static List<CatListItem> extractCatListFromJson(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return null;
        }
        List<CatListItem> catListItems = new ArrayList<>();
        try {
            JSONArray baseJsonResponse = new JSONArray(uri);
            for(int i=0; i<baseJsonResponse.length(); i++){
                JSONObject arrayElement = baseJsonResponse.getJSONObject(i);
                String id = arrayElement.optString("id");
                String breed = arrayElement.optString("name");
                catListItems.add(new CatListItem(id, breed));
            }
        }
        catch (JSONException e) {
            Log.e("Error list from json", e.getMessage());
        }
        return catListItems;
    }

    private static String readFromStream(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStreamReader != null) {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStreamReader inputStreamReader = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                jsonResponse = readFromStream(inputStreamReader);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
        }
        return jsonResponse;
    }

    public static List<CatListItem> fetchCatList(URL requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
        }
        List<CatListItem> weatherUnits = extractCatListFromJson(jsonResponse);
        return weatherUnits;
    }

    public static BreedCard fetchBreedCard(URL requestUrl) {
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
        }
        BreedCard breedCard = extractBreedListFromJson(jsonResponse);
        return breedCard;
    }
}
