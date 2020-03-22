package com.ehabahmed.studentcertificate;

public class comment {
public comment(){

}
     String comment_user,comment_text,code,comment_photo,data_time;

    public comment(String comment_user, String comment_text, String code,String comment_photo, String data_time) {
        this.comment_user = comment_user;
        this.comment_text = comment_text;
        this.code = code;
        this.comment_photo=comment_photo;
        this.data_time = data_time;
    }

    public String getCode() {
        return code;
    }

    public String getComment_photo() {
        return comment_photo;
    }

    public String getComment_text() {
        return comment_text;
    }

    public String getComment_user() {
        return comment_user;
    }

    public String getData_time() {
        return data_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setComment_photo(String comment_photo) {
        this.comment_photo = comment_photo;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public void setComment_user(String comment_user) {
        this.comment_user = comment_user;
    }

    public void setData_time(String data_time) {
        this.data_time = data_time;
    }
}
