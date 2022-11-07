package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;

import java.util.List;

public interface SpotDao {
    List<Spot> getSpotAll() throws Exception;
    Spot getSpot(Long spotIdx) throws Exception;
    void saveSpot(Spot spot) throws Exception;
    void deleteSpot(Long spotIdx) throws Exception;

    List<WorkResponseDto> work(long driverIdx, SpotCategory spotCategory) throws Exception;
    List<Spot> spotList(long userIdx,String date) throws Exception;
    void workUpdate(Long spotIdx) throws Exception;
}
