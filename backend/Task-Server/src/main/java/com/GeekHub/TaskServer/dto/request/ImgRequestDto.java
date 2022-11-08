package com.GeekHub.TaskServer.dto.request;

import lombok.Data;

@Data
public class ImgRequestDto {
    String s3PictureUrl;
    String userId;
    String spotId;
    String deliveryTime;
}
