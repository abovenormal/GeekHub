package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.entity.User;
import com.GeekHub.TaskServer.repository.SpotRepository;
import com.GeekHub.TaskServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpotDaoImpl implements SpotDao {
    private final SpotRepository spotRepository;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    public SpotDaoImpl(SpotRepository spotRepository, UserRepository userRepository) {
        this.spotRepository = spotRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Spot> getSpotAll() throws Exception {
        List<Spot> spotList = spotRepository.findAll();
        return spotList;
    }

    @Override
    public Spot getSpot(Long spotIdx) throws Exception {
        Spot spotEntity = spotRepository.getReferenceById(spotIdx);
        if(spotEntity == null)
            throw new Exception();
        return spotEntity;
    }

    @Override
    public void saveSpot(Spot spot) throws Exception {
        try{
            spotRepository.save(spot);
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    public void deleteSpot(Long spotIdx) throws Exception {
        try{
            spotRepository.deleteById(spotIdx);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    public List<WorkResponseDto> work(long driverIdx, SpotCategory spotCategory) throws Exception {
        User user = userRepository.findUserByUserIdx(driverIdx);
        List<WorkResponseDto> result = new ArrayList<>();
        try {
            List<Spot> searchList = spotRepository.findSpotByUserAndSpotCategory(user, spotCategory).orElse(null);
            if (searchList != null) {
                for (Spot spot : searchList) {
                    WorkResponseDto workResponseDto = new WorkResponseDto();
                    workResponseDto.setSpotName(spot.getSpotName());
                    workResponseDto.setExpected_time(spot.getExpected_time());
                    workResponseDto.setCount(spot.getCount());
                    workResponseDto.setStatus(spot.getStatus());
                    result.add(workResponseDto);
                }
            }
            return result;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
