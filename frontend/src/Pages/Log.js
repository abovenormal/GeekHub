import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import List from "../Components/Log/List";
import "./css/Log.css";
import { getDriverList } from "../api/GetDriverList";
const Log = () => {
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: "",
  });
  const [driverData, setDriverData] = useState([]);
  useEffect(() => {
    if (selected.localCity && selected.localSchool && selected.date) {
      // const driver = getDriverList(selected);
      // setDriverData(driver);
      //-------아래는 임시 prop 확인용 -> Api 완성시 위에 두 줄 사용
      setDriverData([
        {
          name: "형준",
          id: 221343,
          task: [
            {
              pickupZone: "알촌",
              arrivalScheduled: "a",
              arrivalTime: "12:11",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "12:11",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c12:11",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
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
              pickupZone: "알촌",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
            {
              pickupZone: "생돈가스",
              arrivalScheduled: "a",
              arrivalTime: "c",
              pickupPicture:
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Google_Chrome_icon_%28February_2022%29.svg/2048px-Google_Chrome_icon_%28February_2022%29.svg.png",
            },
          ],
        },
      ]);
    }
    // 1. 해당 지역, 학교의 기사 리스트 가져오기 (getDriverList)
    // 1번의 데이터를 state에 저장하고 List에 prop
    // 2. Driver의 id를 통해 로그 데이터 가져오기 (getLogData, {params: {id: id}})
    // 2번은 그러면 List에서 목록 헤더 부분에 작성해야함
    console.log(selected);
  }, [selected]);
  return (
    <div className="log-container">
      <h1>로그 기록 조회</h1>
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
      </div>
      <List driverData={driverData} />
    </div>
  );
};
export default Log;
