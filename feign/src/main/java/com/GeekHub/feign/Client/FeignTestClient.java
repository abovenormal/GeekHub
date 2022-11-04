package com.GeekHub.feign.Client;

import com.GeekHub.feign.Dto.SpotResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = "fegin", url = "http://k7c205.p.ssafy.io:9013")
public interface FeignTestClient {

    @GetMapping("/spot/work/1")
    HashMap<String, Object> test();

    @GetMapping("/spot")
    List<SpotResponseDto> getAll();
}
