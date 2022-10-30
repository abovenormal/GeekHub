import React, { useState, useEffect } from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import TextField from "@mui/material/TextField";
import Datepicker from "./Datepicker";
const Dropdown = () => {
  const [selected, setSelected] = useState({
    city: "",
    school: "",
    date: "",
  })
  const [schoolList, setSchoolList] = useState([]);
  
  const onChange = (e) => {
    console.log(e);
    const nextInfo = {
      ...selected,
      [e.target.name]: e.target.value
    }
    console.log(nextInfo);
    setSelected(nextInfo);
    if (nextInfo.city === "서울") {
      setSchoolList(schoolSeoul);
    } else if (nextInfo.city === "광주") {
      setSchoolList(schoolGwangju);
    } else if (nextInfo.city === "수원") {
      setSchoolList(schoolSuwon);
    } else if (nextInfo.city === "인천") {
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

  const schoolGwangju = ["광주과학기술원", "전남대학교"];

  const [value, setValue] = React.useState(null);
  return (
    // <Stack
    //   direction="row"
    //   spacing={3}
    //   sx={{ width: 600 }}
    //   divider={<Divider orientation="vertical" flexItem />}
    // >
    //   <Autocomplete
    //     onChange={(e) => {
    //       setSelectedLocation(e.target.innerText);
    //       console.log(selectedLocation);
    //     }}
    //     id="size-small-standard"
    //     size="small"
    //     sx={{ width: 200 }}
    //     options={locations}
    //     getOptionLabel={(option) => option.location}
    //     // defaultValue={locations[0]}
    //     renderInput={(params) => (
    //       <TextField
    //         {...params}
    //         variant="standard"
    //         label="location"
    //         placeholder="지역"
    //       />
    //     )}
    //   />
    //   <Autocomplete
    //     id="size-small-standard"
    //     size="small"
    //     sx={{ width: 200 }}
    //     options={school}
    //     getOptionLabel={(option) => option.school}
    //     // defaultValue={locations[0]}
    //     renderInput={(params) => (
    //       <TextField
    //         {...params}
    //         variant="standard"
    //         label="school"
    //         placeholder="학교"
    //       />
    //     )}
    //   />

    //   <Datepicker />
    // </Stack>
    <div>
      <FormControl variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">City</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.city}
          onChange={onChange}
          label="City"
          name="city"
        >
          <MenuItem value={"서울"}>서울</MenuItem>
          <MenuItem value={"광주"}>광주</MenuItem>
          <MenuItem value={"인천"}>인천</MenuItem>
          <MenuItem value={"수원"}>수원</MenuItem>
        </Select>
      </FormControl>
      <FormControl variant="standard" sx={{ m: 1, minWidth: 200 }}>
            <InputLabel id="demo-simple-select-standard-label">
              School
            </InputLabel>
            <Select
              labelId="demo-simple-select-standard-label"
              id="demo-simple-select-standard"
              value={selected.school}
              onChange={onChange}
              label="School"
              name="school"
            >
              {schoolList.map((school) => (
                <MenuItem key={school} value={school}>
                  {school}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
    </div>
  );
};
export default Dropdown;
