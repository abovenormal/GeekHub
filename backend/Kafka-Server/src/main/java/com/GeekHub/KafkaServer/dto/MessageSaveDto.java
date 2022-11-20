package com.GeekHub.KafkaServer.dto;

import com.GeekHub.KafkaServer.model.Message;
import lombok.Data;

@Data
public class MessageSaveDto {
    private String driver;
    private Double longitude;
    private Double latitude;
    private String timestamp;

    public Message toEntity() {
        Message message = new Message();
        message.setDriver(driver);
        message.setLatitude(latitude);
        message.setLongitude(longitude);
        message.setTimestamp(timestamp);
        return message;
    }
}
