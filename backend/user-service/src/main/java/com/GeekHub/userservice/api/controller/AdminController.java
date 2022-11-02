package com.GeekHub.userservice.api.controller;

import com.GeekHub.userservice.api.request.LoginPostReq;
import com.GeekHub.userservice.api.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AdminController {

    private final AdminService adminService;

    // 배달기사 정보 생성
    @PostMapping("/createdriver")
    public ResponseEntity<?> createDriver(@RequestBody LoginPostReq loginPostReq) {

        try {
            log.info("requesting driverID creation");
            adminService.createDriver(loginPostReq);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 배달기사 정보 수정
    @PostMapping("/updatedriver")
    public ResponseEntity<?> updateDriver(@RequestBody LoginPostReq loginPostReq) {

        try {
            log.info("requesting update driverId = " + loginPostReq.getUserId());
            adminService.updateDriver(loginPostReq);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/validUserId")
    public ResponseEntity<?> validUserId(@RequestParam String userId) {

        try {
            Boolean validation = adminService.validUserId(userId);

            if(validation==true){
                return new ResponseEntity<>(validation,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(validation,HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/checkpic")
    public ResponseEntity<?> checkPicture(@RequestParam String orderIdx) {

        try {
            String returnUrl = adminService.loadPictureUrl(orderIdx);

            return new ResponseEntity<>(returnUrl,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
