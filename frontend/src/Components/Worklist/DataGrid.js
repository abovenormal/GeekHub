import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../../api/index";
function DataList() {
  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);
  const [newrows, setNewRows] = useState([]);

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
    setNewRows(result);
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
    console.log(result);
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
        <DataGrid
          rows={newrows}
          columns={columns}
          pageSize={1}
          experimentalFeatures={{ newEditingApi: true }}
        />
      </div>
    </div>
  );
}

export default DataList;
