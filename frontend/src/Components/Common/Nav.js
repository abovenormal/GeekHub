import React, { useState } from "react";
import { Link, Navigate } from "react-router-dom";
import "./css/Nav.css";

import HomeIcon from "@mui/icons-material/Home";
import ChatIcon from "@mui/icons-material/Chat";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import SummarizeIcon from "@mui/icons-material/Summarize";
import PersonAddAltIcon from "@mui/icons-material/PersonAddAlt";
import LogoutIcon from '@mui/icons-material/Logout';
import Logout from "@mui/icons-material/Logout";
const Nav = () => {
  const [nowActive, setNowActive] = useState("overview");
  return (
    <nav className="navigation">
      <div>
        <Link
          className={"nav-link" + (nowActive === "overview" ? " active" : "")}
          // className={"nav-link"}
          to="/"
          onClick={() => {
            setNowActive("overview");
          }}
        >
          <HomeIcon className="nav-icon" />
          Overview
        </Link>
        <Link
          className={"nav-link" + (nowActive === "chat" ? " active" : "")}
          onClick={() => {
            setNowActive("chat");
          }}
          to="chat"
        >
          <ChatIcon className="nav-icon"></ChatIcon>
          채팅
        </Link>
        <Link
          className={
            "nav-link" + (nowActive === "driverlocation" ? " active" : "")
          }
          onClick={() => {
            setNowActive("driverlocation");
          }}
          to="driverlocation"
        >
          <LocalShippingIcon className="nav-icon"></LocalShippingIcon>
          배달 현황 관리
        </Link>
        <Link
          className={"nav-link" + (nowActive === "log" ? " active" : "")}
          onClick={() => {
            setNowActive("log");
          }}
          to="log"
        >
          <SummarizeIcon className="nav-icon"></SummarizeIcon>
          로그 확인
        </Link>
        <Link className={"nav-link" + (nowActive === "signup" ? " active" : "")}
          onClick={() => {
            setNowActive("signup");
          }} to="signup">
          <PersonAddAltIcon className="nav-icon"></PersonAddAltIcon>
          신규 기사 생성
        </Link>
      </div>
      <div>
      <Link className="logout" onClick={()=>{
        window.localStorage.setItem("accesstoken", "");
        Navigate('/');
      }}>
        <LogoutIcon className="nav-icon"></LogoutIcon> 로그아웃
      </Link>
      </div>
    </nav>
  );
};

export default Nav;
