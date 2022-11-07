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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
                    .expectedTime(spotRequestDto.getExpectedTime())
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

    @Override
    @Transactional
    public void workUpdate(Long spotIdx) throws Exception {
        //log.info(String.valueOf(spotIdx));
        System.out.println(spotIdx);
        spotDao.workUpdate(spotIdx);
    }



    public List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception{
       String localCity= logRequestDto.getLocalCity();
        String localSchool= logRequestDto.getLocalSchool();
        String date= logRequestDto.getDate();
        List<User> users = userDao.getSelectUser(localCity,localSchool);
        List<SpotLogDto> result= new ArrayList<>();
        for(User user:users){
            SpotLogDto spotLogDto = new SpotLogDto();
            spotLogDto.setUserName(user.getUserName());
            spotLogDto.setUserIdx(user.getUserIdx());

            List<Spot> list = spotDao.spotList(user.getUserIdx(),date);
            List<SpotResponseDto> temp = new ArrayList<>();
            int total=0;
            int count=0;
            for(Spot spot: list){
                SpotResponseDto spotResponseDto= new SpotResponseDto();
                spotResponseDto.setSpotIdx(spot.getSpotIdx());
                spotResponseDto.setSpotCategory(spot.getSpotCategory());
                spotResponseDto.setSpotName(spot.getSpotName());
                spotResponseDto.setLat(spot.getLat());
                spotResponseDto.setLon(spot.getLon());
                spotResponseDto.setExpectedTime(spot.getExpectedTime());
                spotResponseDto.setArrivedTime(spot.getArrivedTime());
                spotResponseDto.setImageUrl(spot.getImageUrl());
                spotResponseDto.setCount(spot.getCount());
                spotResponseDto.setStatus(spot.getStatus());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = sdf.parse(String.valueOf(spot.getExpectedTime()).substring(11));
                    date2 = sdf.parse(String.valueOf(spot.getArrivedTime()).substring(11));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                spotResponseDto.setDif((date2.getTime()-date1.getTime())/60000);
                temp.add(spotResponseDto);
                LOGGER.info(String.valueOf(spot.getExpectedTime()));
            }

            spotLogDto.setSpotResponseDtoList(temp);
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

