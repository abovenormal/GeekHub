package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot,Long> {

}
