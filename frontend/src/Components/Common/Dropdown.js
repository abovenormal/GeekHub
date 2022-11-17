import React, { useState, useEffect } from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import "./css/Dropdown.css"
const Dropdown = props => {
  const selected = props.selected
  const setSelected = props.setSelected
  const [schoolList, setSchoolList] = useState([]);
  
  const onChange = (e) => {
    // console.log(e);
    const nextInfo = {
      ...selected,
      [e.target.name]: e.target.value
    }
    setSelected(nextInfo);
    if (nextInfo.localCity === "서울") {
      setSchoolList(schoolSeoul);
    } else if (nextInfo.localCity === "광주") {
      setSchoolList(schoolGwangju);
    } else if (nextInfo.localCity === "수원") {
      setSchoolList(schoolSuwon);
    } else if (nextInfo.localCity === "인천") {
      setSchoolList(schoolIncheon);
    }
  }

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

  const schoolGwangju = ["광주과학기술원", "전남대학교", "SSAFY"];
  return (
    <div className="dropdown">
      <FormControl variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">지역</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.localCity}
          onChange={onChange}
          label="지역"
          name="localCity"
        >
          <MenuItem value={"서울"}>서울</MenuItem>
          <MenuItem value={"광주"}>광주</MenuItem>
          <MenuItem value={"인천"}>인천</MenuItem>
          <MenuItem value={"수원"}>수원</MenuItem>
        </Select>
      </FormControl>
      <FormControl variant="standard" sx={{ m: 1, minWidth: 250 }}>
        <InputLabel id="demo-simple-select-standard-label">
          장소
        </InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.localSchool}
          onChange={onChange}
          label="장소"
          name="localSchool"
        >
          {schoolList.map((localSchool) => (
            <MenuItem key={localSchool} value={localSchool}>
              {localSchool}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
};
export default Dropdown;
