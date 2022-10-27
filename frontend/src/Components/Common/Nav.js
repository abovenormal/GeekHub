import React from 'react';
import { Link } from "react-router-dom";
import "./css/Nav.css";

const Nav = () => {
  return (
    <nav className="navigation">
      <Link
        className="nav-link"
        to="/">
        Dashboard
      </Link>
      <Link
        className="nav-link"
        to="chat">
        채팅
      </Link>
      <Link
        className="nav-link"
        to="driverlocation">
        배달현황관리
      </Link>
      <Link
        className="nav-link"
        to="log">
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
      >회원 가입
      </Link>
    </nav>
  );
};

export default Nav;