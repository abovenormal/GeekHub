package com.GeekHub.TaskServer.dto.response;

import lombok.Data;

@Data
public class NextSpotDto {
    String spotName;
    double lat;
    double lon;
}
