/* global kakao */
import { usePickerState } from "@mui/x-date-pickers/internals/hooks/usePickerState";
import React, { useEffect, useState } from "react";
import { apiInstance } from "../../api/index";
import Dropdown from "../Common/Dropdown";
import "./css/KakaoMap.css";
import { Map, MapMarker, CustomOverlayMap } from "react-kakao-maps-sdk";

function KakaoMap() {
  const [state, setState] = useState({
    center: { lat: 35.19919101818564, lng: 126.87300478078876 },
    isPanto: false,
    level: 7,
  });
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
  });

  useEffect(() => {
    console.log(selected);
    if (selected.localCity == "광주") {
      setState((prev) => {
        return {
          ...prev,
          center: { lat: 35.19919101818564, lng: 126.87300478078876 },
          level: 7,
        };
      });
    } else if (selected.localCity == "서울") {
      setState((prev) => {
        return {
          ...prev,
          center: { lat: 37.540876286520124, lng: 126.98383067483235 },
          level: 9,
        };
      });
    } else if (selected.localCity == "인천") {
      setState((prev) => {
        return {
          ...prev,
          center: { lat: 37.45516317286201, lng: 126.70391641355518 },
          level: 9,
        };
      });
    } else if (selected.localCity == "수원") {
      setState((prev) => {
        return {
          ...prev,
          center: { lat: 37.2614848228648, lng: 127.03042637316096 },
          level: 9,
        };
      });
    }
  }, [selected]);

  return (
    <div>
      <div className="map-label">근무중인 배달기사</div>
      <Dropdown selected={selected} setSelected={setSelected} />

      <Map
        center={state.center}
        isPanto={state.isPanto}
        level={state.level}
        style={{ width: "100%", height: "80vh" }}
      >
        <MapMarker position={{ lat: 33.55635, lng: 126.795841 }}>
          <div style={{ color: "#000" }}>Hello World!</div>
        </MapMarker>
      </Map>
    </div>
  );
}
export default KakaoMap;
