package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Spot;

import java.util.List;

public interface SpotDao {
    List<Spot> getSpotAll() throws Exception;
    Spot getSpot(Long spotIdx) throws Exception;
    void saveSpot(Spot spot) throws Exception;
    void deleteSpot(Long spotIdx) throws Exception;
}
