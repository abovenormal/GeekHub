package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUserIdx(long userIdx);
}
