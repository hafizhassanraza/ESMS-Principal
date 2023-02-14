package com.enfotrix.principalportal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable{
    public static String studentName,fatherName,className,sectionName,status,fatherPhoneNumber,studentId;

    public Student(){

    }

    public Student(String studentName, String fatherName, String className, String sectionName, String status, String fatherPhoneNumber, String studentId) {
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.className = className;
        this.sectionName = sectionName;
        this.status = status;
        this.fatherPhoneNumber = fatherPhoneNumber;
        this.studentId = studentId;
    }

    protected Student(Parcel in) {
        studentName = in.readString();
        fatherName = in.readString();
        className = in.readString();
        sectionName = in.readString();
        status = in.readString();
        fatherPhoneNumber = in.readString();
        studentId = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFatherPhoneNumber() {
        return fatherPhoneNumber;
    }

    public void setFatherPhoneNumber(String fatherPhoneNumber) {
        this.fatherPhoneNumber = fatherPhoneNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(studentName);
        parcel.writeString(fatherName);
        parcel.writeString(className);
        parcel.writeString(sectionName);
        parcel.writeString(status);
        parcel.writeString(fatherPhoneNumber);
        parcel.writeString(studentId);
    }
}
