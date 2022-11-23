package com.GeekHub.TaskServer.dto.request;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSpotRequestDto {

    private SpotCategory spotCategory;
    private String spotName;
    private double lat;
    private double lon;
    private LocalDateTime expectedTime;
    private int count;

    private String userName;

    public static UpdateSpotRequestDto of(Spot spotEntity){
        UpdateSpotRequestDto spotDto = ModelMapperUtils.getModelMapper().map(spotEntity, UpdateSpotRequestDto.class);
        return spotDto;
    }
}
