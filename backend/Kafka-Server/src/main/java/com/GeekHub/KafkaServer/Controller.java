package com.GeekHub.KafkaServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class Controller {
    @GetMapping("/log")
    public String kafkalog(){
        return "kafka";

    }
}
