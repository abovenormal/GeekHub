import React from 'react';
import { Link } from "react-router-dom";
import sct from "../../asset/image/shortcut-task.gif"
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import "./css/Shortcut.css"
const ShortcutTask = () => {
  return (
    <Link
        className="dashboard-links"
        to="chat"
      >
    <div className="shortcut-box">
      <img src={sct} className="sc-img"></img>
      <h2>업무 관리</h2>
      <ArrowForwardIosIcon style={{paddingRight: "0.5rem"}}></ArrowForwardIosIcon>
    </div>
    </Link>
  );
};

export default ShortcutTask;