import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import withReactContent from "sweetalert2-react-content";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import TextField from "@mui/material/TextField";
import "./css/Signup.css";
import { duplicateId } from "../api/UserAPI";
import Toast from "../utils/Toast";
import { AiFillCheckCircle } from "react-icons/ai";
import { apiInstance } from "../api/index";
import axios from 'axios';
const Signup = () => {
  const [info, setInfo] = useState({
    userName: "",
    phone: "",
    localCity: "",
    localSchool: "",
    userId: "",
    password: "",
  });

  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [checkId, setCheckId] = useState(false);
  const [checkPassword, setCheckPassword] = useState(false);
  const API = apiInstance();
  const navigate = useNavigate();
  const MySwal = withReactContent(Swal);
  // 비밀번호 확인
  useEffect(() => {
    if (info.password === passwordConfirm) {
      setCheckPassword(true);
    } else {
      setCheckPassword(false);
    }
    console.log(checkPassword);
  }, [passwordConfirm]);

  useEffect(() => {
    setCheckId(false);
  },[info.userId])
  const [schoolList, setSchoolList] = useState([]);

  const onChange = (e) => {
    console.log(e);
    const nextInfo = {
      ...info, // 기존의 값 복사 (spread operator)
      [e.target.name]: e.target.value, // 덮어쓰기
    };
    console.log(nextInfo);
    setInfo(nextInfo);
    if (nextInfo.localCity === "서울") {
      setSchoolList(schoolSeoul);
    } else if (nextInfo.localCity === "광주") {
      setSchoolList(schoolGwangju);
    } else if (nextInfo.localCity === "수원") {
      setSchoolList(schoolSuwon);
    } else if (nextInfo.localCity === "인천") {
      setSchoolList(schoolIncheon);
    }
  };

  const schoolSeoul = [
    "건국대학교",
    "경희대학교",
    "마포구공유오피스",
    "서울교육대학교",
    "서울대학교",
    "서울시립대학교",
    "연세대학교",
    "이화여자대학교",
    "카이스트경영대학",
    "한국외국어대학교",
    "한성대학교",
  ];
  const schoolSuwon = ["성균관대학교(자연과학캠퍼스)"];
  const schoolIncheon = ["송도 글로벌캠퍼스", "연세대학교(송도)"];

  const schoolGwangju = ["광주과학기술원", "전남대학교"];

  // 회원 가입
  async function signupSubmit(e) {
    e.preventDefault();
    if (checkId === true && checkPassword === true) {
      try {
        await API.post("admin/createdriver", info);
        await MySwal.fire({
          icon: "success",
          title: "회원가입 성공!",
        });
        await new Promise(() => {
          navigate("/");
        });
      } catch (error) {
        MySwal.fire({
          icon: "error",
          title: "Oops...",
          text: "오류가 발생하였습니다.",
        });
      }
    } else {
      MySwal.fire({
        icon: "warning",
        title: "삐~~",
        text: `${[!checkId && "아이디를", !checkPassword && "비밀번호를"]
          .filter((text) => text.length > 0)
          .join(", ")} 확인하세요`,
      });
    }
  }

  return (
    <div className="signup-container">
      <form className="signup-form" method="post" onSubmit={signupSubmit}>
        <h2 className="signup-title">SIGN UP</h2>
        <TextField
          required
          id="name"
          name="userName"
          label="Name"
          variant="standard"
          value={info.userName}
          onChange={onChange}
          sx={{
            // " .MuiInputLabel-root": {
            //   color: "white",
            //   border: "1px solid rgba( 255, 255, 255, 0.2 )",
            // },
            m: 1,
            minWidth: 310,
            width: "80%"
          }}
        />
        <TextField
          required
          id="phoneNumber"
          name="phone"
          label="Phone Number"
          variant="standard"
          value={info.phone}
          onChange={onChange}
          sx={{ m: 1, minWidth: 310, width: "80%" }}
        />
        <div>
          <FormControl required variant="standard" sx={{ m: 1, minWidth: 100 }}>
            <InputLabel id="demo-simple-select-standard-label">City</InputLabel>
            <Select
              labelId="demo-simple-select-standard-label"
              id="demo-simple-select-standard"
              value={info.localCity}
              onChange={onChange}
              label="City"
              name="localCity"
            >
              <MenuItem value={"서울"}>서울</MenuItem>
              <MenuItem value={"광주"}>광주</MenuItem>
              <MenuItem value={"인천"}>인천</MenuItem>
              <MenuItem value={"수원"}>수원</MenuItem>
            </Select>
          </FormControl>
          <FormControl required variant="standard" sx={{ m: 1, minWidth: 280 }}>
            <InputLabel id="demo-simple-select-standard-label">
              School
            </InputLabel>
            <Select
              labelId="demo-simple-select-standard-label"
              id="demo-simple-select-standard"
              value={info.localSchool}
              onChange={onChange}
              label="School"
              name="localSchool"
            >
              {schoolList.map((school) => (
                <MenuItem key={school} value={school}>
                  {school}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </div>
        <div className="signup-id">
          <TextField
            required
            id="standard-disabled"
            label="ID"
            name="userId"
            value={info.userId}
            variant="standard"
            onChange={onChange}
            sx={{ m: 1, minWidth: 330 }}
          />
          {checkId ? (
            <div className="signup-check-confirm">
              <AiFillCheckCircle />
            </div>
          ) : (
            <div
              className="signup-check"
              onClick={() => {
                duplicateId(info.userId, Toast, setCheckId);
              }}
            >
              중복확인
            </div>
          )}
        </div>
        <TextField
          required
          id="password"
          label="Password"
          name="password"
          type="password"
          autoComplete="current-password"
          variant="standard"
          value={info.password}
          onChange={onChange}
          sx={{ m: 1, minWidth: 310, width: "80%" }}
        />
        <TextField
          required
          id="password"
          label="Password Confirm"
          type="password"
          autoComplete="current-password"
          variant="standard"
          value={passwordConfirm}
          onChange={(e) => {
            setPasswordConfirm(e.target.value);
          }}
          sx={{ m: 1, minWidth: 310, width: "80%" }}
        />
        <button className="signup-button">DONE</button>
      </form>
    </div>
  );
};

export default Signup;