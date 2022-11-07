package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.NextSpotDto;
import com.GeekHub.TaskServer.dto.response.SpotLogDto;
import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.service.SpotServie;
import com.GeekHub.TaskServer.service.SpotServieImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
           SpotServie.createSpot(spotRequestDto);
         }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @PostMapping("/log")
    public ResponseEntity<List<SpotLogDto>> sendLog(@RequestBody LogRequestDto logRequestDto) throws Exception {
        List<SpotLogDto> list = SpotServie.log(logRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/work/{driverIdx}")
    public ResponseEntity<HashMap> sendWork(@PathVariable("driverIdx") Long driverIdx) throws Exception {
        HashMap<String, Object> workList = new HashMap<>();
        List<WorkResponseDto> receiveList = SpotServie.work(driverIdx, SpotCategory.STORE);
        workList.put("rec", receiveList);
        List<WorkResponseDto> deleveryList = SpotServie.work(driverIdx, SpotCategory.DESTINATION);
        workList.put("del", deleveryList);
        return ResponseEntity.status(HttpStatus.OK).body(workList);
    }

    @PutMapping("/update")
    public ResponseEntity<String> workUpdate(@RequestBody Map<String, String> spotId) {
        try {
            SpotServie.workUpdate(Long.valueOf(spotId.get("spotId")));
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return ResponseEntity.status(HttpStatus.OK).body("업무 완료");
    }

    @GetMapping("/nextInfo/{driverIdx}")
    public ResponseEntity<NextSpotDto> nextWork(@PathVariable("driverIdx") long driverIdx) {
        try {
            NextSpotDto result = SpotServie.nextWork(driverIdx).orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
