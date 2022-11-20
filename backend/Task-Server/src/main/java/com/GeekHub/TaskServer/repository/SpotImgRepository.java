package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.SpotImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpotImgRepository extends JpaRepository<SpotImg, Long> {
    Optional<SpotImg> findSpotImgBySpotName(String spotName);
}
