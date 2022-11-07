package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepository extends JpaRepository<Spot,Long> {
    Optional<List<Spot>> findSpotByUserIdxAndSpotCategory(long userIdx, SpotCategory spotCategory);
    Optional<List<Spot>> findSpotByUserIdx(long userIdx);
    Optional<Spot> findSpotBySpotIdx(Long spotIdx);
}
