package com.lanzhu.test.job.entity;

public class DataEntity {

    private int id;
    private String location;
    private Status status;

    public DataEntity(int id, String location, Status status) {
        this.id = id;
        this.location = location;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        TODO,
        COMPLETED
    }

}
