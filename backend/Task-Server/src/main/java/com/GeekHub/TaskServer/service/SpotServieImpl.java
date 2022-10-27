package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dao.SpotDao;
import com.GeekHub.TaskServer.dto.SpotDto;
import com.GeekHub.TaskServer.entity.Spot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotServieImpl implements SpotServie{

    private final SpotDao spotDao;

    @Override
    @Transactional
    public List<SpotDto> getSpotAll() throws Exception {
        try{
            List<Spot> spotList = spotDao.getSpotAll();
            List<SpotDto> spotDtoList = spotList.stream().map(entity -> SpotDto.of(entity)).collect(Collectors.toList());

            return spotDtoList;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public SpotDto getSpot(Long spotIdx) throws Exception {
        try {
            Spot spotEntity = spotDao.getSpot(spotIdx);
            SpotDto spotDto = SpotDto.of(spotEntity);

            return spotDto;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void createSpot(SpotDto spotDto) throws Exception {
        try {
            Spot spotEntity = new Spot();
            spotEntity.setSpotCategory(spotDto.getSpotCategory());
            spotEntity.setSpotName(spotDto.getSpotName());
            spotEntity.setLat(spotDto.getLat());
            spotEntity.setLon(spotDto.getLon());
            spotDao.saveSpot(spotEntity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void updateSpot(SpotDto spotDto) throws Exception {
        Spot spotEntity = spotDao.getSpot(spotDto.getSpotIdx());
        spotEntity.setSpotCategory(spotDto.getSpotCategory());
        spotEntity.setSpotName(spotDto.getSpotName());
        spotEntity.setLat(spotDto.getLat());
        spotEntity.setLon(spotDto.getLon());
        spotDao.saveSpot(spotEntity);
    }

    @Override
    @Transactional
    public void deleteSpot(Long spotIdx) throws Exception {
        spotDao.deleteSpot(spotIdx);
    }

}
