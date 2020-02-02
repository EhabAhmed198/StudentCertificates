package com.ehabahmed.studentcertificate;

public final class SendForm {
    private String Sender_Name, Sender_id, Sender_image, title, message;

    public SendForm() {
    }

    public SendForm(String Sender_name, String Sender_image, String Sender_id, String message, String title) {
        this.message = message;
        this.Sender_id = Sender_id;
        this.title = title;
        this.Sender_Name = Sender_name;
        this.Sender_image = Sender_image;
    }

    public String getMessage() {
        return message;
    }

    public String getSender_id() {
        return Sender_id;
    }

    public String getSender_image() {
        return Sender_image;
    }

    public String getTitle() {
        return title;
    }

    public String getSender_Name() {
        return Sender_Name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender_id(String sender_id) {
        Sender_id = sender_id;
    }

    public void setSender_image(String sender_image) {
        Sender_image = sender_image;
    }

    public void setSender_Name(String sender_Name) {
        Sender_Name = sender_Name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
