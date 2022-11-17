import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import List from "../Components/Log/List";
import "./css/Log.css";
import { getDriverList } from "../api/GetDriverList";
import { apiInstance } from "../api/index"
import drg from "../asset/image/driverlocation-gps.gif";
import drp from "../asset/image/driverlocation-picture.gif";
const Log = () => {
  let today = new Date();   
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1;  // 월
  let date = today.getDate();  // 날짜
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`
  });
  const [listData, setListData] = useState([]);
  useEffect(() => {
    if (selected.localCity && selected.localSchool && selected.date) {
      if (`${year}-${month}-${date}` === selected.date) {
        // console.log("선택한 날짜는 오늘")
        async function getData() {
          const res = await apiInstance().post('spot/current', selected);
          setListData(res.data)
          console.log(res.data);
          setLoading(false);
        }
        getData();}
      else {
        // console.log("선택한 날짜는 오늘이 아님")
          async function getData() {
            const res = await apiInstance().post('spot/log', selected);
            setListData(res.data)
            // console.log(res.data);
            setLoading(false);
          }
          getData();
        }
    }
    
    console.log(selected)
  }, [selected])
  return (
    <div className="log-container">
      <div className="log-title">로그 기록 조회</div>
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
      </div>
      {loading ? <div className="all-img">
          <div className="gif-container1">
            <div className="gif-img-con">
              <img className="gif-img" src={drp} />
            </div>
            <h2>사진 데이터 확인</h2>
            <div className="text">
              드라이버가 픽업/배달존에서 찍은
              <br />
              모든 사진을 한눈에 확인할 수 있어요.
            </div>
          </div>
          <div className="gif-container2">
            <div className="gif-img-con">
              <img className="gif-img" src={drg} />
            </div>
            <h2>GPS 데이터 확인</h2>
            <div className="text">
              모든 드라이버의
              <br />
              GPS 데이터를 확인할 수 있어요.
            </div>
          </div>
        </div> : <List selected={selected} listData={listData} />}
      
    </div>
  );
};
export default Log;
