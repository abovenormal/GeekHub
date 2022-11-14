/* global kakao */
import React, { useState, useEffect } from "react";
import "./css/Drivermap.css";
import KakaoMap from "../Components/Kakaomap/KakaoMap";

const Drivermap = () => {
  return (
    <div className="drivermap">
      <div className="drivermap-container">
        <KakaoMap />
      </div>
    </div>
  );
};
export default Drivermap;
