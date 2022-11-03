package com.GeekHub.userservice.api.service;

import com.GeekHub.userservice.api.request.LoginPostReq;
import com.GeekHub.userservice.common.enums.UserStatus;
import com.GeekHub.userservice.db.entity.User;
import com.GeekHub.userservice.db.repository.DriverRepository;
import com.GeekHub.userservice.db.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
                .local_city(loginPostReq.getLocal_city())
                .local_school(loginPostReq.getLocal_school())
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
        curDriver.setLocal_city(loginPostReq.getLocal_city());
        curDriver.setLocal_school(loginPostReq.getLocal_school());

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

}