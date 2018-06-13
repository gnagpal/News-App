package com.example.android.newsroom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

class NewsAdapter extends ArrayAdapter<News>{


    public NewsAdapter(@NonNull Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currNews = getItem(position);

//        ImageView mImageView = listItemView.findViewById(R.id.news_image);
//        String imageURL = "https://media.guim.co.uk/a272fe03d0d51f098c636aea60eb584a71a84ce1/0_142_2250_1350/500.jpg";
//        mImageView.setImageBitmap(getImageBitmap(currNews.getImageUrl()));

        TextView mHeadlinesView = listItemView.findViewById(R.id.news_headlines);
        mHeadlinesView.setText(currNews.getHeadLines());

        TextView dateView = listItemView.findViewById(R.id.news_date);
        Date date = new Date(currNews.getDate());
        String formattedDate = formateDate(date);
        dateView.setText(formattedDate);

        return listItemView;
    }

    private String formateDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MMM DD, yyyy");
        return format.format(date);
    }

//    private Bitmap getImageBitmap(String imageUrl) {
//        Bitmap bm = null;
//        try{
//            URL url = new URL(imageUrl);
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
//            bis.close();
//            is.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        return bm;
//    }
}
