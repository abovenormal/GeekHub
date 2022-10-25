package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dao.TaskDao;
import com.GeekHub.TaskServer.dto.TaskDto;
import com.GeekHub.TaskServer.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServieImpl implements  TaskServie{
    private final TaskDao taskDao;

    @Override
    @Transactional
    public List<TaskDto> getTaskAll() throws Exception {
        try{
            List<Task> taskList = taskDao.getTaskAll();
            List<TaskDto> taskDtoList = taskList.stream().map(entity -> TaskDto.of(entity)).collect(Collectors.toList());

            return taskDtoList;
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    @Transactional
    public TaskDto getTask(Long taskIdx) throws Exception {
        try {
            Task taskEntity = taskDao.getTask(taskIdx);
            TaskDto taskDto = TaskDto.of(taskEntity);

            return taskDto;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void createTask(TaskDto taskDto) throws Exception {
        try {
            Task taskEntity = new Task();
            System.out.println("테스크 엔티티생성");
            taskEntity.setTaskContent(taskDto.getTaskContent());
            taskEntity.setStatus(taskDto.getStatus());
            taskEntity.setAssignTime(LocalDateTime.now());
            System.out.println("엔티티 세팅 완료");
            taskDao.saveTask(taskEntity);
            System.out.println("세이브");
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void updateTask(TaskDto taskDto) throws Exception {
        Task taskEntity = taskDao.getTask(taskDto.getTaskId());
        taskEntity.setStatus(taskDto.getStatus());
        taskEntity.setFinTime(taskDto.getFinTime());
        taskEntity.setAssignTime(taskDto.getAssignTime());
        taskEntity.setTaskContent(taskDto.getTaskContent());
        taskDao.saveTask(taskEntity);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskIdx) throws Exception {
        taskDao.deleteTask(taskIdx);
    }
}
