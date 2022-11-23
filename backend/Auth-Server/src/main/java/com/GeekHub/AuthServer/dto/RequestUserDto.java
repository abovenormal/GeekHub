package com.GeekHub.AuthServer.dto;

import lombok.Data;

@Data
public class RequestUserDto {
    private String userId;
    private String password;
}