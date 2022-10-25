package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Task;
import com.GeekHub.TaskServer.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskDaoImpl implements TaskDao {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskDaoImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTaskAll() throws Exception {
        List<Task> taskList = taskRepository.findAll();
        return taskList;
    }

    @Override
    public Task getTask(Long taskIdx) throws Exception {
        Task taskEntity = taskRepository.getById(taskIdx);
        if(taskEntity == null)
            throw new Exception();
        return taskEntity;
    }

    @Override
    public void saveTask(Task task) throws Exception {
        try{
            taskRepository.save(task);
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    public void deleteTask(Long taskIdx) throws Exception {
        try{
            taskRepository.deleteById(taskIdx);
        }catch (Exception e){
            throw new Exception();
        }
    }
}
