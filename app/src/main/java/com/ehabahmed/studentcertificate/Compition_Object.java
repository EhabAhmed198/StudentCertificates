package com.ehabahmed.studentcertificate;

public class Compition_Object {
    String id;
    String name;
    String image;
    String details;

    public Compition_Object(String id,String name, String image,String details) {
        this.id=id;
        this.name = name;
        this.image = image;
        this.details=details;

    }

    public String getName() {
        return name;
    }
}
