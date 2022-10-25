package com.GeekHub.TaskServer.controller;

import com.GeekHub.TaskServer.dto.TaskDto;
import com.GeekHub.TaskServer.service.TaskServie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServie taskService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTaskAll(){
        List<TaskDto> result = null;
        try {
            result = taskService.getTaskAll();
        }catch (Exception e){
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    @PostMapping
//    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto,)


}
