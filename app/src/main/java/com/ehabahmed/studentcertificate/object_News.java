package com.ehabahmed.studentcertificate;

public class object_News {
    int news_id;
    String news_text;
    String news_detals;
    String news_ivname;
    String news_type;
    String data_time;

    public object_News(int news_id, String news_text,String news_detals, String news_ivname, String news_type,String data_time) {
        this.news_id = news_id;
        this.news_text = news_text;
        this.news_detals=news_detals;
        this.news_ivname = news_ivname;
        this.news_type = news_type;
        this.data_time=data_time;
    }
}
