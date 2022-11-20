package com.GeekHub.S3server.api.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileReq {

    MultipartFile image;
    String userId;
    String spotId;
    String deliveryTime;

}
