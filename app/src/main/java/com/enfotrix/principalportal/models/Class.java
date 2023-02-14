package com.enfotrix.principalportal.models;

public class Class {
    private String AdmissionFee;
    private String ClassFee;
    private String ClassName;
    private String HostelFee;
    private String LabFee;
    private String MagazineFee;
    private String MedicalFee;
    private String MedicalHostelFee;
    private String StationaryFee;


    public Class(String className){
        this.ClassName = className;
    }


    public String getAdmissionFee() {
        return AdmissionFee;
    }

    public void setAdmissionFee(String admissionFee) {
        AdmissionFee = admissionFee;
    }

    public String getClassFee() {
        return ClassFee;
    }

    public void setClassFee(String classFee) {
        ClassFee = classFee;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getHostelFee() {
        return HostelFee;
    }

    public void setHostelFee(String hostelFee) {
        HostelFee = hostelFee;
    }

    public String getLabFee() {
        return LabFee;
    }

    public void setLabFee(String labFee) {
        LabFee = labFee;
    }

    public String getMagazineFee() {
        return MagazineFee;
    }

    public void setMagazineFee(String magazineFee) {
        MagazineFee = magazineFee;
    }

    public String getMedicalFee() {
        return MedicalFee;
    }

    public void setMedicalFee(String medicalFee) {
        MedicalFee = medicalFee;
    }

    public String getMedicalHostelFee() {
        return MedicalHostelFee;
    }

    public void setMedicalHostelFee(String medicalHostelFee) {
        MedicalHostelFee = medicalHostelFee;
    }

    public String getStationaryFee() {
        return StationaryFee;
    }

    public void setStationaryFee(String stationaryFee) {
        StationaryFee = stationaryFee;
    }

    public Class() {

    }

    @Override
    public String toString() {
        return ClassName;
    }
}
