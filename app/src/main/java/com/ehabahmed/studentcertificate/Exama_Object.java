package com.ehabahmed.studentcertificate;

public class Exama_Object {
    String id;
    String name;
    String Image;
    String term;
    public Exama_Object(String id,String name, String image,String term) {
        this.id=id;
        this.name = name;
        Image = image;
        this.term=term;
    }

    public String getName() {
        return name;
    }
}
