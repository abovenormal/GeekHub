package com.GeekHub.TaskServer.dto;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

@Data
public class SpotDto {
    private long spotIdx;
    private SpotCategory spotCategory;
    private String spotName;
    private double lat;
    private double lon;

    public static SpotDto of(Spot spotEntity){
        SpotDto spotDto = ModelMapperUtils.getModelMapper().map(spotEntity, SpotDto.class);
        return spotDto;
    }
}
