package com.GeekHub.KafkaServer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Data
@Document(collection = "location_log")
public class Message implements Serializable {

    @Id
    private String _id;
    private String driver;
    private Double longitude;
    private Double latitude;
    private String timestamp;

    public Message() {
    }

    public Message(String driver, Double longitude,Double latitude,String timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.driver=driver;
        this.timestamp = timestamp;
    }
}
