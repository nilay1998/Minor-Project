package com.example.attendancemanagementfaculty.Reterofit;

public class Profile {
    private String status;
    private String message;
    private String name;
    private String email;
    private Boolean isClass;
    private int classes;
    private String[] dates;

    public String[] getDates() {
        return dates;
    }

    public void setDates(String[] dates) {
        this.dates = dates;
    }

    public int getClasses() {
        return classes;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }

    public Boolean getisClass() {
        return isClass;
    }

    public void setClass(Boolean aClass) {
        isClass = aClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
