import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import List from "../Components/Driverlocation/List";
import "./css/Driverlocation.css";
import { apiInstance } from "../api/index";
import getDriverList from "../api/GetDriverList";

const Driverlocation = () => {

  let today = new Date();   
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1;  // 월
  let date = today.getDate();  // 날짜

  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`,
  });
  const [listData, setListData] = useState([]);
  const API = apiInstance();
  // 이거 GetLocation 함수는 여기가 아니라 List쪽에서 이름 클릭 했을 때
  // driver id 넘겨 주면서 get요청 해야할 듯
  // List.js에서 이거 쓸 때 /getLocation/${id} 이런 식으로 파라미터 형으로 변경 필
  // async function GetLocation(e) {
  //   e.preventDefault();
  //   try {
  //     const res = await API.get("/location/getLocation?=driver=221343")
  //     console.log(res.data)
  //   }
  //   catch (err) {
  //     console.log(err)
  //   }
  // }
  // useEffect(() => {
  //   if (selected.localCity && selected.localSchool && selected.date) {
  //     // ----------------------위 내용은 List.js에 완료-------------
  //     // 해당 페이지에서는 기사 List+Task 받아오는 (getDriverList)
  //     // 해당 res를 <List /> prop 전달
  //     // const driverData = getDriverList(selected);
  //   }}, [selected]);

    
    useEffect(() => {
      if (selected.localCity && selected.localSchool && selected.date) {
        if (`${year}-${month}-${date}` === selected.date) {
          console.log("선택한 날짜는 오늘")
          async function getData() {
            const res = await apiInstance().post('spot/current', selected);
            setListData(res.data)
          }
          getData();}
        else {
          console.log("선택한 날짜는 오늘이 아님")
            async function getData() {
              const res = await apiInstance().post('spot/log', selected);
              setListData(res.data)
            }
            getData();
          }
      }
      
      console.log(selected)
    }, [selected])
      

      // console.log(driverData);
      // setListData(driverData);
    
      //-------아래는 임시 prop 확인용 -> Api 완성시 위에 두 줄 사용
    //   setListData([
    //     {
    //       name: "형준",
    //       id: 221343,
    //       task: [
    //         {
    //           pickupZone: "d",
    //           arrivalScheduled: "a",
    //           arrivalTime: "c",
    //           pickupPicture:
    //             "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
    //         },
    //         {
    //           pickupZone: "d",
    //           arrivalScheduled: "a",
    //           arrivalTime: "c",
    //           pickupPicture:
    //             "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
    //         },
    //         {
    //           pickupZone: "d",
    //           arrivalScheduled: "a",
    //           arrivalTime: "c",
    //           pickupPicture:
    //             "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
    //         },
    //         {
    //           pickupZone: "d",
    //           arrivalScheduled: "a",
    //           arrivalTime: "c",
    //           pickupPicture:
    //             "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
    //         },
    //       ],
    //     },
    //     {
    //       name: "세환",
    //       id: 221343,
    //       task: [
    //         {
    //           pickupZone: "dㅁㄴㅇ",
    //           arrivalScheduled: "ㄴa",
    //           arrivalTime: "cㅇ",
    //           pickupPicture:
    //             "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
    //         },
    //       ],
    //     },
    //   ]);
    // }
    // console.log(selected);
  
  return (
    <div className="driver-location-container">
      <h1>배달 현황 조회</h1>
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
      </div>
      <List listData={listData} />
    </div>
  );
};
export default Driverlocation;
