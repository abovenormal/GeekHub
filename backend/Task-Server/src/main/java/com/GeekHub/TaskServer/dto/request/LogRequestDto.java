package com.GeekHub.TaskServer.dto.request;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogRequestDto {
    private String localCity;
    private String localSchool;
    private String date;

}
