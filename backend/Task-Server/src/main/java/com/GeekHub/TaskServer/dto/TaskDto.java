package com.GeekHub.TaskServer.dto;

import com.GeekHub.TaskServer.entity.Task;
import com.GeekHub.TaskServer.entity.TaskStatus;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private long taskIdx;
    private TaskStatus status;
    private LocalDateTime assignTime;
    private LocalDateTime finTime;
    private String taskContent;

    public static TaskDto of(Task taskEntity){
        TaskDto taskDto = ModelMapperUtils.getModelMapper().map(taskEntity, TaskDto.class);
        return taskDto;
    }
}
