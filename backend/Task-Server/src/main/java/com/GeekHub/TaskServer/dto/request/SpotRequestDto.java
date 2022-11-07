package com.GeekHub.TaskServer.dto.request;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpotRequestDto {

    private SpotCategory spotCategory;
    private String spotName;
    private double lat;
    private double lon;
    private LocalDateTime expectedTime;
    private LocalDateTime arrivedTime;
    private String imageUrl;
    private int status;
    private int count;

    private long userIdx;

    public static SpotRequestDto of(Spot spotEntity){
        SpotRequestDto spotDto = ModelMapperUtils.getModelMapper().map(spotEntity, SpotRequestDto.class);
        return spotDto;
    }
}
