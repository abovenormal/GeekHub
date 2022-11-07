package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.User;
import com.GeekHub.TaskServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private final UserRepository userRepository;

    @Autowired
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserAll() throws Exception {
        List<User> userList = userRepository.findAll();
        return userList;
    }
    @Override
    public List<User> getSelectUser(String local_city, String local_school) throws Exception {
        List<User> userList =userRepository.findUserByLocalCityAndLocalSchool(local_city,local_school).orElse(null);

        return userList;
    }
    @Override
    public User getUser(Long userIdx) throws Exception {
        User userEntity = userRepository.findUserByUserIdx(userIdx);
        if(userEntity == null) {
            throw new Exception();
        }
        return userEntity;
    }

    @Override
    public void saveUser(User user) throws Exception {
        try{
            userRepository.save(user);
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    public void deleteUser(Long userIdx) throws Exception {
        try{
            userRepository.deleteById(userIdx);
        }catch (Exception e){
            throw new Exception();
        }
    }
}
