package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.SpotDto;
import com.GeekHub.TaskServer.service.SpotServie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotServie SpotServie;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping
    public ResponseEntity<List<SpotDto>> getSpotAll(){
        List<SpotDto> result = null;
        try {
            result = SpotServie.getSpotAll();
        }catch (Exception e){
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{spot_idx}")
    public ResponseEntity<SpotDto> getTask(@PathVariable("spot_idx") Long spotIdx){
        SpotDto result=null;
        try {
            result = SpotServie.getSpot(spotIdx);
        }catch (Exception e){
            throw  new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody SpotDto SpotDto){
        try {
            SpotServie.createSpot(SpotDto);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @PutMapping("/{spot_idx}")
    public ResponseEntity<String> updateTask(@PathVariable("spot_idx") Long spotIdx,@RequestBody SpotDto SpotDto){
        try {
            SpotDto.setSpotIdx(spotIdx);
            SpotServie.updateSpot(SpotDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

    @DeleteMapping("/{spot_idx}")
    public ResponseEntity<String> deleteTask(@PathVariable("spot_idx") Long spotIdx){
        try {
            SpotServie.deleteSpot(spotIdx);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

}
