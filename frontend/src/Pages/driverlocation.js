import React, {useState,useEffect} from "react";
import Dropdown from "../Components/Common/Dropdown";
import DatePicker from "../Components/Common/Datepicker";
import List from "../Components/Driverlocation/List"
import "./css/Driverlocation.css";
import { apiInstance } from "../api/index";


const Driverlocation = () => {
  const [selected, setSelected] = useState({
    city: "",
    school: "",
    date: "",
  })
  const API = apiInstance();
  async function GetLocation(e) {
    e.preventDefault();
    try {
      const res = await API.get("/location/getLocation?=driver=221343")
      console.log(res.data)
    }
    catch (err) {
      console.log(err)
    }
  }
  useEffect(()=>{
    if (selected.city && selected.school && selected.date) {
      alert('작성 완료!')
      // 기사 리스트 불러오기 (getDriverList)
      // 해당 res를 <List /> prop 전달
      // List는 기사를 누를 때마다 해당 기사의 위치 정보 불러오기 (getLocation)
      // const res = GetLocations("");
      // console.log(res)
    }
    // console.log(selected)
  },[selected])
  return (
    <div className="container">
      <div className="picker">
        <Dropdown selected={selected} setSelected={setSelected}/>
        <DatePicker selected={selected} setSelected={setSelected}/>
      </div>
      <button onClick={GetLocation}>불러오기</button>
      <List />
    </div>
  );
};
export default Driverlocation;