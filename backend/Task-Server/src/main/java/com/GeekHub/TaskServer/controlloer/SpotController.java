package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.request.CreateSpotRequestDto;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @GetMapping("/send")
    public void send() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> request = new HashMap<String,Object>();
        String[] result;
        try {
            result = SpotService.delayUser();
        }catch (Exception e){
            throw new Exception();
        }
        if(result==null){
            return ;
        }
        request.put("username", "긱허브봇");
        request.put("text", "*"+result[0]+"("+result[1]+","+result[2]+")님의 "+result[3]+" 배달이 지연되고 있습니다.*\n" +
                ":round_pushpin:<http://k7c205.p.ssafy.io/drivermap|"+result[0]+" 기사님의 현재 위치 조회> :white_check_mark::white_check_mark:\n" +
                ":car:<http://k7c205.p.ssafy.io/driverlocation|"+result[0]+" 기사님의 배달 현황 조회> :fire::fire:");
        HttpEntity<Map<String,Object>> entity = new HttpEntity<Map<String,Object>>(request);
        // Webhook URL
        String url = "https://hooks.slack.com/services/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";
        log.info("11111111111");
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        log.info("222222222222");
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
    @PostMapping("/createname")
    public ResponseEntity<String> createSpotName(@RequestBody CreateSpotRequestDto createSpotRequestDto){
        try {

            SpotService.createSpotName(createSpotRequestDto);
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
        boolean check = true;
        List<WorkResponseDto> receiveList = SpotService.work(driverIdx, SpotCategory.STORE);
        workList.put("rec", receiveList);
        List<WorkResponseDto> deleveryList = SpotService.work(driverIdx, SpotCategory.DESTINATION);
        workList.put("del", deleveryList);
        for (WorkResponseDto workResponseDto : deleveryList) {
            if (workResponseDto.getStatus() == 1) {
                check = false;
                break;
            }
        }
        workList.put("isFinished", check);
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

    @GetMapping("/workingDriver")
    public ResponseEntity<List<DriverLocationDto>> workingDriver() {
        try {
            List<DriverLocationDto> list = deliveryLogService.workingDriver();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
