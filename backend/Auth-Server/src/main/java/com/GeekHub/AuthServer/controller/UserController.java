package com.GeekHub.AuthServer.controller;


import com.GeekHub.AuthServer.dto.RequestUserDto;
import com.GeekHub.AuthServer.dto.Token;
import com.GeekHub.AuthServer.entity.User;
import com.GeekHub.AuthServer.repository.UserRepository;
import com.GeekHub.AuthServer.service.AuthService;
import com.GeekHub.AuthServer.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/login")
    public Token login(@RequestBody RequestUserDto requestUserDto) {
        log.info("user id = {}", requestUserDto.getUserId());
        User member = userRepository.findByUserId(requestUserDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));

        if(bCryptPasswordEncoder.matches(requestUserDto.getPassword(),member.getPassword())){
            Token tokenDto = jwtTokenProvider.createAccessToken(member.getUserId(), member.getRoles(),member.getUsername());
            log.info("getrole = {}", member.getRoles());
            authService.login(tokenDto);
            return tokenDto;
        }else{
         return null;
        }

    }
    @PostMapping("/refresh")
    public ResponseEntity<String> validateRefreshToken(@RequestBody HashMap<String, String> bodyJson){

        log.info("refresh controller 실행");
        Map<String, String> map = authService.validateRefreshToken(bodyJson.get("refreshToken"));

        if(map.get("status").equals("402")){
            log.info("RefreshController - Refresh Token이 만료.");
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        log.info("RefreshController - Refresh Token이 유효.");
        return new ResponseEntity<String>("OK", HttpStatus.OK);

    }
}
