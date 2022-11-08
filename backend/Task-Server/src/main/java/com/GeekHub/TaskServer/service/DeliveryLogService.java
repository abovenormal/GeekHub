package com.GeekHub.TaskServer.service;


import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.response.SchoolSuccessDto;
import com.GeekHub.TaskServer.dto.response.SpotLogDto;

import java.util.List;

public interface DeliveryLogService {
    List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception;
    List<SchoolSuccessDto> getSuccess() throws  Exception;
}
