package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dao.SpotDao;
import com.GeekHub.TaskServer.dao.UserDao;
import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.SpotLogDto;
import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotServieImpl implements SpotServie{
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServieImpl.class);
    private final SpotDao spotDao;
    private final UserDao userDao;

    @Override
    @Transactional
    public List<SpotResponseDto> getSpotAll() throws Exception {
        try{
            List<Spot> spotList = spotDao.getSpotAll();
            List<SpotResponseDto> spotDtoList = spotList.stream().map(entity -> SpotResponseDto.of(entity)).collect(Collectors.toList());

            return spotDtoList;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public SpotResponseDto getSpot(Long spotIdx) throws Exception {
        try {
            Spot spotEntity = spotDao.getSpot(spotIdx);
            SpotResponseDto spotResponseDto = SpotResponseDto.of(spotEntity);

            return spotResponseDto;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void createSpot(SpotRequestDto spotRequestDto) throws Exception {
        LOGGER.info(spotRequestDto.toString());
        User spotUser = userDao.getUser(spotRequestDto.getUserIdx());
        LOGGER.info(spotUser.toString());
        try {
            Spot spotEntity = Spot.builder()
                    .spotCategory(spotRequestDto.getSpotCategory())
                    .spotName(spotRequestDto.getSpotName())
                    .lat(spotRequestDto.getLat())
                    .lon(spotRequestDto.getLon())
                    .expected_time(spotRequestDto.getExpected_time())
                    .status(spotRequestDto.getStatus())
                    .count(spotRequestDto.getCount())
                    .userIdx(spotUser.getUserIdx())
                    .build();
            spotDao.saveSpot(spotEntity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    public List<WorkResponseDto> work(long driverIdx, SpotCategory spotCategory) throws Exception {
        return spotDao.work(driverIdx, spotCategory);
    }

    public List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception{
       String local_city= logRequestDto.getLocal_city();
        String local_school= logRequestDto.getLocal_school();
        String date= logRequestDto.getDate();
        List<User> users = userDao.getSelectUser(local_city,local_school);
        List<SpotLogDto> result= new ArrayList<>();
        for(User user:users){
            LOGGER.info(String.valueOf(user.getUserIdx()));
            SpotLogDto spotLogDto = new SpotLogDto();
            spotLogDto.setUserName(user.getUserName());
            spotLogDto.setSpotResponseDtoList(spotDao.spotList(user.getUserIdx(),date));
            result.add(spotLogDto);
        }

        return result;
    }
//    @Override
//    @Transactional
//    public void updateSpot(SpotRequestDto spotRequestDto,Long spotIdx) throws Exception {
//        Spot spotEntity = spotDao.getSpot(spotIdx);
//
//        spotDao.saveSpot(spotEntity);
//    }
//
//    @Override
//    @Transactional
//    public void deleteSpot(Long spotIdx) throws Exception {
//        spotDao.deleteSpot(spotIdx);
//    }

}

