package com.GeekHub.KafkaServer.controller;


import com.GeekHub.KafkaServer.model.DriverLocation;
import com.GeekHub.KafkaServer.service.DriverLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

@CrossOrigin
@RestController
@RequestMapping(value = "/driver")
public class DriverLocationController {
    @Autowired
    DriverLocationService driverLocationService;

    @GetMapping("/location")
    public DriverLocation getDriverLocation(@RequestParam String driver){
        System.out.println("Controller-check-1");
       // DriverLocation driverLocation = new DriverLocation();
        return driverLocationService.getDriverLocation(driver);

    }

}
