package com.ehabahmed.studentcertificate;

import java.util.ArrayList;

public class MemberMakeLikes extends ArrayList<MemberMakeLikes> {

    String id;
    String name;
    String photo;
    String code;

    public MemberMakeLikes(String id, String name, String photo, String code) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCode() {
        return code;
    }
}
