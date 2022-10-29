import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { apiInstance } from "../api";
// import Swal from "sweetalert2";
// import withReactContent from "sweetalert2-react-content";
// import Toast from "../Utils/Toast";
import './css/Login.css'
const Login = () => {
  const [loginInfo, setLoginInfo] = useState({id: "", password: ""});
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
      <form
        className="login-form"
        method="post"
        // onSubmit={loginSubmit}
      >
        <label className="login-input-label">아이디</label>
        <input
          className="login-input"
          type="text"
          id="id"
          placeholder="ID"
          value={loginInfo.id}
          // onChange={loginInput}
          required 
        />
        <label className="login-input-label">비밀번호</label>
        <input
          className="login-input"
          type="password"
          id="password"
          placeholder="PASSWORD"
          value={loginInfo.password}
          // onChange={loginInput}
          required 
        />
        <button className="login-button">로그인</button>
      </form>
    </div>
  );
};
export default Login;