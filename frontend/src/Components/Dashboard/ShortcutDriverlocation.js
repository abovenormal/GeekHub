import React from 'react';
import {Link} from "react-router-dom";
import scl from "../../asset/image/shortcut-mon.gif"
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import "./css/Shortcut.css"
const ShortcutDriverlocation = () => {
  return (
    <Link
          className="dashboard-links"
          to="driverlocation"
        >
      <div className="shortcut-box">
        <img src={scl} className="sc-img"></img>
        <h2>실시간 <br /> 모니터링</h2>
        <ArrowForwardIosIcon style={{paddingRight: "0.5rem"}}></ArrowForwardIosIcon>
      </div>
    </Link>
  );
};

export default ShortcutDriverlocation;