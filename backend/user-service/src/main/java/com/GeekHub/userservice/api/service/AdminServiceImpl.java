package com.GeekHub.userservice.api.service;

import com.GeekHub.userservice.api.request.LoginPostReq;
import com.GeekHub.userservice.api.response.ChatUserInfoDto;
import com.GeekHub.userservice.common.enums.UserStatus;
import com.GeekHub.userservice.db.entity.User;
import com.GeekHub.userservice.db.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final DriverRepository driverRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void createDriver(LoginPostReq loginPostReq) {

        User driver = User.builder()
                .userName(loginPostReq.getUserName())
                .userId(loginPostReq.getUserId())
                .password(bCryptPasswordEncoder.encode(loginPostReq.getPassword()))
                .phone(loginPostReq.getPhone())
                .localCity(loginPostReq.getLocalCity())
                .localSchool(loginPostReq.getLocalSchool())
                .userStatus(UserStatus.RESTING)
                .build();

        driverRepository.save(driver);
        log.info("creating Driver completed");

    }

    @Transactional
    public void updateDriver(LoginPostReq loginPostReq) {

        String driverId = loginPostReq.getUserId();

        User curDriver = driverRepository.findByUserId(driverId).orElse(null);

        curDriver.setUserId(loginPostReq.getUserId());
        curDriver.setPassword(loginPostReq.getPassword());
        curDriver.setUserName(loginPostReq.getUserName());
        curDriver.setPhone(loginPostReq.getPhone());
        curDriver.setLocalCity(loginPostReq.getLocalCity());
        curDriver.setLocalSchool(loginPostReq.getLocalSchool());

        log.info("updating Driver completed");
    }

    public boolean validUserId(String userId) {

        log.info("checking validation userId");

        User driver = driverRepository.findByUserId(userId).orElse(null);

        if (driver == null) {
            log.info("validated userID");
            return true;
        }

        log.info("Invalidated userID");
        return false;

    }

    public User userInfo(long userIdx) {
        User user = driverRepository.findUserByUserIdx(userIdx).orElse(null);
        return user;
    }
    @Override
    public List<ChatUserInfoDto> getUsers(String localSchool) throws Exception{
        List<ChatUserInfoDto> result = new ArrayList<>();
        List<User> users = driverRepository.findAllByLocalSchool(localSchool);
        try {
            for (User u : users) {
                ChatUserInfoDto chatUserInfoDto = new ChatUserInfoDto();
                chatUserInfoDto.setUserIdx(u.getUserIdx());
                chatUserInfoDto.setUserName(u.getUserName());
                result.add(chatUserInfoDto);
            }
        }catch (Exception e) {
            throw new Exception(e);
        }
        return result;
    }
}
