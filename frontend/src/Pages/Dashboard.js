import React, { useEffect } from "react";
import Chart from "../Components/Dashboard/Chart"
import { Link } from "react-router-dom";

import ShortcutChat from "../Components/Dashboard/ShortcutChat";
import ShortcutDriverlocation from "../Components/Dashboard/ShortcutDriverlocation.js";
import ShortcutLog from "../Components/Dashboard/ShortcutLog";
import ShortcutUser from "../Components/Dashboard/ShortcutUser";

import "./css/Dashboard.css"
const Dashboard = () => {
  return (
    <div className="dashboard-container">
      <Chart />
    </div>
  );
};
export default Dashboard;