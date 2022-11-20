package com.GeekHub.S3server.api.response;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRes {

    String s3PictureUrl;
    String userId;
    String spotId;
    String deliveryTime;

}