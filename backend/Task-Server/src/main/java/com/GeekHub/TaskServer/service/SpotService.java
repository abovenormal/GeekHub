package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.request.CreateSpotRequestDto;
import com.GeekHub.TaskServer.dto.request.ImgRequestDto;
import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.*;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;

import java.util.List;
import java.util.Optional;

public interface SpotService {
    List<SpotResponseDto> getSpotAll() throws Exception;
    SpotResponseDto getSpot(Long spotIdx) throws  Exception;
    void createSpotName(CreateSpotRequestDto createSpotRequestDto) throws Exception;
    void createSpot(SpotRequestDto spotDto) throws Exception;
//    void updateSpot(SpotRequestDto spotDto) throws Exception;
    void deleteSpot(Long spotIdx) throws Exception;
//    List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception;
//    List<SchoolSuccessDto> getSuccess() throws  Exception;
    List<WorkResponseDto> work(long driverIdx, SpotCategory spotCategory) throws Exception;

    void workUpdate(Long spotIdx) throws Exception;

    Optional<NextSpotDto> nextWork(Long driverIdx) throws Exception;

    void saveImg(ImgRequestDto imgRequestDto) throws Exception;

    List<SpotLogDto> current(LogRequestDto logRequestDto) throws Exception;
    List<Spot> spotList(long userIdx, String date)  throws Exception;
    String[] delayUser() throws Exception;
}
