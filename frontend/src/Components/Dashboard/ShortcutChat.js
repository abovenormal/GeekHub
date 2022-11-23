import React from 'react';
import {Link} from "react-router-dom";
import scc from "../../asset/image/shortcut-chat.gif"
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import "./css/Shortcut.css"
const ShortcutChat = () => {
  return (
    <Link
          className="dashboard-links"
          to="chat"
        >
      <div className="shortcut-box">
        <img src={scc} className="sc-img"></img>
        <h2>채팅</h2>
        <ArrowForwardIosIcon style={{paddingRight: "0.5rem"}}></ArrowForwardIosIcon>
      </div>
    </Link>
  );
};

export default ShortcutChat;