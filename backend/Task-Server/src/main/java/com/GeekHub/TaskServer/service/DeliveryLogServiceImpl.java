package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.response.SchoolSuccessDto;
import com.GeekHub.TaskServer.dto.response.SpotLogDto;
import com.GeekHub.TaskServer.dto.response.SpotResponseDto;
import com.GeekHub.TaskServer.entity.DeliveryLog;
import com.GeekHub.TaskServer.entity.User;
import com.GeekHub.TaskServer.repository.DeliveryLogRepository;
import com.GeekHub.TaskServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryLogServiceImpl implements DeliveryLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServiceImpl.class);
    @Autowired
    private final DeliveryLogRepository deliveryLogRepository;
    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception{
        // 시티, 스큘, 날짜
        String localCity= logRequestDto.getLocalCity();
        String localSchool= logRequestDto.getLocalSchool();
        String date= logRequestDto.getDate();
        // 드라이버 정보 가져오기
        List<User> users = userRepository.findUserByLocalCityAndLocalSchool(localCity, localSchool).orElse(null);
        // 리턴용 리스트
        List<SpotLogDto> result= new ArrayList<>();
        // 해당 기사들 순회해서
        for(User user:users){
            SpotLogDto spotLogDto = new SpotLogDto();
            spotLogDto.setUserName(user.getUserName());
            spotLogDto.setUserIdx(user.getUserIdx());

            List<DeliveryLog> list = spotList(user.getUserIdx(), date);
            List<SpotResponseDto> temp = new ArrayList<>();
            int total=0;
            int count=0;
            for(DeliveryLog deliveryLog: list){
                SpotResponseDto spotResponseDto= new SpotResponseDto();
                spotResponseDto.setSpotIdx(deliveryLog.getSpotIdx());
                log.info(String.valueOf(deliveryLog.getLogIdx()));
                spotResponseDto.setSpotCategory(deliveryLog.getSpotCategory());
                spotResponseDto.setSpotName(deliveryLog.getSpotName());
                spotResponseDto.setLat(deliveryLog.getLat());
                spotResponseDto.setLon(deliveryLog.getLon());
                spotResponseDto.setExpectedTime(deliveryLog.getExpectedTime());
                spotResponseDto.setArrivedTime(deliveryLog.getArrivedTime());
                spotResponseDto.setImageUrl(deliveryLog.getImageUrl());
                spotResponseDto.setCount(deliveryLog.getCount());
                spotResponseDto.setStatus(deliveryLog.getStatus());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                if(deliveryLog.getArrivedTime()==null) {
                }else {
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = sdf.parse(String.valueOf(deliveryLog.getExpectedTime()).substring(11));
                        date2 = sdf.parse(String.valueOf(deliveryLog.getArrivedTime()).substring(11));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    spotResponseDto.setDif((date2.getTime()-date1.getTime())/60000);
                }

                temp.add(spotResponseDto);
                LOGGER.info(String.valueOf(deliveryLog.getExpectedTime()));
            }

            spotLogDto.setSpotResponseDtoList(temp);
            result.add(spotLogDto);
        }

        return result;
    }

    @Override
    public List<SchoolSuccessDto> getSuccess() throws Exception{
        List<SchoolSuccessDto> result= new ArrayList<>();
        Date date = new Date();
        date = new Date(date.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String yesterday = dSdf.format(date);
        String[] schools= {"SSAFY","전남대학교","광주과학기술원"
                ,"건국대학교","경희대학교","마포구공유오피스","서울교육대학교","서울대학교","서울시립대학교","연세대학교","이화여자대학교","카이스트경영대학","한국외국어대학교","한성대학교"
                ,"송도 글로벌캠퍼스","연세대학교(송도)","성균관대학교(자연과학캠퍼스)"};
        for(String school: schools){
            SchoolSuccessDto schoolSuccessDto = new SchoolSuccessDto();
            schoolSuccessDto.setSchoolName(school);
            long totalSpot=0;
            long successSpot=0;
            List<User> users= userRepository.findUserByLocalSchool(school).orElse(null);
            for(User user:users){
                List<DeliveryLog> deliveryLogList = spotList(user.getUserIdx(), yesterday);;
                for(DeliveryLog deliveryLog:deliveryLogList){
                    if(deliveryLog.getStatus()==2){
                        if(deliveryLog.getArrivedTime()==null){
                            continue;
                        }
                        totalSpot++;
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = sdf.parse(String.valueOf(deliveryLog.getExpectedTime()).substring(11));
                            date2 = sdf.parse(String.valueOf(deliveryLog.getArrivedTime()).substring(11));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        if((date2.getTime()-date1.getTime())/60000<=0){
                            successSpot++;
                        };
                    }
                }
            }
            schoolSuccessDto.setTotalSpot(totalSpot);
            schoolSuccessDto.setSuccessSpot(successSpot);
            result.add(schoolSuccessDto);
        }
        return result;
    }

    private List<DeliveryLog> spotList(long userIdx, String date) {
        List<DeliveryLog> result = new ArrayList<>();
        List<DeliveryLog> list = deliveryLogRepository.findDeliveryLogByUserIdx(userIdx).orElse(null);

        StringTokenizer st = new StringTokenizer(date, "-");
        String year = st.nextToken();
        String month = st.nextToken();
        String day = st.nextToken();
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-");
        if (month.length()==1){
            sb.append(0).append(month).append("-");
        }else{
            sb.append(month).append("-");
        }
        if(day.length()==1){
            sb.append(0).append(day);
        }else {
            sb.append(day);
        }
        for (DeliveryLog deliveryLog : list) {
            if (deliveryLog.getExpectedTime().toString().substring(0, 10).equals(sb.toString())){
                result.add(deliveryLog);
            }
        }
        return result;
    }

}
