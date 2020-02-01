package com.ehabahmed.studentcertificate;

import com.google.gson.annotations.SerializedName;

public class member {
      @SerializedName("student_info_id")
    String PersonalId;
    @SerializedName("student_info_photo")
    String PersonalPhoto;
    @SerializedName("student_info_name")
    String PersonName;
    @SerializedName("department_id")
    String Personaldepartment;
    @SerializedName("student_info_level")
    String Personallevel;

    public member(String personalId, String personalPhoto, String personName, String personaldepartment, String personallevel) {
        PersonalId = personalId;
        PersonalPhoto = personalPhoto;
        PersonName = personName;
        Personaldepartment = personaldepartment;
        Personallevel = personallevel;
    }








}
