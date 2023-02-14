package com.enfotrix.principalportal.models;

import java.util.Date;

public class AnnouncementModel {

    public AnnouncementModel() {

    }


    String Announcement;
    String Date;
    String Heading;

    public String getAnnouncement() {
        return Announcement;
    }

    public void setAnnouncement(String announcement) {
        Announcement = announcement;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public java.util.Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(java.util.Date timeStamp) {
        TimeStamp = timeStamp;
    }

    String ID;
    String Status;

    public AnnouncementModel(String announcement, String date, String heading, String ID, String status, java.util.Date timeStamp) {
        Announcement = announcement;
        Date = date;
        Heading = heading;
        this.ID = ID;
        Status = status;
        TimeStamp = timeStamp;
    }

    Date TimeStamp;
}
