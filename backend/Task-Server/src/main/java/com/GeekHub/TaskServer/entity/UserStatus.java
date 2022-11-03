package com.GeekHub.TaskServer.entity;

public enum UserStatus {

    WORKING("근무중"),
    RESTING("비근무중");


    private String status;

    UserStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}

