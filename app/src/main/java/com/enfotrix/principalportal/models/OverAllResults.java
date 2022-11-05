package com.enfotrix.principalportal.models;

public class OverAllResults {
    private String className, sectionName, sectionID, totalStudents, passStudents, failStudents, percentage;

    public OverAllResults(String className, String sectionName, String sectionID, String totalStudents, String passStudents, String failStudents, String percentage) {
        this.className = className;
        this.sectionName = sectionName;
        this.sectionID = sectionID;
        this.totalStudents = totalStudents;
        this.passStudents = passStudents;
        this.failStudents = failStudents;
        this.percentage = percentage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getPassStudents() {
        return passStudents;
    }

    public void setPassStudents(String passStudents) {
        this.passStudents = passStudents;
    }

    public String getFailStudents() {
        return failStudents;
    }

    public void setFailStudents(String failStudents) {
        this.failStudents = failStudents;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
