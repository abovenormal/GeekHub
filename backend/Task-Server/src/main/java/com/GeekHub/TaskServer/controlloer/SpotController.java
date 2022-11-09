package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.request.ImgRequestDto;
import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.*;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.service.DeliveryLogService;
import com.GeekHub.TaskServer.service.SpotService;
import com.GeekHub.TaskServer.service.SpotServiceImpl;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServiceImpl.class);

    private final SpotService SpotService;

    private final DeliveryLogService deliveryLogService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping
    public ResponseEntity<List<SpotResponseDto>> getSpotAll(){
        List<SpotResponseDto> result = null;
        try {
            result = SpotService.getSpotAll();
        }catch (Exception e){
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{spot_idx}")
    public ResponseEntity<SpotResponseDto> getSpot(@PathVariable("spot_idx") Long spotIdx){
        SpotResponseDto result=null;
        try {
            result = SpotService.getSpot(spotIdx);
        }catch (Exception e){
            throw  new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<String> createSpot(@RequestBody SpotRequestDto spotRequestDto){
        try {
           SpotService.createSpot(spotRequestDto);
         }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @PostMapping("/log")
    public ResponseEntity<List<SpotLogDto>> sendLog(@RequestBody LogRequestDto logRequestDto) throws Exception {
        List<SpotLogDto> list = deliveryLogService.log(logRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }
    @PostMapping("/current")
    public ResponseEntity<List<SpotLogDto>> sendCurrent(@RequestBody LogRequestDto logRequestDto) throws Exception {
        List<SpotLogDto> list = SpotService.current(logRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }


    @GetMapping("/success")
    public ResponseEntity<List<SchoolSuccessDto>> getSuccess() throws Exception {
        List<SchoolSuccessDto> list = deliveryLogService.getSuccess();

        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @GetMapping("/work/{driverIdx}")
    public ResponseEntity<HashMap> sendWork(@PathVariable("driverIdx") Long driverIdx) throws Exception {
        HashMap<String, Object> workList = new HashMap<>();
        List<WorkResponseDto> receiveList = SpotService.work(driverIdx, SpotCategory.STORE);
        workList.put("rec", receiveList);
        List<WorkResponseDto> deleveryList = SpotService.work(driverIdx, SpotCategory.DESTINATION);
        workList.put("del", deleveryList);
        return ResponseEntity.status(HttpStatus.OK).body(workList);
    }

    @PutMapping("/update")
    public ResponseEntity<String> workUpdate(@RequestBody Map<String, String> spotId) {
        try {
            SpotService.workUpdate(Long.valueOf(spotId.get("spotId")));
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return ResponseEntity.status(HttpStatus.OK).body("업무 완료");
    }

    @GetMapping("/nextInfo/{driverIdx}")
    public ResponseEntity<NextSpotDto> nextWork(@PathVariable("driverIdx") long driverIdx) {
        try {
            NextSpotDto result = SpotService.nextWork(driverIdx).orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/img")
    public ResponseEntity<String> saveImg(@RequestBody ImgRequestDto imgRequestDto) {
        try {
            SpotService.saveImg(imgRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body("성공");
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
