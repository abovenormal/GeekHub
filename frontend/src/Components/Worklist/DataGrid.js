import React, { useEffect, useState } from "react";
import { DataGrid } from '@mui/x-data-grid';
import { apiInstance } from "../../api/index";
function DataList() {
  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);

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
    return `${(params.row.expectedTime).substr(0, 10)} ${(params.row.expectedTime).substr(11, 18)}`
  }
  function getArrivedTime(params) {
    if (params.row.arrivedTime == null) {
      return;
    }
    return `${(params.row.arrivedTime).substr(0, 10)} ${(params.row.arrivedTime).substr(11, 18)}`
  }

  const columns = [
    { field: 'id', headerName: 'ID', width: 10 },
    {
      field: 'spotCategory',
      headerName: '분류',
      width: 120,
      editable: true,
    },
    {
      field: 'spotName',
      headerName: '가게 이름',
      width: 200,
      editable: true,
    },
    {
      field: 'expectedTime',
      headerName: '도착 예정 시간',
      type: 'dateTime',
      width: 200,
      editable: true,
      valueGetter: getExpectedTime
    },
    {
      field: 'arrivedTime',
      headerName: '도착 시간',
      type: 'dateTime',
      width: 200,
      editable: true,
      valueGetter: getArrivedTime
    },
    {
      field: 'count',
      headerName: '수량',
      type: 'numger',
      width: 150,
      editable: true,
    },
    {
      field: 'lat',
      headerName: '위도',
      width: 150,
      editable: true,
    },
    {
      field: 'lon',
      headerName: '경도',
      width: 150,
      editable: true,
    }
    // {
    //   field: 'fullName',
    //   headerName: 'Full name',
    //   description: 'This column has a value getter and is not sortable.',
    //   sortable: false,
    //   width: 160,
    //   valueGetter: (params) =>
    //     `${params.getValue(params.id, 'firstName') || ''} ${params.getValue(params.id, 'lastName') || ''
    //     }`,
    // },
  ];

  return (
    <div style={{ height: '50vh', width: '70vw' }}>
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
      />
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={1}
        experimentalFeatures={{ newEditingApi: true }}
      />
    </div>
  );
}

export default DataList;
