package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.service.SpotServie;
import com.GeekHub.TaskServer.service.SpotServieImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServieImpl.class);

    private final SpotServie SpotServie;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping
    public ResponseEntity<List<SpotResponseDto>> getSpotAll(){
        List<SpotResponseDto> result = null;
        try {
            result = SpotServie.getSpotAll();
        }catch (Exception e){
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{spot_idx}")
    public ResponseEntity<SpotResponseDto> getSpot(@PathVariable("spot_idx") Long spotIdx){
        SpotResponseDto result=null;
        try {
            result = SpotServie.getSpot(spotIdx);
        }catch (Exception e){
            throw  new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<String> createSpot(@RequestBody SpotRequestDto spotRequestDto){
        LOGGER.info("11111111111111111111");
        try {
            LOGGER.info("2222222222222222222");
            SpotServie.createSpot(spotRequestDto);
            LOGGER.info("33333333333333333333");
        }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

//    @PutMapping("/{spot_idx}")
//    public ResponseEntity<String> updateTask(@PathVariable("spot_idx") Long spotIdx,@RequestBody SpotRequestDto SpotDto){
//        try {
//            SpotDto.setSpotIdx(spotIdx);
//            SpotServie.updateSpot(SpotDto);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return  new ResponseEntity<String>("success",HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{spot_idx}")
//    public ResponseEntity<String> deleteTask(@PathVariable("spot_idx") Long spotIdx){
//        try {
//            SpotServie.deleteSpot(spotIdx);
//        }catch (Exception e){
//            throw new RuntimeException();
//        }
//        return  new ResponseEntity<String>("success",HttpStatus.OK);
//    }

}
