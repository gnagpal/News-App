package com.example.android.newsroom;

public class News {
    private String headLines;

    private String StringimageUrl;

    private String date;

    private String url;

    public News(String headLines, String date, String url) {
        this.headLines = headLines;
   //     this.imageUrl = imageUrl;
        this.date = date;
        this.url = url;
    }

    public String getHeadLines() {
        return headLines;
    }

//  public String getImageUrl() {
//        return imageUrl;
//    }

    public String getDate() {
        return date;
    }

    public String getUrl(){
        return url;
    }
}
