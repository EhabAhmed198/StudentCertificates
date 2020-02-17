package com.ehabahmed.studentcertificate;

public class Articles_Object {

  public   String  id;
    String title;
    String photo;
    String deatls;
    String date;

    public Articles_Object(String id, String title, String photo, String deatls, String date) {
        this.id = id;
        this.title = title;
        this.photo = photo;
        this.deatls = deatls;
        this.date = date;

    }
    public String getName() {
        return title;
    }
}
