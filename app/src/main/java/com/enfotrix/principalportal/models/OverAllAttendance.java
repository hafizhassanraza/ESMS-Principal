package com.enfotrix.principalportal.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class OverAllAttendance implements Parcelable {
    String class_,section,sectionId,total,present,absent,leave,percent;

    public OverAllAttendance(String class_, String section, String sectionId, String total, String present, String absent, String leave, String percent) {
        this.class_ = class_;
        this.section = section;
        this.sectionId = sectionId;
        this.total = total;
        this.present = present;
        this.absent = absent;
        this.leave = leave;
        this.percent = percent;
    }

    protected OverAllAttendance(Parcel in) {
        class_ = in.readString();
        section = in.readString();
        sectionId = in.readString();
        total = in.readString();
        present = in.readString();
        absent = in.readString();
        leave = in.readString();
        percent = in.readString();
    }

    public static final Creator<OverAllAttendance> CREATOR = new Creator<OverAllAttendance>() {
        @Override
        public OverAllAttendance createFromParcel(Parcel in) {
            return new OverAllAttendance(in);
        }

        @Override
        public OverAllAttendance[] newArray(int size) {
            return new OverAllAttendance[size];
        }
    };

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(class_);
        parcel.writeString(section);
        parcel.writeString(sectionId);
        parcel.writeString(total);
        parcel.writeString(present);
        parcel.writeString(absent);
        parcel.writeString(leave);
        parcel.writeString(percent);
    }
}
