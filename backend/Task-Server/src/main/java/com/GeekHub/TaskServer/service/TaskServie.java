package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.TaskDto;

import java.util.List;

public interface TaskServie {
    List<TaskDto> getTaskAll() throws Exception;
    TaskDto getTask(Long taskIdx) throws  Exception;
    void createTask(TaskDto taskDto) throws Exception;
    void updateTask(TaskDto taskDto) throws Exception;
    void deleteTask(Long taskIdx) throws Exception;
    void complitionTask(Long taskIdx) throws Exception;
}
