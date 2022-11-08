package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getUserAll() throws Exception;
    List<User> getSelectUser(String local_localCity,String localSchool) throws Exception;
    List<User> getSchoolUser(String localSchool) throws Exception;
    User getUser(Long userIdx) throws Exception;
    void saveUser(User user) throws Exception;
    void deleteUser(Long userIdx) throws Exception;
}
