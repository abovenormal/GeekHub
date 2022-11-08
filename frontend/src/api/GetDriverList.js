import { useState, useEffect } from "react";
import { apiInstance } from "./index";

// info(지역, 학교, 날짜) 보내고 해당 지역학교 기사들 리스트, 태스크 가져오기
export default function getDriverList(info) {
  const [listData, setListData] = useState([]);
  // try {
  //   console.log(info)
  //   const res = await API.post('spot/log', info);
  //   console.log(res.data)
  //   setListData(res.data)
  //   return listData
  // } 
  // catch (error) {
  //   console.log(error);
  // };

  useEffect(() => {
    async function getData() {
      const res = await apiInstance().post('spot/log', info);
      setListData(res.data)
    }
    getData();
  }, [info])
  return listData
}