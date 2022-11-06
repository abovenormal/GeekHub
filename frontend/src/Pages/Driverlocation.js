import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import List from "../Components/Driverlocation/List";
import "./css/Driverlocation.css";
import { apiInstance } from "../api/index";
import { getDriverList } from "../api/GetDriverList";

const Driverlocation = () => {
  const [selected, setSelected] = useState({
    city: "",
    school: "",
    date: "",
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
  useEffect(() => {
    if (selected.city && selected.school && selected.date) {
      // List는 기사를 누를 때마다 해당 기사의 위치 정보 불러오기 (getLocation)
      // const res = GetLocations("");
      // console.log(res)
      // ----------------------위 내용은 List.js에 완료-------------
      // 해당 페이지에서는 기사 List+Task 받아오는 (getDriverList)
      // 해당 res를 <List /> prop 전달

      // const driverData = getDriverList(selected);
      // setListData(driverData);
      //-------아래는 임시 prop 확인용 -> Api 완성시 위에 두 줄 사용
      setListData([
        {
          name: "형준",
          id: 221343,
          task: [
            {
              pickupZone: "d",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "d",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "d",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "d",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
          ],
        },
        {
          name: "세환",
          id: 221343,
          task: [
            {
              pickupZone: "dㅁㄴㅇ",
              arrivalScheduled: "ㄴa",
              arrivalTime: "cㅇ",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
          ],
        },
      ]);
    }
    console.log(selected);
  }, [selected]);
  return (
    <div className="driver-location-container">
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
      </div>
      <List listData={listData} />
    </div>
  );
};
export default Driverlocation;
