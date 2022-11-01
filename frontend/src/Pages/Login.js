import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
import logo from "../asset/image/logo.png";
import Toast from "../utils/Toast";
import { useNavigate } from "react-router-dom";
import { apiInstance } from "../api";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import "./css/Login.css";
const Login = () => {
  const api = apiInstance();
  const navigate = useNavigate();
  const MySwal = withReactContent(Swal);

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
      console.log(res);
      localStorage.setItem("accesstoken", res.data.accessToken);

      await new Promise(() => {
        navigate("/");
        // navigate(0);
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
        text: "아이디 혹은 비밀번호를 확인해주세요."
      })
    }
  }
  return (
    <div className="login-container">
      <div className="login-left">
        <div className="logo">
          <img className="logo-img" src={logo}></img>
          <h3 className="logo-name">GeekHub</h3>
        </div>
      </div>
      <form className="login-form" method="post" onSubmit={loginSubmit}>
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
  );
};
export default Login;