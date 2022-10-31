package com.GeekHub.userservice.common.enums;

public enum OrderStatus {
    FINISH("배달완료"),
    DELIVERYING("배달중");

    private String status;

    OrderStatus(String status){this.status =status;}

    public String getStatus(){return status;}

}
