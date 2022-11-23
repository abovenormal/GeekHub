package com.GeekHub.userservice.api.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginPostReq {

    String userName;
    String userId;
    String password;
    String phone;
    String localCity;
    String localSchool;

}
