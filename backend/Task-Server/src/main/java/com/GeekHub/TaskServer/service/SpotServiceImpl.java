package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.request.CreateSpotRequestDto;
import com.GeekHub.TaskServer.dto.request.ImgRequestDto;
import com.GeekHub.TaskServer.dto.request.LogRequestDto;
import com.GeekHub.TaskServer.dto.request.SpotRequestDto;
import com.GeekHub.TaskServer.dto.response.*;
import com.GeekHub.TaskServer.entity.*;
import com.GeekHub.TaskServer.repository.SpotImgRepository;
import com.GeekHub.TaskServer.repository.SpotRepository;
import com.GeekHub.TaskServer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpotServiceImpl.class);
    @Autowired
    private final SpotRepository spotRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SpotImgRepository spotImgRepository;


    @Override
    @Transactional
    public List<SpotResponseDto> getSpotAll() throws Exception {
        try{
            List<Spot> spotList = spotRepository.findAll();
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
            Spot spotEntity = spotRepository.findSpotBySpotIdx(spotIdx).orElse(null);
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
        User spotUser = userRepository.findUserByUserIdx(spotRequestDto.getUserIdx());
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
            spotRepository.save(spotEntity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void createSpotName(CreateSpotRequestDto createSpotRequestDto) throws Exception {
        LOGGER.info(createSpotRequestDto.toString());
        User spotUser = userRepository.findUserByUserName(createSpotRequestDto.getUserName());

        LOGGER.info(spotUser.toString());
        try {
            Spot spotEntity = Spot.builder()
                    .spotCategory(createSpotRequestDto.getSpotCategory())
                    .spotName(createSpotRequestDto.getSpotName())
                    .lat(createSpotRequestDto.getLat())
                    .lon(createSpotRequestDto.getLon())
                    .expectedTime(createSpotRequestDto.getExpectedTime())
                    .status(createSpotRequestDto.getStatus())
                    .count(createSpotRequestDto.getCount())
                    .userIdx(spotUser.getUserIdx())
                    .build();
            spotRepository.save(spotEntity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    public List<WorkResponseDto> work(long driverIdx, SpotCategory spotCategory) throws Exception {
        User user = userRepository.findUserByUserIdx(driverIdx);
        List<WorkResponseDto> result = new ArrayList<>();
        try {
            List<Spot> searchList = spotRepository.findSpotByUserIdxAndSpotCategoryOrderByExpectedTime(user.getUserIdx(), spotCategory).orElse(null);
            String  time = "";
            if (searchList != null) {
                for (Spot spot : searchList) {
                    String spotLogo[] = spot.getSpotName().split(" ");
                    SpotImg spotImg = spotImgRepository.findSpotImgBySpotName(spotLogo[0]).orElse(null);
                    int hour = spot.getExpectedTime().getHour();
                    int minute = spot.getExpectedTime().getMinute();
                    time = minute<10?hour + "시 0" + minute + "분":hour+"시 " + minute + "분";
                    WorkResponseDto workResponseDto = new WorkResponseDto();
                    workResponseDto.setSpotIndex(String.valueOf(spot.getSpotIdx()));
                    workResponseDto.setSpotName(spot.getSpotName());
                    workResponseDto.setExpectedTime(time);
                    workResponseDto.setCount(spot.getCount());
                    workResponseDto.setStatus(spot.getStatus());
                    workResponseDto.setIconUrl(spotImg.getImgUrl());
                    result.add(workResponseDto);
                }
            }
            return result;
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    @Transactional
    public void workUpdate(Long spotIdx) throws Exception {
        Spot spot = spotRepository.findSpotBySpotIdx(spotIdx).orElse(null);
        spot.setStatus(2);
    }


//    public List<SpotLogDto> log(LogRequestDto logRequestDto) throws Exception{
//        String localCity= logRequestDto.getLocalCity();
//        String localSchool= logRequestDto.getLocalSchool();
//        String date= logRequestDto.getDate();
//        List<User> users = userRepository.findUserByLocalCityAndLocalSchool(localCity, localSchool).orElse(null);
//        List<SpotLogDto> result= new ArrayList<>();
//        for(User user:users){
//            SpotLogDto spotLogDto = new SpotLogDto();
//            spotLogDto.setUserName(user.getUserName());
//            spotLogDto.setUserIdx(user.getUserIdx());
//
//            List<Spot> list = spotList(user.getUserIdx(), date);
//            List<SpotResponseDto> temp = new ArrayList<>();
//            int total=0;
//            int count=0;
//            for(Spot spot: list){
//                SpotResponseDto spotResponseDto= new SpotResponseDto();
//                spotResponseDto.setSpotIdx(spot.getSpotIdx());
//                spotResponseDto.setSpotCategory(spot.getSpotCategory());
//                spotResponseDto.setSpotName(spot.getSpotName());
//                spotResponseDto.setLat(spot.getLat());
//                spotResponseDto.setLon(spot.getLon());
//                spotResponseDto.setExpectedTime(spot.getExpectedTime());
//                spotResponseDto.setArrivedTime(spot.getArrivedTime());
//                spotResponseDto.setImageUrl(spot.getImageUrl());
//                spotResponseDto.setCount(spot.getCount());
//                spotResponseDto.setStatus(spot.getStatus());
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                Date date1 = null;
//                Date date2 = null;
//                try {
//                    date1 = sdf.parse(String.valueOf(spot.getExpectedTime()).substring(11));
//                    date2 = sdf.parse(String.valueOf(spot.getArrivedTime()).substring(11));
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
//                spotResponseDto.setDif((date2.getTime()-date1.getTime())/60000);
//                temp.add(spotResponseDto);
//                LOGGER.info(String.valueOf(spot.getExpectedTime()));
//            }
//
//            spotLogDto.setSpotResponseDtoList(temp);
//            result.add(spotLogDto);
//        }
//
//        return result;
//    }
//    public List<SchoolSuccessDto> getSuccess() throws Exception{
//        List<SchoolSuccessDto> result= new ArrayList<>();
//        Date date = new Date();
//        date = new Date(date.getTime()+(1000*60*60*24*-1));
//        SimpleDateFormat dSdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//        String yesterday = dSdf.format(date);
//        String[] schools= {"전남대학교","광주과학기술원"
//                ,"건국대학교","경희대학교","마포구공유오피스","서울교육대학교","서울대학교","서울시립대학교","연세대학교","이화여자대학교","카이스트경영대학","한국외국어대학교","한성대학교"
//                ,"송도 글로벌캠퍼스","연세대학교(송도)","성균관대학교(자연과학캠퍼스)"};
//        for(String school: schools){
//            SchoolSuccessDto schoolSuccessDto = new SchoolSuccessDto();
//            schoolSuccessDto.setSchoolName(school);
//            long totalSpot=0;
//            long successSpot=0;
//            List<User> users= userRepository.findUserByLocalSchool(school).orElse(null);
//            for(User user:users){
//                List<Spot> spotList = spotList(user.getUserIdx(), "2022-11-02");;
//                for(Spot spot:spotList){
//                    if(spot.getStatus()==2){
//                        totalSpot++;
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                        Date date1 = null;
//                        Date date2 = null;
//                        try {
//                            date1 = sdf.parse(String.valueOf(spot.getExpectedTime()).substring(11));
//                            date2 = sdf.parse(String.valueOf(spot.getArrivedTime()).substring(11));
//                        } catch (ParseException e) {
//                            throw new RuntimeException(e);
//                        }
//                        if((date2.getTime()-date1.getTime())/60000<=0){
//                            successSpot++;
//                        };
//                    }
//                }
//            }
//            schoolSuccessDto.setTotalSpot(totalSpot);
//            schoolSuccessDto.setSuccessSpot(successSpot);
//            result.add(schoolSuccessDto);
//        }
//        return result;
//    }
//
//    private List<Spot> spotList(long userIdx, String date) {
//        List<Spot> result = new ArrayList<>();
//        List<Spot> list = spotRepository.findSpotByUserIdx(userIdx).orElse(null);
//
//        StringTokenizer st = new StringTokenizer(date, "-");
//        String year = st.nextToken();
//        String month = st.nextToken();
//        String day = st.nextToken();
//        StringBuilder sb = new StringBuilder();
//        sb.append(year).append("-");
//        if (month.length()==1){
//            sb.append(0).append(month).append("-");
//        }else{
//            sb.append(month).append("-");
//        }
//        if(day.length()==1){
//            sb.append(0).append(day);
//        }else {
//            sb.append(day);
//        }
//        for (Spot spot : list) {
//            if (spot.getExpectedTime().toString().substring(0, 10).equals(sb.toString())){
//                result.add(spot);
//            }
//        }
//        return result;
//    }

    @Override
    public Optional<NextSpotDto> nextWork(Long driverIdx) throws Exception {
        List<Spot> result = spotRepository.findSpotByUserIdxOrderByExpectedTime(driverIdx).orElse(null);
        NextSpotDto nextSpotDto = new NextSpotDto();
        try {
            for (Spot spot : result) {
                if (spot.getStatus() == 1) {
                    nextSpotDto.setSpotName(spot.getSpotName());
                    nextSpotDto.setLat(spot.getLat());
                    nextSpotDto.setLon(spot.getLon());
                    return Optional.of(nextSpotDto);
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void saveImg(ImgRequestDto imgRequestDto) throws Exception{
        try {
            Spot spot = spotRepository.findSpotByUserIdxAndSpotIdx(Long.parseLong(imgRequestDto.getUserId()), Long.parseLong(imgRequestDto.getSpotId()));
            spot.setImageUrl(imgRequestDto.getS3PictureUrl());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH:mm", Locale.KOREA);
            log.info(imgRequestDto.getDeliveryTime());
            LocalDateTime dateTime = LocalDateTime.parse(imgRequestDto.getDeliveryTime(), formatter);
            log.info("안나오니?");
            log.info(dateTime.toString());
            log.info("진짜??");
            spot.setArrivedTime(dateTime);
        }catch (Exception e) {
            throw new Exception();
        }
    }
    @Override
    @Transactional
    public List<SpotLogDto> current(LogRequestDto logRequestDto) throws Exception{
        String localCity= logRequestDto.getLocalCity();
        String localSchool= logRequestDto.getLocalSchool();
        String date= logRequestDto.getDate();
        List<User> users = userRepository.findUserByLocalCityAndLocalSchool(localCity, localSchool).orElse(null);
        List<SpotLogDto> result= new ArrayList<>();
        for(User user:users){
            SpotLogDto spotLogDto = new SpotLogDto();
            spotLogDto.setUserName(user.getUserName());
            spotLogDto.setUserIdx(user.getUserIdx());

            List<Spot> list = spotList(user.getUserIdx(), date);
            List<SpotResponseDto> temp = new ArrayList<>();
            int total=0;
            int count=0;
            for(Spot spot: list){
                if(spot.getStatus()==0) continue;
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
                if(spot.getArrivedTime()==null) {
                }else {
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = sdf.parse(String.valueOf(spot.getExpectedTime()).substring(11));
                        date2 = sdf.parse(String.valueOf(spot.getArrivedTime()).substring(11));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    spotResponseDto.setDif((date2.getTime()-date1.getTime())/60000);
                }
                temp.add(spotResponseDto);
                LOGGER.info(String.valueOf(spot.getExpectedTime()));
            }

            spotLogDto.setSpotResponseDtoList(temp);
            result.add(spotLogDto);
        }

        return result;
    }
    @Override
    @Transactional
    public List<Spot> spotList(long userIdx, String date) {
        List<Spot> result = new ArrayList<>();
        List<Spot> list = spotRepository.findSpotByUserIdxOrderByExpectedTime(userIdx).orElse(null);

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
        for (Spot spot : list) {
            if (spot.getExpectedTime().toString().substring(0, 10).equals(sb.toString())){
                result.add(spot);
            }
        }
        return result;
    }
    public String[] delayUser() throws Exception{
        List<User> users = userRepository.findAll();
        String[] arr = new String[4];
        for(User user:users){
            List<Spot> spots = spotRepository.findSpotByUserIdxOrderByExpectedTime(user.getUserIdx()).orElse(null);

            for(Spot spot:spots){
                if(spot.getStatus()==2){continue;}
                String ex = String.valueOf(spots.get(0).getExpectedTime());
                String str = ex.replace("T"," ");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
                LocalDateTime now = LocalDateTime.now().minusMinutes(5);

                if(now.compareTo(dateTime)>0){
                    arr[0]=user.getUserName();
                    arr[1]=user.getLocalCity();
                    arr[2]=user.getLocalSchool();
                    arr[3]=spot.getSpotName();
                    return arr;
                }
            }
        }
        return null;
    }
}

