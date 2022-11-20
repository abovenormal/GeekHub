package com.GeekHub.userservice.api.service;

import com.GeekHub.userservice.api.request.LoginPostReq;
import com.GeekHub.userservice.api.response.ChatUserInfoDto;
import com.GeekHub.userservice.db.entity.User;

import java.util.List;

public interface AdminService {

    /*
     * 배달기사 생성
     * @param LogintPostReq
     */
    void createDriver(LoginPostReq loginPostReq);

    /*
     * 배달기사 정보수정
     * @param LogintPostReq
     */
    void updateDriver(LoginPostReq loginPostReq);

    /*
     * 배달기사 정보수정
     * @param userId
     */
    boolean validUserId(String userId);

    User userInfo(long userIdx);

    List<ChatUserInfoDto> getUsers(String localSchool) throws Exception;

}
