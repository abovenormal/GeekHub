package com.GeekHub.TestServer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/first-service")
public class TestController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to the First service";
    }


}
