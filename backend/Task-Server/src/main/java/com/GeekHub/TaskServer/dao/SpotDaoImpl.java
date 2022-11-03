package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpotDaoImpl implements SpotDao {
    private final SpotRepository spotRepository;

    @Autowired
    public SpotDaoImpl(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
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
}
