package com.ehabahmed.studentcertificate;

import com.google.gson.annotations.SerializedName;

public class DataGroup {
    @SerializedName("group_id")
    String GroupId;
    @SerializedName("group_name")
    String GroupName;
    @SerializedName("group_photo")
    String GroupPhoto;

    public String getGroupId() {
        return GroupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getGroupPhoto() {
        return GroupPhoto;
    }
}
