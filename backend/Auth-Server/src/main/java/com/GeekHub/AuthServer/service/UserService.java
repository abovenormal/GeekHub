package com.GeekHub.AuthServer.service;

import com.GeekHub.AuthServer.dto.TokenInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public TokenInfo login(String userId, String password);
}
