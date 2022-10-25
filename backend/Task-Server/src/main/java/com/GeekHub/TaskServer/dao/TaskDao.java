package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaskDao  {
    List<Task> getTaskAll() throws Exception;
    Task getTask(Long taskIdx) throws Exception;
    void saveTask(Task task) throws Exception;
    void deleteTask(Long taskIdx) throws Exception;
}
