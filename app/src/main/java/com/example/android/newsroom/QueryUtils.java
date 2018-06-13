package com.example.android.newsroom;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class QueryUtils {
    public QueryUtils() {
    }

    public static List<News> fetchNews(String requestUrl){
        URL url = buildUrl(requestUrl);

        String jsonResponse = null;

        try{
            jsonResponse = getResponseFromHttpURL(url);
        } catch (IOException io){
            io.printStackTrace();
        }
        List<News> news = extractNewsFromJson(jsonResponse);
        return news;
    }

    private static List<News> extractNewsFromJson(String jsonResponse) {
        ArrayList<News> newsList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);

            JSONObject response = jsonObject.getJSONObject("response");

            JSONArray results = jsonObject.getJSONArray("results");

            for(int i=0; i<results.length(); i++){


                JSONObject fields = results.getJSONObject(i).getJSONObject("fields");

                String headline = fields.getString("headline");

                String imageUrlUrl = fields.getString("thumbnail");

                String publicationDate = results.getJSONObject(i).getString("webPublicationDate");

                String url = results.getJSONObject(i).getString("webUrl");

                News news = new News(headline, publicationDate, url);

                newsList.add(news);
            }


        } catch (JSONException e){
            e.printStackTrace();
        }
        return newsList;
    }

    private static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection urlConnection = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream is = urlConnection.getInputStream();
            Scanner sc = new Scanner(is);

            sc.useDelimiter("\\A");
            boolean hasInput = sc.hasNext();
            if(hasInput){
                return sc.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static URL buildUrl(String requestUrl) {
        URL url = null;
        try{
            url = new URL(requestUrl);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
