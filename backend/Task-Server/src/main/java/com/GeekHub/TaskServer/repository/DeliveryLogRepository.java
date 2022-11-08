package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.DeliveryLog;
import com.GeekHub.TaskServer.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryLogRepository extends JpaRepository<DeliveryLog, Long> {
    Optional<List<DeliveryLog>> findDeliveryLogByUserIdx(long userIdx);
}
