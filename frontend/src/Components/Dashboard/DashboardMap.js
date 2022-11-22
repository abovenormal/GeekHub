/* global kakao */
import React, { useEffect, useState } from "react";
import { apiInstance } from "../../api/index";
import "./css/DashboardMap.css";
import { Map, MapMarker, CustomOverlayMap } from "react-kakao-maps-sdk";
import drl from "../../asset/image/driverlocation-location.gif";
import markerImg from "../../asset/image/marker.png";
import { Link, Navigate } from "react-router-dom";
function DashboardMap() {
  const [data, setData] = useState([]);
  const [state, setState] = useState({
    center: { lat: 35.18919101818564, lng: 126.81300478078876 },
    isPanto: false,
    level: 7,
  });

  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
  });
  const [preSchool, setPreSchool] = useState("");
  async function getData() {
    try {
      const res = await apiInstance().get("spot/workingDriver");
      console.log(res.data);
      setData(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  setInterval(() => {
    getData();
    console.log(new Date());
  }, 30000);
  useEffect(() => {
    getData();
  }, []);

  useEffect(() => {
    console.log(selected);
    console.log(state);
    console.log(preSchool);
    
  }, [selected]);

  return (
    <div className="maps-container">
      <div className="map-labels">
        <div className="labels">드라이버의 현재 위치 확인하기</div>
      </div>
      <Link
        to="drivermap">   
        <Map
          center={state.center}
          isPanto={state.isPanto}
          level={state.level}
          className="map"
          draggable={false}
        >
          {data.map((driver, index) => (
            <>
              <CustomOverlayMap // 커스텀 오버레이를 표시할 Container
                // 커스텀 오버레이가 표시될 위치입니다
                position={{
                  lat: driver.lat,
                  lng: driver.lon,
                }}
              >
                {/* 커스텀 오버레이에 표시할 내용입니다 */}
                <div className="label" style={{ color: "#0e1737" }}>
                  <span className="label-span">{driver.userName}</span>
                </div>
              </CustomOverlayMap>
              <MapMarker
                key={`${driver.userName}`}
                position={{
                  lat: driver.lat,
                  lng: driver.lon,
                }} // 마커를 표시할 위치
                image={{
                  src: markerImg,
                  size: {
                    width: 24,
                    height: 35,
                  },
                }}
                title={driver.userName} // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
              ></MapMarker>
            </>
          ))}
        </Map>
      </Link>
      <a className="dashboard-link">바로 확인하러 가기 </a>
    </div>
  );
}
export default DashboardMap;
