package com.enfotrix.principalportal.models;

import com.google.firebase.Timestamp;

public class NotificationModel {

    private String id;
    private String data;
    private com.google.firebase.Timestamp date;
    private String heading;
    private String studentid;
    private String status;


    public NotificationModel(String data, Timestamp date, String heading, String studentID, String status) {
        this.data = data;
        this.date = date;
        this.heading = heading;
        this.studentid = studentID;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public com.google.firebase.Timestamp getDate() {
        return date;
    }

    public void setDate(com.google.firebase.Timestamp date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
