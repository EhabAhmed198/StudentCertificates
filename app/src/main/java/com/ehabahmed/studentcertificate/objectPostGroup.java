package com.ehabahmed.studentcertificate;

public class objectPostGroup {


    String group_Id;
    String group_text;
    String group_details;
    String group_ivname;
    String group_type;
    String group_time;

    public objectPostGroup(String group_Id, String group_text, String group_details, String group_ivname, String group_type, String group_time) {
        this.group_Id = group_Id;
        this.group_text = group_text;
        this.group_details = group_details;
        this.group_ivname = group_ivname;
        this.group_type = group_type;
        this.group_time = group_time;
    }

    public String getGroup_Id() {
        return group_Id;
    }

    public String getGroup_text() {
        return group_text;
    }

    public String getGroup_details() {
        return group_details;
    }

    public String getGroup_ivname() {
        return group_ivname;
    }

    public String getGroup_type() {
        return group_type;
    }

    public String getGroup_time() {
        return group_time;
    }
}
