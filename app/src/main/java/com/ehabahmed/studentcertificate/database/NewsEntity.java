package com.ehabahmed.studentcertificate.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public final class NewsEntity {
    @PrimaryKey
    private int id;
    private String news_text;
    private String news_detals;
    private String news_ivname;
    private String news_type;
    private String data_time;

    public NewsEntity(int id, String text, String news_detals, String news_ivname, String news_type, String data_time) {
        this.news_text = text;
        this.news_detals = news_detals;
        this.news_ivname = news_ivname;
        this.news_type = news_type;
        this.data_time = data_time;
        this.id = id;
    }

    public NewsEntity() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNews_text(String news_text) {
        this.news_text = news_text;
    }

    public void setNews_detals(String news_detals) {
        this.news_detals = news_detals;
    }

    public void setNews_ivname(String news_ivname) {
        this.news_ivname = news_ivname;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public void setData_time(String data_time) {
        this.data_time = data_time;
    }

    public int getId() {
        return id;
    }

    public String getData_time() {
        return data_time;
    }

    public String getNews_detals() {
        return news_detals;
    }

    public String getNews_text() {
        return news_text;
    }

    public String getNews_ivname() {
        return news_ivname;
    }

    public String getNews_type() {
        return news_type;
    }
}
