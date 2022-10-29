import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import Input from "@mui/material/Input";
// import { useNavigate } from "react-router-dom";
// import { apiInstance } from "../api";
// import Swal from "sweetalert2";
// import withReactContent from "sweetalert2-react-content";
// import Toast from "../Utils/Toast";
import "./css/Login.css";
const Login = () => {
  const [loginInfo, setLoginInfo] = useState({ id: "", password: "" });
  // const api = apiInstance();
  // const navigate = useNavigate();
  // const MySwal = withReactContent(Swal);

  // function loginInput({target: {id, value}}) {
  //   const newLoginInfo = {
  //     ...loginInfo,
  //     [id]: value,
  //   };
  //   setLoginInfo(newLoginInfo);
  // };

  // async function loginSubmit(e) {
  //   e.preventDefault();
  //   try {
  //     const res = await api.post('/auth/login', loginInfo);
  //     localStorage.setItem('accesstoken', res.data.accessToken);
  //     await MySwal.fire({
  //       icon: "success",
  //       title: "로그인 성공!"
  //     })
  //     await new Promise(() => {
  //       navigate('/');
  //       navigate(0);
  //     })
  //   } catch (error) {
  // if (error.response.status === 401) {
  //   Toast.fire({
  //     icon: "error",
  //     title: "비밀번호 오류",
  //     text: "비밀번호가 올바르지 않습니다."
  //   })
  // } else if (error.response.status === 500) {
  //   Toast.fire({
  //     icon: "question",
  //     title: "잘못된 ID",
  //     text: "입력한 아이디를 사용하는 계정을 찾을 수 없습니다."
  //   })
  // }
  //   }
  // };
  return (
    <div className="login-container">
      <div className="login-left"></div>
      <form
        className="login-form"
        method="post"
        // onSubmit={loginSubmit}
      >
        <h3>Welcome to Geekhub!</h3>
        <TextField
          className="text-field"
          id="password"
          placeholder="ID"
          sx={{
            " .MuiOutlinedInput-root": {
              color: "#d1d5db",
              border: "1px solid rgba( 255, 255, 255, 0.2 )",
              marginBottom: "1rem"
            },
          }}
        />
        <TextField
          className="text-field"
          id="password"
          placeholder="Password"
          type="password"
          sx={{
            " .MuiOutlinedInput-root": {
              color: "#d1d5db",
              border: "1px solid rgba( 255, 255, 255, 0.2 )",
            },
          }}
        />
        <button className="login-button">LOGIN</button>
      </form>
    </div>
  );
};
export default Login;
