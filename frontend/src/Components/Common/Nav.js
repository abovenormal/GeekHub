import React, { useState } from "react";
import { Link, Navigate, useLocation } from "react-router-dom";
import "./css/Nav.css";
import logo from "../../asset/image/logo.png";
import MenuIcon from "@mui/icons-material/Menu";
import HomeIcon from "@mui/icons-material/Home";
import ChatIcon from "@mui/icons-material/Chat";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import SummarizeIcon from "@mui/icons-material/Summarize";
import PersonAddAltIcon from "@mui/icons-material/PersonAddAlt";
import LogoutIcon from "@mui/icons-material/Logout";
import FmdGoodIcon from "@mui/icons-material/FmdGood";
import PostAddIcon from "@mui/icons-material/PostAdd";
import Logout from "@mui/icons-material/Logout";
// import logo from "../../asset/image/logo.png"
const Nav = () => {
  const [nowActive, setNowActive] = useState("overview");
  const location = useLocation();
  const [expanded, setExpanded] = useState(true);
  return (
    // <nav className={`navigation ${expanded && "expanded"}`}>
    <nav className={`sidebar ${expanded && "expanded"}`}>
      <div>
        <Link
          className={"nav-menu"}
          onClick={() => {
            setExpanded(!expanded);
          }}
        >
          <MenuIcon className="nav-icon" />
          <div className={`description ${expanded && "show-description"}`}>
            GeekHub
          </div>
        </Link>

        <Link
          className={"nav-link" + (location.pathname === "/" ? " active" : "")}
          to="/"
          onClick={() => {
            setNowActive("overview");
          }}
        >
          <HomeIcon className="nav-icon" />
          <p className={`description ${expanded && "show-description"}`}>
            Overview
          </p>
        </Link>
        <Link
          className={
            "nav-link" +
            (location.pathname === "/driverlocation" ? " active" : "")
          }
          onClick={() => {
            setNowActive("driverlocation");
          }}
          to="driverlocation"
        >
          <LocalShippingIcon className="nav-icon"></LocalShippingIcon>
          <p className={`description ${expanded && "show-description"}`}>
            실시간 모니터링
          </p>
        </Link>
        <Link
          className={
            "nav-link" + (location.pathname === "/drivermap" ? " active" : "")
          }
          onClick={() => {
            setNowActive("drivermap");
          }}
          to="drivermap"
        >
          <FmdGoodIcon className="nav-icon"></FmdGoodIcon>
          <p className={`description ${expanded && "show-description"}`}>
            드라이버 현재 위치
          </p>
        </Link>
        <Link
          className={
            "nav-link" + (location.pathname === "/chat" ? " active" : "")
          }
          onClick={() => {
            setNowActive("chat");
          }}
          to="chat"
        >
          <ChatIcon className="nav-icon"></ChatIcon>
          <p className={`description ${expanded && "show-description"}`}>
            채팅
          </p>
        </Link>
        <Link
          className={
            "nav-link" + (location.pathname === "/log" ? " active" : "")
          }
          onClick={() => {
            setNowActive("log");
          }}
          to="log"
        >
          <SummarizeIcon className="nav-icon"></SummarizeIcon>
          <p className={`description ${expanded && "show-description"}`}>
            로그 확인
          </p>
        </Link>
        <Link
          className={
            "nav-link" + (location.pathname === "/worklist" ? " active" : "")
          }
          onClick={() => {
            setNowActive("worklist");
          }}
          to="worklist"
        >
          <PostAddIcon className="nav-icon"></PostAddIcon>
          <p className={`description ${expanded && "show-description"}`}>
            업무 관리
          </p>
        </Link>
        <Link
          className={
            "nav-link" + (location.pathname === "/signup" ? " active" : "")
          }
          onClick={() => {
            setNowActive("signup");
          }}
          to="signup"
        >
          <PersonAddAltIcon className="nav-icon"></PersonAddAltIcon>
          <p className={`description ${expanded && "show-description"}`}>
            드라이버 등록
          </p>
        </Link>
      </div>
      <div>
        <Link
          className="logout"
          onClick={() => {
            window.localStorage.setItem("accesstoken", "");
            Navigate("/");
          }}
        >
          <LogoutIcon className="nav-icon"></LogoutIcon>
          <p className={`description ${expanded && "show-description"}`}>
            로그아웃
          </p>
        </Link>
      </div>
    </nav>
  );
};

export default Nav;
