package com.ehabahmed.studentcertificate;

import android.app.Application;

public class Info extends Application {
    String id,name,pass,photo,level,department,group_name,ghange_level,SMobile="NF",SEmail="NF";
    String doctor_id,doctor_name,doctor_photo,doctor_password,doctor_mobile,doctor_email;
String type;

    public String getSMobile() {
        return SMobile;
    }

    public void setSMobile(String SMobile) {
        this.SMobile = SMobile;
    }

    public String getSEmail() {
        return SEmail;
    }

    public void setSEmail(String SEmail) {
        this.SEmail = SEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getDoctor_mobile() {
        return doctor_mobile;
    }

    public void setDoctor_mobile(String doctor_mobile) {
        this.doctor_mobile = doctor_mobile;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_photo() {
        return doctor_photo;
    }

    public void setDoctor_photo(String doctor_photo) {
        this.doctor_photo = doctor_photo;
    }

    public String getDoctor_password() {
        return doctor_password;
    }

    public void setDoctor_password(String doctor_password) {
        this.doctor_password = doctor_password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGhange_level() {
        return ghange_level;
    }

    public void setGhange_level(String ghange_level) {
        this.ghange_level = ghange_level;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
