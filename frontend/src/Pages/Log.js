import React, { useState, useEffect } from "react";
import Dropdown from "../Components/Common/Dropdown";
import Datepicker from "../Components/Common/Datepicker";
import List from "../Components/Log/List";
import "./css/Log.css";
import { getDriverList } from "../api/GetDriverList";
import { apiInstance } from "../api/index"
const Log = () => {
  let today = new Date();   
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1;  // 월
  let date = today.getDate();  // 날짜
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
          // console.log(res.data);
        }
        getData();}
      else {
        // console.log("선택한 날짜는 오늘이 아님")
          async function getData() {
            const res = await apiInstance().post('spot/log', selected);
            setListData(res.data)
            // console.log(res.data);
          }
          getData();
        }
    }
    
    console.log(selected)
  }, [selected])
  return (
    <div className="log-container">
      <h1>로그 기록 조회</h1>
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected} />
        <Datepicker selected={selected} setSelected={setSelected} />
      </div>
      <List selected={selected} listData={listData} />
    </div>
  );
};
export default Log;
