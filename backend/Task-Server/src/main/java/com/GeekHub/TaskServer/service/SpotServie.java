package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.SpotResponseDto;

import java.util.List;

public interface SpotServie {
    List<SpotResponseDto> getSpotAll() throws Exception;
    SpotResponseDto getSpot(Long spotIdx) throws  Exception;
    void createSpot(SpotRequestDto spotDto) throws Exception;
//    void updateSpot(SpotRequestDto spotDto) throws Exception;
//    void deleteSpot(Long spotIdx) throws Exception;
}
