package com.ehabahmed.studentcertificate;

import com.google.gson.annotations.SerializedName;

public class objectPostGroup {

    @SerializedName("post_id")
    String group_Id;
    @SerializedName("post_text")
    String group_text;
    @SerializedName("post_details")
    String group_details;
    @SerializedName("post_ivname")
    String group_ivname;
    @SerializedName("post_type")
    String group_type;
    @SerializedName("post_time")
    String group_time;
    @SerializedName("student_info_id")
    String publisherId;
    @SerializedName("student_info_name")
    String publisherName;
    @SerializedName("student_info_photo")
    String publisherPhoto;

    public objectPostGroup(String group_Id, String group_text, String group_details, String group_ivname, String group_type, String group_time, String publisherId, String publisherName, String publisherPhoto) {
        this.group_Id = group_Id;
        this.group_text = group_text;
        this.group_details = group_details;
        this.group_ivname = group_ivname;
        this.group_type = group_type;
        this.group_time = group_time;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.publisherPhoto = publisherPhoto;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getPublisherPhoto() {
        return publisherPhoto;
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
