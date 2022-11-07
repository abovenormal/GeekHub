package com.GeekHub.userservice.db.entity;

import com.GeekHub.userservice.common.enums.UserStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter @Setter
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdx;

    @Column
    private String userName;

    @Column( unique = true)
    private String userId;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String localCity;

    @Column
    private String localSchool;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    @Embedded
    private Location location;
}
