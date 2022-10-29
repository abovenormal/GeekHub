import React from 'react';
import { Link } from "react-router-dom";
import "./css/Nav.css";

import HomeIcon from '@mui/icons-material/Home';
import ChatIcon from '@mui/icons-material/Chat';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import SummarizeIcon from '@mui/icons-material/Summarize';
import PersonAddAltIcon from '@mui/icons-material/PersonAddAlt';
const Nav = () => {
  return (
    <nav className="navigation">
      <Link
        className="nav-link"
        to="/">
        <HomeIcon className="nav-icon"/>
        Overview
      </Link>
      <Link
        className="nav-link"
        to="chat">
        <ChatIcon className="nav-icon"></ChatIcon>
        채팅
      </Link>
      <Link
        className="nav-link"
        to="driverlocation">
        <LocalShippingIcon className='nav-icon'></LocalShippingIcon>
        배달 현황 관리
      </Link>
      <Link
        className="nav-link"
        to="log">
          <SummarizeIcon className='nav-icon'></SummarizeIcon>
        로그 확인
      </Link>
      <Link
        className="nav-link"
        to="user">
        회원 관리
      </Link>
      <Link
        className="nav-link"
        to="login">
        로그인
      </Link>
      <Link
        className="nav-link"
        to="signup"
      >
        <PersonAddAltIcon className='nav-icon'></PersonAddAltIcon>
        회원 가입
      </Link>
    </nav>
  );
};

export default Nav;