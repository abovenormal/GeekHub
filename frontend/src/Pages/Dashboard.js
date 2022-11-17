import React from "react";
import Chart from "../Components/Dashboard/Chart";
import ShortcutChat from "../Components/Dashboard/ShortcutChat";
import ShortcutDriverlocation from "../Components/Dashboard/ShortcutDriverlocation";
import ShortcutLog from "../Components/Dashboard/ShortcutLog";
import ShortcutTask from "../Components/Dashboard/ShortcutTask";
import DashboardMap from "../Components/Dashboard/DashboardMap";
import "./css/Dashboard.css";
const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <Chart />
      <div className="bottom-container">
        <div className="dash-map">
          <DashboardMap />
        </div>
        <div className="shorcut-chat">
          <ShortcutDriverlocation></ShortcutDriverlocation>
          <ShortcutChat></ShortcutChat>
        </div>
        <div className="shorcut-chat">
          <ShortcutLog></ShortcutLog>
          <ShortcutTask></ShortcutTask>
        </div>
      </div>
    </div>
  );
};
export default Dashboard;
