package com.GeekHub.userservice.api.service;

import com.GeekHub.userservice.api.request.LoginPostReq;

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

    /*
     * 주문번호에 따른 사진 불러오기
     * @param orderIdx
     * */
}
