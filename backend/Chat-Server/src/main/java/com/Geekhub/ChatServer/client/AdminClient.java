package com.Geekhub.ChatServer.client;

import com.Geekhub.ChatServer.dto.UserInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user", url = "http://localhost:9003")
public interface AdminClient {
    @GetMapping("/admin/user/{userIdx}")
    UserInfoDto userInfo(@PathVariable String userIdx);
}
