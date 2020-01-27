package com.ehabahmed.studentcertificate;

public class object_post_group{

    String id;
    String text;
    String pvf_name;
    String type;
    String group_id;
String data_time;


    public object_post_group(String id, String text, String pvf_name, String type, String group_id,String data_time) {
        this.id = id;
        this.text = text;
        this.pvf_name = pvf_name;
        this.type = type;
        this.group_id = group_id;
       this.data_time=data_time;
    }
}