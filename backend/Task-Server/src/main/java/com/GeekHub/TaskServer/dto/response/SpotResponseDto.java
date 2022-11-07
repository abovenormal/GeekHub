package com.GeekHub.TaskServer.dto.response;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpotResponseDto {
    private long spotIdx;
    private SpotCategory spotCategory;
    private String spotName;
    private double lat;
    private double lon;
    private LocalDateTime expectedTime;
    private LocalDateTime arrivedTime;
    private String imageUrl;
    private int count;
    private int status;

    private long userIdx;

    public static SpotResponseDto of(Spot spotEntity){
        SpotResponseDto spotDto = ModelMapperUtils.getModelMapper().map(spotEntity, SpotResponseDto.class);
        return spotDto;
    }
}
