package com.GeekHub.S3server.client;

import com.GeekHub.S3server.api.response.UploadFileRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "fegin", url = "http://k7c205.p.ssafy.io:8000")
public interface TaskClient {

    @PostMapping("/spot/img")
    String saveImage(UploadFileRes res);


}
