import React from "react";
import Chart from "../Components/Dashboard/Chart"
import Shortcut from "../Components/Dashboard/Shortcut";

import "./css/Dashboard.css"
const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <Chart />
      <Shortcut />
    </div>
  );
};
export default Dashboard;