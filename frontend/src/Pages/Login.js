import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import logo from "../asset/image/logo.png";
import Toast from "../utils/Toast";
import { useNavigate } from "react-router-dom";
import { apiInstance } from "../api";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import { useMediaQuery } from "react-responsive";
import "./css/Login.css";
import loginDelivery from "../asset/image/login-delivery.gif";
import loginVideo from "../asset/image/loginVideo.mp4";
import loginAnalysis from "../asset/image/login-analysis.gif";
const Login = () => {
  const api = apiInstance();
  const navigate = useNavigate();
  const isPc = useMediaQuery({
    query: "(min-width:1024px)",
  });
  const isMobile = useMediaQuery({
    query: "(max-width:767px)",
  });

  const [loginInfo, setLoginInfo] = useState({ userId: "", password: "" });
  const onChange = (e) => {
    console.log(e);
    const nextInfo = {
      ...loginInfo, // 기존의 값 복사 (spread operator)
      [e.target.id]: e.target.value, // 덮어쓰기
    };
    console.log(nextInfo);
    setLoginInfo(nextInfo);
  };

  async function loginSubmit(e) {
    e.preventDefault();
    try {
      console.log(loginInfo);
      const res = await api.post("auth/login", loginInfo);
      console.log(res.data.accessToken);
      localStorage.setItem("accesstoken", res.data.accessToken);
      await new Promise(() => {
        navigate("/");
        navigate(0);
        Toast.fire({
          icon: "success",
          title: "로그인 성공!",
          timer: 1500,
        });
      });
    } catch (error) {
      Toast.fire({
        icon: "error",
        title: "오류",
        text: "아이디 혹은 비밀번호를 확인해주세요.",
      });
    }
  }
  return (
    <div className="login-div">
      {isMobile ? (
        <div className="login-container-mobile">
          <form
            className="login-form-mobile"
            method="post"
            onSubmit={loginSubmit}
          >
            {/* <div className="logo">
            <img className="logo-img-mobile" src={logo}></img>
          </div> */}
            <h4>Welcome to GeekHub! 👋🏻</h4>
            <TextField
              className="text-field"
              id="userId"
              placeholder="ID"
              onChange={onChange}
              sx={{
                " .MuiOutlinedInput-root": {
                  color: "white",
                  border: "1px solid rgba( 255, 255, 255, 0.2 )",
                  marginBottom: "1rem",
                },
              }}
            />
            <TextField
              className="text-field"
              id="password"
              placeholder="Password"
              onChange={onChange}
              type="password"
              sx={{
                " .MuiOutlinedInput-root": {
                  color: "white",
                  border: "1px solid rgba( 255, 255, 255, 0.2 )",
                  marginBottom: "1rem",
                },
              }}
            />
            <button className="login-button">LOGIN</button>
          </form>
        </div>
      ) : (
        <div className="login-container">
          <div className="login-left">
            <div className="logo">
              <img className="logo-img" src={logo}></img>
              <h3 className="logo-name"></h3>
            </div>
            <div className="vc">
              <div className="video-container">
                <video
                  className="login-video"
                  controls
                  loop
                  muted
                  autoPlay
                  data-inline-media=""
                  preload="none"
                >
                  <source src={loginVideo} />
                </video>
                {/* <div className="center"></div> */}
              </div>
            </div>
          </div>
          <div className="login-header">
            <div className="login-title">
            <img src={loginAnalysis} className="login-analysis"></img>
              <h3>
                안녕하세요! 👋🏻<br />
                GeekHub 관리자 페이지입니다!
              </h3>
            </div>
            <form className="login-form" method="post" onSubmit={loginSubmit}>
              {/* <img src={loginDelivery} className="login-delivery"></img> */}
              <TextField
                className="text-field"
                id="userId"
                placeholder="ID"
                onChange={onChange}
                sx={{
                  " .MuiOutlinedInput-root": {
                    color: "black",
                    border: "1px solid rgba( 255, 255, 255, 0.2 )",
                    marginBottom: "1rem",
                  },
                }}
              />
              <TextField
                className="text-field"
                id="password"
                placeholder="Password"
                onChange={onChange}
                type="password"
                sx={{
                  " .MuiOutlinedInput-root": {
                    color: "black",
                    border: "1px solid rgba( 255, 255, 255, 0.2 )",
                    marginBottom: "1rem",
                  },
                }}
              />
              <button className="login-button">LOGIN</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};
export default Login;
