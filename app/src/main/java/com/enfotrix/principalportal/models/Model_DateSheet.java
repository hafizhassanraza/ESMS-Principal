package com.enfotrix.principalportal.models;

public class Model_DateSheet {
    String ClassName;
    String Date;
    String DocID;
    String Exam;
    String ExamID;
    String SectionID;
    String SectionName;
    String Session;
    String SubjectID;
    String SubjectName;

    public Model_DateSheet(){

    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDocID() {
        return DocID;
    }

    public void setDocID(String docID) {
        DocID = docID;
    }

    public String getExam() {
        return Exam;
    }

    public void setExam(String exam) {
        Exam = exam;
    }

    public String getExamID() {
        return ExamID;
    }

    public void setExamID(String examID) {
        ExamID = examID;
    }

    public String getSectionID() {
        return SectionID;
    }

    public void setSectionID(String sectionID) {
        SectionID = sectionID;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(String subjectID) {
        SubjectID = subjectID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public Model_DateSheet(String className, String date, String docID, String exam, String examID, String sectionID, String sectionName, String session, String subjectID, String subjectName) {
        ClassName = className;
        Date = date;
        DocID = docID;
        Exam = exam;
        ExamID = examID;
        SectionID = sectionID;
        SectionName = sectionName;
        Session = session;
        SubjectID = subjectID;
        SubjectName = subjectName;
    }
}
