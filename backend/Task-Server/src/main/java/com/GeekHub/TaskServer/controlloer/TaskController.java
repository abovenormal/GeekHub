package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.TaskDto;
import com.GeekHub.TaskServer.service.TaskServie;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{task_idx}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("task_idx") Long taskIdx){
        TaskDto result=null;
        try {
            result = taskService.getTask(taskIdx);
        }catch (Exception e){
            throw  new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDto taskDto){
        try {
            taskService.createTask(taskDto);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @PutMapping("/{task_idx}")
    public ResponseEntity<String> updateTask(@PathVariable("task_idx") Long taskIdx,@RequestBody TaskDto taskDto){
        try {
            taskDto.setTaskIdx(taskIdx);
            taskService.updateTask(taskDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

    @DeleteMapping("/{task_idx}")
    public ResponseEntity<String> deleteTask(@PathVariable("task_idx") Long taskIdx){
        try {
            taskService.deleteTask(taskIdx);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

    @PutMapping("/complition/{task_idx}")
    public ResponseEntity<String> completionTask(@PathVariable("task_idx") Long taskIdx){
        try {
            taskService.complitionTask(taskIdx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

}
