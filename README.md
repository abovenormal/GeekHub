# :exclamation: 프로젝트 소개

##### 안녕하세요 팀 Del:eaT(DELivery+EAT) 입니다

### :raising_hand: 팀원소개

<table>
  <tr>
      <td align="center"><a href="https://github.com/positivehun/"><img src="https://avatars.githubusercontent.com/u/46879750?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>김지헌<br>Back-end, 팀장</b></sub></a><br /></td>      
      <td align="center"><a href="https://github.com/kky0455"><img src="https://avatars.githubusercontent.com/u/97174109?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>김광용<br>Back-end</b></sub></a><br /></td>      
      <td align="center"><a href="https://github.com/paneko3"><img src="https://avatars.githubusercontent.com/u/34058812?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>정재철<br>Back-end</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/abovenormal"><img src="https://avatars.githubusercontent.com/u/51263415?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>한세환<br>Back-end<br/></b></sub></a></td>
      <td align="center"><a href="https://github.com/Xsungmin"><img src="https://avatars.githubusercontent.com/u/97585835?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>김성민<br>Front-end,<br/>Application</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/hanggeee"><img src="https://avatars.githubusercontent.com/u/97644412?v=4" width="100px;" height="120px;" alt=""/><br /><sub><b>윤형준<br>Front-end,<br/>Web</b></sub></a><br /></td>

  </tr>
</table>

### 긱허브(GeekHub) 서비스 소개

#####

- #### 프로젝트명 : 긱허브(GeekHub)

- #### 진행 기간: 2022.08.29 ~ 2022.11.21

- #### 팀명: 딜:이트(Del:eaT)

- #### 목표:

- #### [긱허브 진행 내용 (by notion)](https://handy-beryllium-5b4.notion.site/07d2efefde7d407eb2ca911da5fa8f7d)

- #### 서비스 특징

  - ##### 바쁜 배달기사님들을 위한 배달 보조 앱입니다.

  - ##### NFC(Near Field Communication) 근거리 무선 통신을 이용하여, 픽업과 배달완료 사진을 간편하게 처리 할 수 있습니다.

  - ##### TMap API를 활용하여 위치, 경로 탐색, 네비게이션 연동 서비스를 제공합니다.

  - ##### 관리자 페이지에선 각 지점의 배달 성공률, 배달기사님들의 사진 로그를 바탕으로 각 지점의 개별적 관리를 지원합니다.

- #### 주요 기능

  | 서비스          | 주요 기능                                                                                  |
  | --------------- | ------------------------------------------------------------------------------------------ |
  | NFC             | 핸드폰 근거리 통신으로 사진 촬영과 전송을 한번에 진행하여 시간을 단축합니다.               |
  | 네비게이션      | T-map 오픈소스를 이용하여 기사님들의 현재 위치, 최단 경로, 그리고 네비게이션을 제공합니다. |
  | 채팅            | (-----------이후 추가----------)                                                           |
  | 실시간 모니터링 | 사진이 촬영, 전송되는 시점과 예상 도착 시점의 오차를 분석하여 도표로 제공합니다.           |
  | 지난 로그 확인  | 저장된 기록들을 분석하여 지난 날의 성공,실패 확률을 원그래프로 제시해 줍니다.              |

