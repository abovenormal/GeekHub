import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
import logo from "../asset/image/logo.png";
import Toast from "../utils/Toast"
import { useNavigate } from "react-router-dom";
import { apiInstance } from "../api";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import "./css/Login.css";
const Login = () => {
  
  // const api = apiInstance();
  const navigate = useNavigate();
  const MySwal = withReactContent(Swal);


  const [loginInfo, setLoginInfo] = useState({ id: "", password: "" });
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
      // const res = await api.post('/login', loginInfo);
      // localStorage.setItem('accesstoken', res.data.accessToken);
      
      await new Promise(() => {
        navigate('/');
        // navigate(0);
        Toast.fire({
          icon: "success",
          title: "로그인 성공!",
          timer: 1500
        })
      })
    } catch (error) {
  if (error.response.status === 401) {
    MySwal.fire({
      icon: "error",
      title: "비밀번호 오류",
      text: "비밀번호가 올바르지 않습니다."
    })
  } else if (error.response.status === 500) {
    MySwal.fire({
      icon: "question",
      title: "잘못된 ID",
      text: "입력한 아이디를 사용하는 계정을 찾을 수 없습니다."
    })
  }
    }
  };
  return (
    <div className="login-container">
      <div className="login-left">
        <div className="logo">
          <img className="logo-img" src={logo}></img>
          <h3 className="logo-name">GeekHub</h3>
        </div>
      </div>
      <form
        className="login-form"
        // method="post"
        onSubmit={loginSubmit}
      >
        <h4>Welcome to GeekHub! 👋🏻</h4>
        <TextField
          className="text-field"
          id="id"
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
