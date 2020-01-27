package com.ehabahmed.studentcertificate;

public class Courses_object {
    String id;
    String image;
    String name;

    public Courses_object(String id,String name, String image) {
        this.id=id;
        this.name = name;
        this.image = image;

    }

    public String getName() {
        return name;
    }


}
