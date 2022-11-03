package com.GeekHub.feign.Dto;

import com.GeekHub.feign.Entity.SpotCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpotResponseDto {
    private long spotIdx;
    private SpotCategory spotCategory;
    private String spotName;
    private double lat;
    private double lon;
    private LocalDateTime expected_time;
    private int count;
    private int status;

    private long userIdx;

}
