package com.GeekHub.feign.Controller;

import com.GeekHub.feign.Client.FeignTestClient;
import com.GeekHub.feign.Dto.SpotResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/feign")
@RequiredArgsConstructor
public class APIController {

    @Autowired
    private final FeignTestClient feignTestClient;

    @GetMapping("/info")
    public ResponseEntity<HashMap> getInfo() {
        HashMap<String, Object> result = new HashMap<>();
        result = feignTestClient.test();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/spotInfo")
    public ResponseEntity<List<SpotResponseDto>> getAll() {
        List<SpotResponseDto> result = feignTestClient.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
