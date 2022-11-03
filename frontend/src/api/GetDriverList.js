import { apiInstance } from ".";

const API = apiInstance();

// info(지역, 학교, 날짜) 보내고 해당 지역학교 기사들 리스트, 태스크 가져오기
export async function getDriverList(info) {
  try {
    console.log(info)
    // const res = await API.post('logger/driver', info);
    // console.log(res.data)
    // return res
  } 
  catch (error) {
    console.log(error);
  };
}