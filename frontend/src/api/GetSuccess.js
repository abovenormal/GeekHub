import { useEffect, useState } from "react";
import { apiInstance } from ".";

export default function GetSuccess(url) {
  const [ data, setData ] = useState([]);

  useEffect(() => {
    async function getData() {
      // const res = await authInstance().get(url);
      const res = await apiInstance().get(url)
      console.log(res)
      setData(res.data)
    }
    getData();
  }, [url])
  return data
}
