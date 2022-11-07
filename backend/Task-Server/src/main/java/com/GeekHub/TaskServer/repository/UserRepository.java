package com.GeekHub.TaskServer.repository;

import com.GeekHub.TaskServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUserIdx(long userIdx);
    Optional<List<User>> findUserByLocalCityAndLocalSchool(String localCity, String localSchool);
}