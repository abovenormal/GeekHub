package com.GeekHub.userservice.api.controller;

import com.GeekHub.userservice.api.request.LoginPostReq;
import com.GeekHub.userservice.api.response.ChatUserInfoDto;
import com.GeekHub.userservice.api.response.UserInfoDto;
import com.GeekHub.userservice.api.service.AdminService;
import com.GeekHub.userservice.db.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

            if(validation){
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
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{userId}")
    public UserInfoDto userInfo(@PathVariable String userId) {
        UserInfoDto userInfoDto = new UserInfoDto();
        try {
            User user = adminService.userInfo(Long.parseLong(userId));
            userInfoDto.setLocalCity(user.getLocalCity());
            userInfoDto.setLocalSchool(user.getLocalSchool());
            userInfoDto.setName(user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoDto;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam String localSchool) {
        List<ChatUserInfoDto> result = new ArrayList<>();
        try {
            result = adminService.getUsers(localSchool);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
