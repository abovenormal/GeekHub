import React from "react";
import Chart from "../Components/Dashboard/Chart"
import Shortcut from "../Components/Dashboard/Shortcut";
import DashboardMap from "../Components/Dashboard/DashboardMap";
import "./css/Dashboard.css"
const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <Chart />
      <div className="bottom-container">
        <div className="dash-map">
          <DashboardMap/>
        </div>
      </div>
    </div>
  );
};
export default Dashboard;