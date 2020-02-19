package com.ehabahmed.studentcertificate;

import com.google.gson.annotations.SerializedName;

public class DataGroup {
    @SerializedName("group_id")
    String GroupId;
    @SerializedName("group_name")
    String GroupName;
    @SerializedName("group_photo")
    String GroupPhoto;
    @SerializedName("group_info")
    String GroupInfo;
    @SerializedName("type")
    String type;
    @SerializedName("NumMember")
    String NumMember;

    public String getNumMember() {
        return NumMember;
    }

    public String getType() {
        return type;
    }

    public String getGroupInfo() {
        return GroupInfo;
    }

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
