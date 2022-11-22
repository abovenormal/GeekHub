import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../../api/index";
import Button from "@mui/material/Button";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import "./DataGrid.css";
import DetailDropdown from "../Common/DetailDropdown";
import axios from "axios";
function DataList() {
  let today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜
  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`,
    hour: "",
    min: "",
    spotname: "",
    driver: "",
    userIdx: "",
    lat: "",
    lon: "",
    category: "",
    count: "",
  });

  async function getData() {
    try {
      const res = await apiInstance().get("spot");
      console.log(res.data);
      setData(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  useEffect(() => {
    let result = [];
    let temp = {};
    temp.id = "";
    temp.spotCategory = "";
    temp.spotName = "";
    temp.expectedTime = "2022-01-01 12:00:00";
    temp.arrivedTime = "";
    temp.count = 0;
    temp.lat = "";
    temp.lon = "";
    result.push(temp);
    getData();
    result = [];
    result.push("STORE");
    result.push("HUB");
    result.push("DESTINATION");
    setCategoryList(result);
  }, []);

  useEffect(() => {
    let result = [];
    for (let i = 0; i < data.length; i++) {
      let item = data[i];
      let temp = {};
      temp.id = item.spotIdx;
      temp.spotCategory = item.spotCategory;
      temp.spotName = item.spotName;
      temp.expectedTime = item.expectedTime;
      temp.arrivedTime = item.arrivedTime;
      temp.count = item.count;
      temp.lat = item.lat;
      temp.lon = item.lon;
      result.push(temp);
    }
    setRows(result);
  }, [data]);

  function getExpectedTime(params) {
    return `${params.row.expectedTime.substr(
      0,
      10
    )} ${params.row.expectedTime.substr(11, 18)}`;
  }
  function getArrivedTime(params) {
    if (params.row.arrivedTime == null) {
      return;
    }
    return `${params.row.arrivedTime.substr(
      0,
      10
    )} ${params.row.arrivedTime.substr(11, 18)}`;
  }

  const columns = [
    { field: "id", headerName: "ID", width: 100 },
    {
      field: "spotCategory",
      headerName: "분류",
      width: 100,
      editable: true,
    },
    {
      field: "spotName",
      headerName: "가게 이름",
      width: 200,
      editable: true,
    },
    {
      field: "expectedTime",
      headerName: "도착 예정 시간",
      type: "dateTime",
      width: 200,
      editable: true,
      valueGetter: getExpectedTime,
    },
    {
      field: "count",
      headerName: "수량",
      type: "number",
      width: 150,
      editable: true,
    },
    {
      field: "lat",
      headerName: "위도",
      width: 150,
      editable: true,
    },
    {
      field: "lon",
      headerName: "경도",
      width: 150,
      editable: true,
    },
  ];

  return (
    <div style={{ height: "70vh", width: "80vw" }}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={7}
        rowsPerPageOptions={[5]}
      />
      <div style={{ height: "25vh", width: "80vw" }}>
        <div class="container">
          <div className="newpicker">
            <DetailDropdown selected={selected} setSelected={setSelected} />
          </div>
          <div class="col-3" id="category">
            <FormControl
              className="label"
              variant="standard"
              size="small"
              sx={{ m: 1, minWidth: 100 }}
            >
              <InputLabel id="demo-simple-select-standard-label">
                분류
              </InputLabel>
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
                  console.log(e.target.value);
                }}
                label="분류"
                name="category"
                style={{ maxHeight: 300 }}
              >
                {categoryList.map((category) => (
                  <MenuItem key={category} value={category}>
                    {category}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </div>
          <div class="col-3">
            <input
              class="effect-1"
              type="text"
              placeholder="가게명"
              onChange={(e) => {
                setSelected((prev) => {
                  return {
                    ...prev,
                    spotname: e.target.value,
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
              onChange={(e) => {
                setSelected((prev) => {
                  return {
                    ...prev,
                    lon: e.target.value,
                  };
                });
              }}
            />
            <span class="focus-border"></span>
          </div>

          <div class="col-3">
            <Button
              variant="contained"
              onClick={() => {
                console.log(selected);
                console.log(
                  selected.category,
                  selected.spotname,
                  selected.lat,
                  selected.lon,

                  selected.date + `T${selected.hour}:${selected.min}`,
                  1,
                  selected.count,

                  selected.driver
                );
                axios("https://k7c205.p.ssafy.io/api/spot/createname", {
                  method: "POST",
                  data: {
                    spotCategory: selected.category,
                    spotName: selected.spotname,
                    lat: selected.lat,
                    lon: selected.lon,
                    expectedTime:
                      selected.date + `T${selected.hour}:${selected.min}`,
                    status: 1,
                    count: selected.count,
                    userName: selected.driver,
                  },
                })
                  .then((res) => {
                    console.log(res);
                  })
                  .catch((err) => console.log("Update Price error", err));
              }}
            >
              업무 추가
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DataList;
