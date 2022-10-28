package com.GeekHub.KafkaServer.repository;

import com.GeekHub.KafkaServer.model.DriverLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverLocationRepository extends MongoRepository<DriverLocation, String> {
    @Query(value = "db.location_log.find({\"driver\":?0}).sort({$natural:-1}).limit(1)")
    DriverLocation getDriverLoc(String driver);

}
