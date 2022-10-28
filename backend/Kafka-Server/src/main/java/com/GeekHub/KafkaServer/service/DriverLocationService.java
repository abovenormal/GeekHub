package com.GeekHub.KafkaServer.service;

import com.GeekHub.KafkaServer.model.DriverLocation;
import com.GeekHub.KafkaServer.repository.DriverLocationRepository;
import com.GeekHub.KafkaServer.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverLocationService {
    @Autowired
    private DriverLocationRepository repository;

    public DriverLocation getDriverLocation(String driver){
        System.out.println("service-check");
        return repository.getDriverLoc(driver);
    }
}
