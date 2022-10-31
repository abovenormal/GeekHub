import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./css/Nav.css";

import HomeIcon from "@mui/icons-material/Home";
import ChatIcon from "@mui/icons-material/Chat";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import SummarizeIcon from "@mui/icons-material/Summarize";
import PersonAddAltIcon from "@mui/icons-material/PersonAddAlt";
const Nav = () => {
  const [nowActive, setNowActive] = useState("overview");
  return (
    <nav className="navigation">
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
        회원 가입
      </Link>
      <Link className="nav-link" to="user">
        회원 관리
      </Link>
      <Link className="nav-link" to="login">
        로그인
      </Link>
    </nav>
  );
};

export default Nav;
