import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../../api/index";
import Button from "@mui/material/Button";
import "./DataGrid.css";
import DetailDropdown from "../Common/DetailDropdown";
function DataList() {
  let today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜
  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`,
    hour: "",
    min: "",
    storename: "",
    driver: "",
    lat: "",
    lon: "",
  });

  const [schoolList, setSchoolList] = useState([]);

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
                // console.log(selected);
                // axios("https://k7c205.p.ssafy.io/api/chat/message", {
                //   method: "POST",
                //   data: selected,
                // })
                //   .then((res) => {
                //     console.log(res);
                //     setChat(res.data);
                //   })
                //   .catch((err) => console.log("Update Price error", err));
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
