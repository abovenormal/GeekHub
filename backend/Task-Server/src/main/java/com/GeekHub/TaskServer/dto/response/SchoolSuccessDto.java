package com.GeekHub.TaskServer.dto.response;

import lombok.Data;

@Data
public class SchoolSuccessDto {
    private String schoolName;
    private long totalSpot;
    private long successSpot;

}
