package com.GeekHub.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "log-service", url = "http://localhost:8000/log-service")
public interface LogServiceClient {

    @GetMapping(produces = "/")
    List<String> loadDriverLogs(String userIdx);
}
