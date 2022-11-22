import React, { useState, useEffect } from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import "./css/Dropdown.css";
import { apiInstance } from "../../api/index";
import Datepicker from "./Datepicker";
import cityJson from "../Kakaomap/city.json";
import schoolJson from "../Kakaomap/school.json";
import Button from "@mui/material/Button";
import { Map, MapMarker, Polyline } from "react-kakao-maps-sdk";

const Dropdown = (props) => {
  const selected = props.selected;
  const setSelected = props.setSelected;
  const [schoolList, setSchoolList] = useState([]);
  const [driverList, setDriverList] = useState([]);
  const [hourList, setHourList] = useState([]);
  const [minList, setMinList] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [state, setState] = useState({
    center: { lat: 35.19919101818564, lng: 126.87300478078876 },
    isPanto: false,
    level: 7,
  });
  const [preSchool, setPreSchool] = useState("");
  const [preCity, setPreCity] = useState("");
  useEffect(() => {
    console.log(selected)
    for (let i = 0; i < cityJson.length; i++) {

      if (cityJson[i].localCity == selected.localCity) {

        setState((prev) => {
          return {
            ...prev,
            center: {
              lat: cityJson[i].center.lat,
              lng: cityJson[i].center.lng,
            },
            level: cityJson[i].level,
          };
        });
        if (preSchool == selected.localSchool) {
          break;
        }
        for (let j = 0; j < schoolJson.length; j++) {
          if (schoolJson[j].localSchool == selected.localSchool) {
            console.log(selected.localSchool);
            setState((prev) => {
              return {
                ...prev,
                center: {
                  lat: schoolJson[j].center.lat,
                  lng: schoolJson[j].center.lng,
                },
                level: schoolJson[j].level,
              };
            });
            setPreSchool(selected.localSchool);
            setPreCity(selected.localCity)
            break;
          }
        }
        setPreSchool(selected.localSchool);
        setPreCity(selected.localCity)
        break;
      }
    }
  }, [selected]);

  useEffect(() => {
    let result = [];
    for (let i = 0; i < 24; i++) {
      result.push(i);
    }
    setHourList(result);
    result = [];
    for (let i = 0; i < 60; i++) {
      result.push(i);
    }
    setMinList(result);
    result = [];
    result.push("STORE");
    result.push("DESTINATION");
    result.push("HUB");
    setCategoryList(result);
  }, []);

  useEffect(() => {
    if (selected.localCity && selected.localSchool) {
      async function getUser() {
        const res = await apiInstance().post("spot/current", selected);
        let result = [];
        for (let i = 0; i < res.data.length; i++) {
          let item = res.data[i];
          if (item.userName != null) {
            result.push(item);
          }
        }
        setDriverList(result);
      }
      getUser();
    }
  }, [selected]);

  const onChange = (e) => {
    // console.log(e);

    const nextInfo = {
      ...selected,
      [e.target.name]: e.target.value,
    };
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
  const schoolGwangju = ["광주과학기술원", "전남대학교", "SSAFY"];
  return (
    <div className="dropdown">
      <FormControl variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">지역</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.localCity}
          onChange={(e) => {
            onChange(e);
            setSelected((prev) => {
              return {
                ...prev,
                localSchool: "",
              };
            });
          }
          }
          label="지역"
          name="localCity"
        >
          <MenuItem value={"서울"}>서울</MenuItem>
          <MenuItem value={"광주"}>광주</MenuItem>
          <MenuItem value={"인천"}>인천</MenuItem>
          <MenuItem value={"수원"}>수원</MenuItem>
        </Select>
      </FormControl>
      <FormControl variant="standard" sx={{ m: 1, minWidth: 200 }}>
        <InputLabel id="demo-simple-select-standard-label">장소</InputLabel>
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
      <FormControl variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">드라이버</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.driver}
          onChange={(e) => {
            console.log(e);
            setSelected((prev) => {
              return {
                ...prev,
                driver: e.target.value,
              };
            });
            console.log(e.target.value);
          }}
          label="드라이버"
          name="driver"
        >
          {driverList.map((d) => (
            <MenuItem key={d.userIdx} value={d.userName}>
              {d.userName}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <Datepicker selected={selected} setSelected={setSelected} />
      <FormControl size="small" variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">시간</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.hour}
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                hour: e.target.value,
              };
            });
            console.log(e.target.value);
          }}
          label="시간"
          name="hour"
        >
          {hourList.map((hour) => (
            <MenuItem key={hour} value={hour}>
              {hour}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl
        className="label"
        variant="standard"
        size="small"
        sx={{ m: 1, minWidth: 100 }}
      >
        <InputLabel id="demo-simple-select-standard-label">분</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.min}
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                min: e.target.value,
              };
            });
            console.log(e.target.value);
          }}
          label="분"
          name="min"
          style={{ maxHeight: 300 }}
        >
          {minList.map((min) => (
            <MenuItem key={min} value={min}>
              {min}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl
        className="label1"
        variant="standard"
        size="small"
        sx={{ m: 1, minWidth: 100 }}
      >
        <InputLabel id="demo-simple-select-standard-label">분류</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="demo-simple-select-standard"
          value={selected.category}
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                category: e.target.value,
              };
            });
          }}
          label="분류"
          name="category"
          style={{ maxHeight: 300 }}
        >
          {categoryList.map((category, index) => (
            <MenuItem key={index} value={category}>
              {category}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <div class="col-3">
        <input
          class="effect-1"
          type="text"
          placeholder="가게명"
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                storename: e.target.value,
              };
            });
          }}
        />
        <span class="focus-border"></span>
      </div>
      <div class="col-3">
        <input
          class="effect-1"
          type="text"
          placeholder="개수"
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                count: e.target.value,
              };
            });
          }}
        />
        <span class="focus-border"></span>
      </div>
      <div class="col-3">
        <input
          class="effect-1"
          type="text"
          placeholder="위도"
          value={selected.lat}
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                lat: e.target.value,
              };
            });
          }}
        />
        <span class="focus-border"></span>
      </div>

      <div class="col-3">
        <input
          class="effect-1"
          type="text"
          placeholder="경도"
          value={selected.lng}
          onChange={(e) => {
            setSelected((prev) => {
              return {
                ...prev,
                lng: e.target.value,
              };
            });
          }}
        />
        <span class="focus-border"></span>
      </div>
      <Map
        center={state.center}
        level={state.level}
        style={{
          // 지도의 크기
          width: "100%",
          height: "450px",
        }}
        onClick={(_t, mouseEvent) =>
          setSelected((prev) => {
            return {
              ...prev,
              lat: mouseEvent.latLng.getLat(),
              lng: mouseEvent.latLng.getLng(),
            };
          })

        }
      >
        {selected && <MapMarker position={selected} />}
      </Map>
    </div >
  );
};
export default Dropdown;
