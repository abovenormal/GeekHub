package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.dto.response.WorkResponseDto;
import com.GeekHub.TaskServer.entity.Spot;
import com.GeekHub.TaskServer.entity.SpotCategory;
import com.GeekHub.TaskServer.entity.User;
import com.GeekHub.TaskServer.repository.SpotRepository;
import com.GeekHub.TaskServer.repository.UserRepository;
import com.GeekHub.TaskServer.service.SpotServieImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
@Slf4j
public class SpotDaoImpl implements SpotDao {
    private final SpotRepository spotRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServieImpl.class);
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
            List<Spot> searchList = spotRepository.findSpotByUserIdxAndSpotCategory(user.getUserIdx(), spotCategory).orElse(null);
            String time = "";
            if (searchList != null) {
                for (Spot spot : searchList) {
                    int hour = spot.getExpectedTime().getHour();
                    int minute = spot.getExpectedTime().getMinute();
                    time = hour + "시" + minute + "분";
                    WorkResponseDto workResponseDto = new WorkResponseDto();
                    workResponseDto.setSpotIndex(String.valueOf(spot.getSpotIdx()));
                    workResponseDto.setSpotName(spot.getSpotName());
                    workResponseDto.setExpectedTime(time);
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
    @Override
    public List<Spot> spotList(long userIdx,String date){
        List<Spot> result=new ArrayList<>();
        List<Spot> list = spotRepository.findSpotByUserIdx(userIdx).orElse(null);

        StringTokenizer st = new StringTokenizer(date,"-");
        String year = st.nextToken();
        String month = st.nextToken();
        String day = st.nextToken();
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-");
        if(month.length()==1){
            sb.append(0).append(month).append("-");
        }else {
            sb.append(month).append("-");
        }
        if(day.length()==1){
            sb.append(0).append(day);
        }else {
            sb.append(day);
        }
        LOGGER.info(String.valueOf(list.size()));
        for(Spot spot:list){
            LOGGER.info(spot.getExpectedTime().toString().substring(0,10));
            if(spot.getExpectedTime().toString().substring(0,10).equals(sb.toString())){
                result.add(spot);
            }
        }
        return result;
    }
    @Override
    @Transactional
    public void workUpdate(Long spotIdx) throws Exception{
        Spot spot = spotRepository.findSpotBySpotIdx(spotIdx).orElse(null);
        spot.setStatus(2);
//        spotRepository.save(spot);
    }

}
