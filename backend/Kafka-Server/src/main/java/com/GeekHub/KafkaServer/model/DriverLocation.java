package com.GeekHub.KafkaServer.model;

import lombok.Data;

@Data
public class DriverLocation {
    private String driver;
    private String longitude;
    private String latitude;
}
