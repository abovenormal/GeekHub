package com.GeekHub.TaskServer.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SpotLogDto {
    private String userName;
    private long userIdx;
    private List<SpotResponseDto> spotResponseDtoList;
}