- ## 상세기능

  - #### 1. 관리자 페이지

    - #### 1.1 로그인 페이지

      - ##### 배달긱 홍보영상을 상시 재생하고 있습니다.
      - ##### 관리자로 임명된(ROLE) 아이디만 서비스에 접속할 수 있습니다.

    - #### 1.2 Overview(배달 요약) 페이지

      - ##### 배달현황을 간략하게 볼 수 있는 페이지입니다.
      - ##### 전국 지역과, 각 지역의 배달 성공 / 실패를 원 그래프로 확인할 수 있습니다.

    - #### 1.3 실시간 모니터링 페이지

      - #### 지역, 장소, 시간 테이블, 날짜를 통해 현재 어떻게 배달이 진행되고 있는지 파악합니다.
      - #### 해당 지역의 기사의 현재 위치를 실시간으로 지도에 표시합니다.
      - #### 장소, 도착 예정 시각, 실제 도착 시각, 오차, 사진을 확인합니다.
      - #### 예정 시각과 도착 시각의 오차를 그래프를 통해 확인합니다.
      - #### 오차 시간이 5분이 넘어서 도착할 경우 관리자에게 알람이 울립니다.
      - #### 해당 날짜에 기록이 없을 시 '조회된 데이터가 없습니다'로 표시 됩니다.

    - #### 1.4 로그 확인 페이지

      - #### 배달 완료된 사진 기록을 확인 할 수 있습니다.
      - #### 기록된 GPS를 확인 할 수 있습니다.(전체 보기를 통해 그 날 기록된 기록을 확인 가능합니다.)

    - #### 1.5 신규 기사 생성 페이지

      - #### 배달기사의 신상정보(배달 지역, 시간)를 통해 아이디를 생성합니다.
      - #### 아이디 중복확인을 제공합니다.

  - ### 2. GeekHub 앱

    - #### 2.1 로그인 화면

      - #### 관리자가 생성한 아이디로 앱에 로그인합니다.
      - #### 앱을 이용하기에 앞서 사용자에게 앱 사용 중 위치 공유 권환을 제공 받습니다.

    - #### 2.2 실시간 모니터링 페이지

      - #### 지역, 장소, 시간 테이블, 날짜를 통해 현재 어떻게 배달이 진행되고 있는지 파악합니다.
      - #### 해당 지역의 기사의 현재 위치를 실시간으로 지도에 표시합니다.
      - #### 장소, 도착 예정 시각, 실제 도착 시각, 오차, 사진을 확인합니다.
      - #### 예정 시각과 도착 시각의 오차를 그래프를 통해 확인합니다.
      - #### 오차 시간이 5분이 넘어서 도착할 경우 관리자에게 알람이 울립니다.
      - #### 해당 날짜에 기록이 없을 시 '조회된 데이터가 없습니다'로 표시 됩니다.

    - #### 1.3 실시간 모니터링 페이지

      - #### 지역, 장소, 시간 테이블, 날짜를 통해 현재 어떻게 배달이 진행되고 있는지 파악합니다.
      - #### 해당 지역의 기사의 현재 위치를 실시간으로 지도에 표시합니다.
      - #### 장소, 도착 예정 시각, 실제 도착 시각, 오차, 사진을 확인합니다.
      - #### 예정 시각과 도착 시각의 오차를 그래프를 통해 확인합니다.
      - #### 오차 시간이 5분이 넘어서 도착할 경우 관리자에게 알람이 울립니다.
      - #### 해당 날짜에 기록이 없을 시 '조회된 데이터가 없습니다'로 표시 됩니다.

## :wrench: 기술스택

### Frontend

<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">

<br />

### Android

<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/AndroidStudio-3DDC84?style=for-the-badge&logo=Android&logoColor=white"/>
<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"/>

<br />

### Backend

<img src="https://img.shields.io/badge/Java-1E8CBE?style=for-the-badge&logo=OpenJDK&logoColor=white"> 
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/Apache Tomcat-F8DC75?style=for-the-badge&logo=Apache Tomcat&logoColor=black">

<br />

### DataBase

<img alt="MySQL" src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> 
<img src="https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=MongoDB&logoColor=white"> <br />

### Server

<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"> 
<img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<br />

<img src="https://img.shields.io/badge/Nginx-RED?style=for-the-badge&logo=Nginx&logoColor=white">
<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/JENKINS-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> <br />

### **Version Control**

<img src="https://img.shields.io/badge/GitLab-FC6D26?style=for-the-badge&logo=GitLab&logoColor=white">
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">

### **Issue Tracking System**

<img src="https://img.shields.io/badge/jira-%230A0FFF.svg?style=for-the-badge&logo=jira&logoColor=white"> <br />
<img src="https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white">

## 실행방법

직접 빌드 하거나 [배포 사이트](http://k7c205.p.ssafy.io/)로 접속!!

#### Frontend

## :exclamation:

client 폴더 안에서 아래의 명령어를 실행합니다.

- dev 환경에서는 약간의 버그가 있을 수 있습니다.

```
환경변수 .env.development  .env.production
NEXT_PUBLIC_API_URI=http://j7c207.p.ssafy.io:8088
NEXT_PUBLIC_BLOCKCHAIN_URI=http://43.200.253.174:3000

```

```
패키지 설치
$ npm install
```

```
프로젝트 실행
$ npm run dev
```

#### Backend <br/>

##### Spring <br />

GeekHub 폴더 안에서 아래의 명령어를 실행합니다.

```
jar 파일 빌드
$ ./gradlew build
```

```
빌드 파일 실행
폴더이동 : build -> libs
$ java -jar aldl.jar
```
