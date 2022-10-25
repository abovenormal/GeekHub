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
    private String longitude;
    private String latitude;
    private String timestamp;

    public Message() {
    }

    public Message(String driver, String longitude,String latitude,String timestamp) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.driver=driver;
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return "Message{" +
                "driver='" + driver + '\'' +
                ", longitude='" + longitude + '\'' +
                ", longitude='" + latitude + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
