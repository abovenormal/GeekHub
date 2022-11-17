import { useEffect, useState } from "react";
// import { authInstance } from ".";
import { apiInstance } from ".";

export default function GetLocations(url) {
  const [ data, setData ] = useState([]);

  useEffect(() => {
    async function getData() {
      // const res = await authInstance().get(url);
      const res = await apiInstance().get(url)
      setData(res.data)
    }
    getData();
  }, [url])
  return data
}
