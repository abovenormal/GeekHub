package com.GeekHub.AuthServer.service;

import com.GeekHub.AuthServer.dto.TokenInfo;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    public TokenInfo login(String memberId, String password);
}
