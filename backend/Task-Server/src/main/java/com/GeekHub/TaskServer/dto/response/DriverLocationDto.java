package com.GeekHub.TaskServer.dto.response;

import lombok.Data;

@Data
public class DriverLocationDto {
    private String userName;
    private long userIdx;
    private double lat;
    private double lon;
}
