import React from 'react';
import { Link } from "react-router-dom";
import "./css/Shortcut.css";
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import scl from "../../asset/image/shortcut-log.gif"
const ShortcutLog = () => {
  return (
    <Link
        className="dashboard-links"
        to="log"
      >
    <div className="shortcut-box">
      <img src={scl} className="sc-img"></img>
      <h2>로그 확인</h2>
      <ArrowForwardIosIcon style={{paddingRight: "0.5rem"}}></ArrowForwardIosIcon>
    </div>
    </Link>
  );
};

export default ShortcutLog;