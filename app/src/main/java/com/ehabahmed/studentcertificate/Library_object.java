package com.ehabahmed.studentcertificate;

public class Library_object {
    String id;
    String name;
    String photo;
    String detals;
    String link;

    public Library_object(String id, String name, String photo,String detals ,String link) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.detals=detals;
        this.link = link;
    }

    public String getName() {
        return name;
    }
}
