import React from 'react';
import pie from "../../asset/image/chart.png"
import "./css/Chart.css";

const Chart = () => {
  return (
    <div className="chart">
      <img
        className="pie-chart"
        src={pie}
        alt="piechart"
      />
    </div>
  );
};

export default Chart;