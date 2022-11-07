package com.GeekHub.TaskServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkResponseDto {
    private String spotIndex;
    private String spotName;
    private String expectedTime;
    private int count;
    private int status;
}
