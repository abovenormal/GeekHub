package com.GeekHub.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "nav-service", url = "http://localhost:8000/nav-service")
public interface NavServiceClient {

    @GetMapping(produces = "/")
    String getDriverLocate(String userIdx);

}
