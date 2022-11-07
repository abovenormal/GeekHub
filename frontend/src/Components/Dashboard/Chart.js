import React, { useEffect, useState } from "react";
import MyResponsivePie from "./MyResponsivePie";
import MyResponsiveLine from "./MyResponsiveLine";
import "./css/Chart.css";
import { Info } from "@material-ui/icons";

const Chart = () => {
  const [success, setSuccess] = useState([
    {
      name: "전남대",
      success: 123,
      fail: 2,
    },
    {
      name: "광주과학기술원",
      success: 1223,
      fail: 2,
    },
  ]);

  success.map((school, index) => {
    console.log(school.name);
    console.log(school.success);
  });
  const data_pie = {
    전남대: [
      {
        id: "success",
        label: "success",
        value: 130,
        color: "hsl(220, 70%, 50%)",
      },
      {
        id: "fail",
        label: "fail",
        value: 5,
        color: "hsl(68, 70%, 50%)",
      },
    ],
  };

  const data_line = [
    {
      "id": "픽업존",
      "color": "#fff",
      "data": [
        {
          "x": "plane",
          "y": 0
        },
        {
          "x": "helicopter",
          "y": 3
        },
        {
          "x": "boat",
          "y": 2
        },
        {
          "x": "train",
          "y": -1
        },
        {
          "x": "subway",
          "y": 3
        },
        {
          "x": "bus",
          "y": 2
        },
        {
          "x": "car",
          "y": 1
        },
        {
          "x": "moto",
          "y": 0
        },
        {
          "x": "bicycle",
          "y": 0
        },
        {
          "x": "horse",
          "y": 3
        },
        {
          "x": "skateboard",
          "y": 2
        },
        {
          "x": "others",
          "y": 2
        }
      ]
    }
  ]
  return (
    <div className="chart-container">
      <div className="pie-chart-container">
        <div className="pie-chart-title">Current Success</div>
        <div className="pie-chart-body">
          <MyResponsivePie className="pie-chart" data={data_pie.전남대} />
          <div>date</div>
        </div>
      </div>
      <div className="line-chart-container">
        <div className="line-chart-title">Arrival Time Difference</div>
        <div className="line-chart">
          <MyResponsiveLine data_line={data_line}/>
        </div>
      </div>
    </div>
  );
};

export default Chart;
