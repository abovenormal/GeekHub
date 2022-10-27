package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.SpotDto;

import java.util.List;

public interface SpotServie {
    List<SpotDto> getSpotAll() throws Exception;
    SpotDto getSpot(Long spotIdx) throws  Exception;
    void createSpot(SpotDto spotDto) throws Exception;
    void updateSpot(SpotDto spotDto) throws Exception;
    void deleteSpot(Long spotIdx) throws Exception;
}
