package com.enfotrix.principalportal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentResult implements Parcelable {
    private String studentID, sectionID, physics, pakStudy, urdu, islamiyat, math, biology, scienceG, computer, english, chemistry, status, studentName, fatherName, totalMarks, obtainedMarks;

    public StudentResult() {
        this.studentID = null;
        this.sectionID = null;
        this.physics = null;
        this.pakStudy = null;
        this.urdu = null;
        this.islamiyat = null;
        this.math = null;
        this.biology = null;
        this.scienceG = null;
        this.computer = null;
        this.english = null;
        this.chemistry = null;
        this.status = null;
    }

    protected StudentResult(Parcel in) {
        studentID = in.readString();
        sectionID = in.readString();
        physics = in.readString();
        pakStudy = in.readString();
        urdu = in.readString();
        islamiyat = in.readString();
        math = in.readString();
        biology = in.readString();
        scienceG = in.readString();
        computer = in.readString();
        english = in.readString();
        chemistry = in.readString();
        status = in.readString();
    }

    public static final Creator<StudentResult> CREATOR = new Creator<StudentResult>() {
        @Override
        public StudentResult createFromParcel(Parcel in) {
            return new StudentResult(in);
        }

        @Override
        public StudentResult[] newArray(int size) {
            return new StudentResult[size];
        }
    };

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getPhysics() {
        return physics;
    }

    public void setPhysics(String physics) {
        this.physics = physics;
    }

    public String getPakStudy() {
        return pakStudy;
    }

    public void setPakStudy(String pakStudy) {
        this.pakStudy = pakStudy;
    }

    public String getUrdu() {
        return urdu;
    }

    public void setUrdu(String urdu) {
        this.urdu = urdu;
    }

    public String getIslamiyat() {
        return islamiyat;
    }

    public void setIslamiyat(String islamiyat) {
        this.islamiyat = islamiyat;
    }

    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }

    public String getBiology() {
        return biology;
    }

    public void setBiology(String biology) {
        this.biology = biology;
    }

    public String getScienceG() {
        return scienceG;
    }

    public void setScienceG(String scienceG) {
        this.scienceG = scienceG;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(studentID);
        parcel.writeString(sectionID);
        parcel.writeString(physics);
        parcel.writeString(pakStudy);
        parcel.writeString(urdu);
        parcel.writeString(islamiyat);
        parcel.writeString(math);
        parcel.writeString(biology);
        parcel.writeString(scienceG);
        parcel.writeString(computer);
        parcel.writeString(english);
        parcel.writeString(chemistry);
        parcel.writeString(status);


    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(String obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }
}
