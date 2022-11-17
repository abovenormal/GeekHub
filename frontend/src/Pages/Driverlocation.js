import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import Timebelt from "../Components/Common/Timebelt";
import List from "../Components/Driverlocation/List";
import "./css/Driverlocation.css";
import { apiInstance } from "../api/index";
import getDriverList from "../api/GetDriverList";
import MyResponsiveLine from "../Components/Driverlocation/MyResponsiveLine";
import drc from "../asset/image/driverlocation-chart.gif";
import drl from "../asset/image/driverlocation-location.gif";
import drt from "../asset/image/driverlocation-time.gif";
const Driverlocation = () => {
  let today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜

  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`,
  });

  const [timebelt, setTimebelt] = useState({ timebelt: "" });
  const [listData, setListData] = useState([]);
  const [loading, setLoading] = useState(true);
  const API = apiInstance();

  useEffect(() => {
    if (selected.localCity && selected.localSchool && selected.date) {
      if (`${year}-${month}-${date}` === selected.date) {
        // console.log("선택한 날짜는 오늘")
        async function getData() {
          const res = await apiInstance().post("spot/current", selected);
          setListData(res.data);
          // console.log(res.data);
          setLoading(false);
        }
        getData();
      } else {
        // console.log("선택한 날짜는 오늘이 아님")
        async function getData() {
          const res = await apiInstance().post("spot/log", selected);
          setListData(res.data);
          // console.log(res.data);
          setLoading(false);
        }
        getData();
      }
    }
  }, [selected]);

  return (
    <div className="driver-location-container">
      <div className="driver-location-title">실시간 모니터링</div>
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
        {/* <Timebelt timbelt={timebelt} setTimebelt={setTimebelt}/> */}
      </div>
      {loading ? (
        <div className="all-img">
          <div className="gif-container1">
            <div className="gif-img-con">
              <img className="gif-img" src={drt} />
            </div>
            <h2>실제 도착 시각 확인</h2>
            <div className="text">
              기사님이 픽업/배달존에
              <br />
              도착한 시각을 알 수 있어요.
            </div>
          </div>
          <div className="gif-container2">
            <div className="gif-img-con">
              <img className="gif-img" src={drl} />
            </div>
            <h2>기사님의 현재 위치</h2>
            <div className="text">
              기사님의 현재 위치를
              <br />
              실시간으로 확인해보세요.
            </div>
          </div>
          <div className="gif-container1">
            <div className="gif-img-con">
              <img className="gif-img" src={drc} />
            </div>
            <h2>오차 그래프 제공</h2>
            <div className="text">
              오차 그래프를 바탕으로 <br />
              경로 수정에 참고해보세요.
            </div>
          </div>
        </div>
      ) : (
        <List
          className="driverlocation-list"
          listData={listData}
          timebelt={timebelt}
        />
      )}
    </div>
  );
};
export default Driverlocation;
